package com.courses.mvc.service;

import com.courses.mvc.domain.Message;
import com.courses.mvc.domain.User;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.listener.HttpSessionListenerImpl;
import com.courses.mvc.repository.MessageRepository;
import com.courses.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Stepan
 */
@Service
@Qualifier("chatService")
public class ChatService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public UserDTO authUser(String sessionId){
       HttpSession httpSession = HttpSessionListenerImpl.getSessionById(sessionId);
       return (UserDTO) httpSession.getAttribute("user");
    }

    public List<Message> getAllMessagesByUserLogin(String login) {
        return messageRepository.getAllMessagesByReceiverLogin(login);
    }

    public void saveMessage(String message, String sender, String receiver) {
        Message newMessage = new Message();
        User senderUser = userRepository.findUserByLogin(sender);
        User receiverUser = userRepository.findUserByLogin(receiver);
        newMessage.setBody(message);
        newMessage.setReceiver(receiverUser);
        newMessage.setSender(senderUser);

        messageRepository.save(newMessage);
    }
}
