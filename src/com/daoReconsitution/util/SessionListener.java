package com.daoReconsitution.util;

import java.util.HashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.daoReconsitution.entity.Goods;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent arg0)  { 
         HttpSession session = arg0.getSession();
         HashMap<String, Goods> gwc =new HashMap<String, Goods>();
         session.setAttribute("gwc", gwc);
    }

    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
