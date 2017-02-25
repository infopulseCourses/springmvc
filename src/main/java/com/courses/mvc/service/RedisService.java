package com.courses.mvc.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stepan
 */
@Service
//TODO implement
public class RedisService {
    public Jedis getJedis() {
        return null;
    }

    public List<ObjectNode> getAllMessages() {
        List<String> messages = getJedis().lrange("broadcast", 0, -1);
        ObjectMapper mapper = new ObjectMapper();
        List<ObjectNode> result = messages.stream().map(message -> {
            String[] messageArray = message.split(":");

            ObjectNode broadCastMessage = mapper.createObjectNode();
            JsonNode senderNode = mapper.createObjectNode().path(messageArray[0]);
            JsonNode messageNode = mapper.createObjectNode().path(messageArray[1]);
            broadCastMessage.set("name", senderNode);
            broadCastMessage.set("message", messageNode);
            return broadCastMessage;
        }).collect(Collectors.toList());

        return result;
    }
}
