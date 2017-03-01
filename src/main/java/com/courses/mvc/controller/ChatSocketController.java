package com.courses.mvc.controller;

import com.courses.mvc.domain.Message;
import com.courses.mvc.domain.User;
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
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
        Type jsontType = new TypeToken<ArrayList<HashMap<String, String>>>() {}.getType();
        ArrayList<Map<String, String>> myList = gson.fromJson(message.getPayload(), jsontType);
        Map.Entry<String, String> entry = myList.get(0).entrySet().iterator().next();
        String key = entry.getKey();
        String value = entry.getValue();
        if (key == null)
            session.sendMessage(new TextMessage("Bad"));
        else
            switch (key) {
                case "sessionId":
                    UserDTO user = chatService.authUser(value);
                    authUser(session, user);
                    break;
                case "list":
                    session.sendMessage(new TextMessage(gson.toJson(clientsOnline.keySet())));
                    break;
                case "broadcast":
                    sendMessage(session, false, value, null);
                    break;
                default:
                    WebSocketSession socketSession = clientsOnline.get(key);
                    sendMessage(socketSession, true, value, key);
                    break;
            }
    }

    private void sendMessage(WebSocketSession session, boolean isPrivate, String message, String receiver) {
        String sender = clientsOnline.entrySet().stream()
                .filter(item -> item.getValue() == session)
                .findFirst()
                .orElse(null)
                .getKey();

        if (isPrivate) {
            if (session != null) {
                JsonObject privateMessage = new JsonObject();
                privateMessage.addProperty("name", sender);
                privateMessage.addProperty("message", message);
            } else
                chatService.saveMessage(message, sender, receiver);
        } else {
            JsonObject broadcastMessage = new JsonObject();
            broadcastMessage.addProperty("message", message);
            broadcastMessage.addProperty("sender", sender);

            Jedis jedis = redisService.getJedis();

            jedis.lpush("broadcast", broadcastMessage.getAsString());
            clientsOnline.entrySet().forEach(clientOnline -> {
                try {
                    clientOnline.getValue().sendMessage(new TextMessage(broadcastMessage.getAsString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void authUser(WebSocketSession session, UserDTO user) throws IOException {
        Gson gson = new Gson();
        if (user != null) {
            session.sendMessage(new TextMessage("{'auth':'yes'}"));
            clientsOnline.put(user.getLogin(), session);
            List<Message> messages = chatService.getAllMessagesByUserLogin(user.getLogin());

            messages.forEach(userMessage -> {
                try {
                    session.sendMessage(new TextMessage(gson.toJson(userMessage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            List<Message> broadcastMessages = redisService.getBroadcastMessages();
            broadcastMessages.forEach(broadcastMessage -> {
                try {
                    session.sendMessage(new TextMessage(gson.toJson(broadcastMessage, Message.class)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } else {
            session.sendMessage(new TextMessage("{'auth':'no'}"));
        }
    }
}
