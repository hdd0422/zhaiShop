package com.daoReconsitution.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daoReconsitution.dao.BigTypeDao;
import com.daoReconsitution.dao.SmallTypeDao;
import com.daoReconsitution.entity.BigType;
import com.daoReconsitution.entity.SmallType;



/**
 * 
 * @ClassName:  SearchServlet   
 * @Description:TODO(搜索模块Servlet)   
 * @author: 韩豆豆 
 * @date:   2020年4月11日 上午10:46:55   
 * @context  
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 
		String s_bid = request.getParameter("bid");
		String s_sid = request.getParameter("sid");
		BigType bigType = null;
		SmallType smallType = null;
		if (s_bid!=null&&"".equals(s_bid)) {
			int bid = Integer.parseInt(s_bid);
			try {
				bigType=new BigTypeDao().findBigType(bid);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (s_sid!=null&&"".equals(s_sid)) {
			int sid = Integer.parseInt(s_sid); 
			try {
				smallType= new SmallTypeDao().findSmallType(sid);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		HttpSession session = request.getSession();
		session.setAttribute("bigType",bigType );
		session.setAttribute("smallType",smallType );
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


	

}
