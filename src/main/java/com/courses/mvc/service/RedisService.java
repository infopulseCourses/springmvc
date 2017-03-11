package com.courses.mvc.service;

import com.google.gson.JsonObject;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stepan
 */
@Service
@PropertySource("classpath:redis.properties")
public class RedisService {

    @Resource
    Environment environment;

    private Jedis jedis;

    public synchronized Jedis getJedis() {
        if(jedis == null) {
            jedis = new Jedis(environment.getProperty("redis.url"));
        }
        return jedis;
    }

    public List<JsonObject> getBroadcastMessages(){
        List<String> messages = getJedis().lrange("broadcast", 0, -1);
        List<JsonObject> jsonMessages = new ArrayList<>();

        for(String message : messages){
            JsonObject messageObject = new JsonObject();
            messageObject.addProperty("name", message.split(":")[0]);
            messageObject.addProperty("message", message.split(":")[1]);
            jsonMessages.add(messageObject);
        }

        return jsonMessages;
    }

    public void addMessage(String sender, String message){
        getJedis().lpush("broadcast",sender + ":" + message);
    }

}
