package com.daoReconsitution.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daoReconsitution.dao.UserDao;
import com.daoReconsitution.entity.User;



/**
 * 
 * @author 韩氏王朝
 * 
 * @title 登录模块
 * 
 * @context 先进行验证码的获取以及验证，然后根据用户名以及密码到数据库里面验证，并封装到JavaBean中，再使用请求转发传入页面
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String vCode = (String) session.getAttribute("vCode");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String jyzm = request.getParameter("jyzm");
		if (vCode.equalsIgnoreCase(jyzm)) {
			User user = new User(username, password);
			UserDao userDao = new UserDao();
			User back = null;
			try {
				back = userDao.check(user);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean isSuccess = back == null ? false : true;
			if (isSuccess) {
				session.setAttribute("user", back);
				request.setAttribute("stateOK", 0);
			} else {
				request.setAttribute("stateOK", 2);
			}

		} else {
			request.setAttribute("stateOK", 1);
		}

		request.getRequestDispatcher("/admin/main.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
