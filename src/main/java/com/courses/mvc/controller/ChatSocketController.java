package com.courses.mvc.controller;

import com.courses.mvc.domain.Message;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.service.ChatService;
import com.courses.mvc.service.RedisService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Stepan
 */
public class ChatSocketController extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> clientsOnline = new HashMap<>();

    @Autowired
    private RedisService redisService;

    @Autowired
    private ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Gson gson = new Gson();
        Type jsontType = new TypeToken<HashMap<String, String>>(){}.getType();
        Map<String, String> stringMap = gson.fromJson(message.getPayload(), jsontType);
        String key = stringMap.keySet().iterator().next();
        String value = stringMap.values().iterator().next();

        if (key == null) {
            session.sendMessage(new TextMessage("Bad"));
            return;
        }
        if (key.equals("sessionId")) {
            UserDTO user = chatService.authUser(value);
            authUser(session, user);
            return;
        }
        if (!isAuthorized(session)) {
            session.sendMessage(new TextMessage("Bad"));
            return;
        }
        switch (key) {
            case "list":
                JsonObject list = new JsonObject();
                list.add("list", gson.toJsonTree(clientsOnline.keySet()));
                session.sendMessage(new TextMessage(gson.toJson(list)));
                break;
            case "broadcast":
                sendMessage(session, false, value, null);
                break;
            default:
                sendMessage(session, true, value, key);
                break;
        }
    }

    private boolean isAuthorized(WebSocketSession session) {
        return clientsOnline.values().contains(session);
    }

    private void sendMessage(WebSocketSession session, boolean isPrivate, String message, String receiver) throws IOException {
        Gson gson = new Gson();
        String sender = clientsOnline.entrySet().stream()
                .filter(item -> item.getValue() == session)
                .findFirst()
                .orElse(null)
                .getKey();

        if (isPrivate) {
            if (clientsOnline.get(receiver) != null) {
                JsonObject privateMessage = new JsonObject();
                privateMessage.addProperty("name", sender);
                privateMessage.addProperty("message", message);

                clientsOnline.get(receiver).sendMessage(new TextMessage(gson.toJson(privateMessage)));
            } else {
                System.out.println("save message");
                chatService.saveMessage(message, sender, receiver);
            }
        } else {
            JsonObject broadcastMessage = new JsonObject();
            broadcastMessage.addProperty("name", sender);
            broadcastMessage.addProperty("message", message);

            redisService.addMessage(sender,message);

            for (WebSocketSession socketSession : clientsOnline.values()) {
                socketSession.sendMessage(new TextMessage(gson.toJson(broadcastMessage)));
            }
        }
    }

    private void authUser(WebSocketSession session, UserDTO user) throws IOException {
        Gson gson = new Gson();
        if (user != null) {
            session.sendMessage(new TextMessage("{\"auth\":\"yes\"}"));
            clientsOnline.put(user.getLogin(), session);
            List<Message> messages = chatService.getAllMessagesByUserLogin(user.getLogin());

            messages.forEach(userMessage -> {
                try {
                    session.sendMessage(new TextMessage(gson.toJson(userMessage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            List<JsonObject> broadcastMessages = redisService.getBroadcastMessages();
            broadcastMessages.forEach(broadcastMessage -> {
                try {

                    session.sendMessage(new TextMessage(gson.toJson(broadcastMessage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } else {
            session.sendMessage(new TextMessage("{'auth':'no'}"));
        }
    }
}
