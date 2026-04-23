package com.ssafy.prj.listener;

import com.ssafy.prj.model.dao.BoardDaoFileImpl;
import com.ssafy.prj.model.dao.MemberDaoImpl;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class FileContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  { 
    	BoardDaoFileImpl.getInstance().load();
//    	MemberDaoImpl.getInstance().load();
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    	BoardDaoFileImpl.getInstance().save();
//    	MemberDaoImpl.getInstance().save();
    }
	
}





