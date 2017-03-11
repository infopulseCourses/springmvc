package com.courses.mvc.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stepan
 */
@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener{
    private static final Map<String,HttpSession> sessionMap = new HashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        sessionMap.put(session.getId(),session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        sessionMap.remove(se.getSession().getId());
    }

    public static HttpSession getSessionById(String id){
        return sessionMap.get(id);
    }
}
