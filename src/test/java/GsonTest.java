import com.courses.mvc.domain.Message;
import com.courses.mvc.domain.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Stepan.Kachan
 */
public class GsonTest {

    @Test
    public void objectToJsonTest() {
        Gson gson = new Gson();
        String json = gson.toJson(createMessage());
        assertNotNull(json);
        System.out.println(json);
    }

    @Test
    public void jsonToObjectTest() {
        Message message = createMessage();
        Gson gson = new Gson();
        String jsonString = gson.toJson(message);
        Message newMessage = gson.fromJson(jsonString, Message.class);
        assertEquals(newMessage.getBody(), "Message body");
        assertNotNull(newMessage.getReceiver());
        assertNotNull(newMessage.getSender());
    }

    @Test
    public void testJsonMessagesList() {
        String message1 = " {\"body\":\"Message body\",\"date\":\"Mar 1, 2017 12:32:59 AM\",\"sender\":{\"name\":\"Sender\",\"login\":\"Sender login\"},\"receiver\":{\"name\":\"Receiver\",\"login\":\"Receiver login\"}}";
        String message2 = " {\"body\":\"Message body2\",\"date\":\"Mar 1, 2017 12:32:59 AM\",\"sender\":{\"name\":\"Sender\",\"login\":\"Sender login\"},\"receiver\":{\"name\":\"Receiver\",\"login\":\"Receiver login\"}}";
        List<String> messages = Arrays.asList(message1, message2);
        messages.forEach(message -> {
            Gson gson = new Gson();
            assertNotNull(gson.fromJson(message, Message.class).getBody());
            assertNotNull(gson.fromJson(message, Message.class).getSender());
            assertNotNull(gson.fromJson(message, Message.class).getReceiver());
        });
    }

    @Test
    public void testKeyValue() {
        String json = "{'sessionId':'55'}";

        Type listType = new TypeToken<HashMap<String, String>>() {
        }.getType();

        Gson gson = new Gson();

       Map<String, String> myList = gson.fromJson(json, listType);

       System.out.println(myList.keySet().iterator().next());
       System.out.println(myList.values().iterator().next());
       /* for (Map<String, String> m : myList) {

            assertEquals(m.get("sessionId"), "55");
           // assertEquals(m.get("auth"), "no");
            System.out.println(m.get("sessionId"));
            //System.out.println(m.get("auth"));
        }*/
    }

    @Test
    public void jsonObjectTest() {
        JsonObject json = new JsonObject();
        json.addProperty("message", "message body");
        json.addProperty("sender", "Oleg");
        assertEquals(json.get("message").getAsString(), "message body");
        assertEquals(json.get("sender").getAsString(), "Oleg");
        System.out.println(json);
    }

    private Message createMessage() {
        Message message = new Message();
        message.setDate(new Date());
        message.setBody("Message body");
        message.setReceiver(createReceiver());
        message.setSender(createSender());

        return message;
    }

    private User createReceiver() {
        User receiver = new User();
        receiver.setName("Receiver");
        receiver.setLogin("Receiver login");
        return receiver;
    }

    private User createSender() {
        User sender = new User();
        sender.setName("Sender");
        sender.setLogin("Sender login");
        return sender;
    }
}
