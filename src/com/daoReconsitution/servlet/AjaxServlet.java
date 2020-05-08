package com.daoReconsitution.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daoReconsitution.dao.UserDao;
import com.daoReconsitution.entity.User;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName:  AjaxServlet   
 * @Description:TODO(Ajax验证)   
 * @author: 韩豆豆 
 * @date:   2020年4月11日 上午9:56:05   
 * @context  i.验证用户名,ii.判断验证码正误
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
@WebServlet("/AjaxServlet")
public class AjaxServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type = request.getParameter("type");
		PrintWriter out = response.getWriter();
		if ("checkUserName".equals(type)) {
			UserDao userDao = new UserDao();
			String name = request.getParameter("name");
			User user =null;
			try {
				user = userDao.checkName(name);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean exist=user==null?true:false;
			if (exist) {
				out.write( "{\"success\":\"true\"}"   );
			} else {
				out.write( "{\"success\":\"false\"}"   );
			}
			
		} else if ("checkVCode".equals(type)) {
			HttpSession session = request.getSession();
			String vCode = (String) session.getAttribute("vCode");
			String jyzm = request.getParameter("jyzm");
			if (vCode.equalsIgnoreCase(jyzm)) {
				out.write( "{\"success\":\"true\"}"   );
			} else {
				out.write( "{\"success\":\"false\"}"   );
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
