package com.courses.mvc.controller;

import com.courses.mvc.domain.Message;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.service.ChatService;
import com.courses.mvc.service.RedisService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;

/**
 * @author Stepan
 */
public class ChatSocketController extends TextWebSocketHandler {

    static Map<String, WebSocketSession> clientsOnline = new HashMap<>();

    @Autowired
    private RedisService redisService;

    @Autowired
    ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedMessage = message.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        JsonParser jp = mapper.getFactory().createParser(receivedMessage);
        JsonNode root = mapper.readTree(jp);
        Iterator<Map.Entry<String, JsonNode>> it = root.fields();
        Map.Entry<String, JsonNode> entry = it.next();
        if (entry.getKey() == null) {
            session.sendMessage(new TextMessage("Bad"));
        } else if (entry.getKey().equals("sessionId")) {
            String sessionId = entry.getValue().asText();
            UserDTO user = chatService.authUser(sessionId);
            if (user != null) {
                session.sendMessage(new TextMessage("{\"auth\":\"yes\"}"));
                clientsOnline.put(user.getLogin(), session);

                List<Message> messages = chatService.getAllMessagesByUserLogin(user.getLogin());

                messages.forEach(message1 -> {
                    ObjectNode privateMessage = mapper.createObjectNode();
                    JsonNode senderNode = mapper.createObjectNode().path(message1.getSender().getLogin());
                    JsonNode messageNode = mapper.createObjectNode().path(message1.getBody());
                    privateMessage.set("name", senderNode);
                    privateMessage.set("message", messageNode);
                    try {
                        session.sendMessage(new TextMessage(privateMessage.asText()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                List<ObjectNode> allMessages = redisService.getAllMessages();
                allMessages.forEach(broadcastMessage -> {
                    try {
                        session.sendMessage(new TextMessage(broadcastMessage.asText()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


            } else {
                session.sendMessage(new TextMessage("{\"auth\":\"no\"}"));
            }
        } else if (entry.getKey().equals("list")) {
            List<String> usersList = new ArrayList<>(clientsOnline.keySet());
            ObjectNode key = mapper.createObjectNode();
            ArrayNode list = mapper.createArrayNode();
            usersList.forEach(list::add);
            key.set("list", list);
            session.sendMessage(new TextMessage(key.asText()));
        } else if (entry.getKey().equals("broadcast")) {
            String sender = clientsOnline.entrySet().stream()
                    .filter(item -> item.getValue() == session)
                    .findFirst()
                    .get()
                    .getKey();
            ObjectNode broadCastMessage = mapper.createObjectNode();

            JsonNode senderNode = mapper.createObjectNode().path(sender);
            broadCastMessage.set("message", entry.getValue());
            broadCastMessage.set("name", senderNode);
            Jedis jedis = redisService.getJedis();
            jedis.lpush("broadcast", sender + ":" + entry.getValue().asText());
            clientsOnline.entrySet().forEach(clientOnline -> {
                try {
                    clientOnline.getValue().sendMessage(new TextMessage(broadCastMessage.asText()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            String name = entry.getKey();
            String value = entry.getValue().asText();
            WebSocketSession socketSession = clientsOnline.get(name);
            String sender = clientsOnline.entrySet().stream()
                    .filter(item -> item.getValue() == session)
                    .findFirst()
                    .get()
                    .getKey();
            if (socketSession != null) {
                ObjectNode privateMessage = mapper.createObjectNode();

                JsonNode senderNode = mapper.createObjectNode().path(sender);

                privateMessage.set("name", senderNode);
                privateMessage.set("message", entry.getValue());
            } else {
                chatService.saveMessage(entry.getValue().asText(), sender, name);
            }

        }
    }

}
