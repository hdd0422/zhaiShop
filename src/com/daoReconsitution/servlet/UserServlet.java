package com.daoReconsitution.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daoReconsitution.dao.CartDao;
import com.daoReconsitution.dao.UserDao;
import com.daoReconsitution.entity.Cart;
import com.daoReconsitution.entity.Goods;
import com.daoReconsitution.entity.User;
import com.daoReconsitution.util.CommonUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author 韩豆豆
 *
 * @title 用户模块
 * 
 * @context 后台 1.修改密码,2.分页查询(i.模糊查询,ii.全查询),3.添加,4.修改,5批量删除
 * 
 * @area 前台 1.注册,2.登录
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

	private static UserDao userDao = new UserDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		CartDao cartDao = new CartDao();
		HttpSession session = request.getSession();
		JSONObject jsonObject = new JSONObject();
		User user = null;
		boolean exist = false;
		String type = request.getParameter("type");
		if ("updatePwd".equals(type)) {// 修改密码
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			user = (User) request.getSession().getAttribute("user");
			if (user.getPassword().equals(oldPassword)) {
				user.setPassword(newPassword);
				try {
					exist = userDao.updateUser(user);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (exist) {
					jsonObject.put("success", true);

				} else {
					jsonObject.put("error", true);
				}
				response.getWriter().print(jsonObject);
			}
		} else if ("findAll".equals(type)) {// 分页查询
			String userName = request.getParameter("s_userName");
			int pageNow = Integer.parseInt(request.getParameter("page")); // 获取当前页码，easyui默认传到后台
			int pageSize = Integer.parseInt(request.getParameter("rows")); // 获取每页显示多少行，easyui默认传到后台
			int pageCount = 0;
			List<User> list = null;
			if (userName != null) {
				try {
					// 模糊查询
					pageCount = userDao.getTotal(userName);
					list = userDao.getUsersByPage(pageSize, pageNow, userName);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					// 全查询
					pageCount = userDao.getTotal();
					list = userDao.getUsersByPage(pageSize, pageNow);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			jsonObject = CommonUtil.toGridJson(pageCount, list);
			System.out.println(jsonObject);
			response.getWriter().print(jsonObject);

		} else if ("addUser".equals(type)) {// 添加
			String userName = request.getParameter("userName");
			String trueName = request.getParameter("trueName");
			String sex = request.getParameter("sex");
			String birthday = request.getParameter("birthday");
			String statusID = request.getParameter("statusID");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String email = request.getParameter("email");
			int userType = 1;
			String password = request.getParameter("password");
			user = new User(0, userName, trueName, sex, birthday, statusID, phone, address, email, userType, password);
			try {
				exist = userDao.addUser(user);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (exist) {
				jsonObject.put("success", true);

			} else {
				jsonObject.put("error", true);
			}
			response.getWriter().print(jsonObject);

		} else if ("updateUser".equals(type)) {// 修改
			String userName = request.getParameter("userName");
			String trueName = request.getParameter("trueName");
			String sex = request.getParameter("sex");
			String birthday = request.getParameter("birthday");
			String statusID = request.getParameter("statusID");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String email = request.getParameter("email");
			int userType = 1;
			String password = request.getParameter("password");
			int id = Integer.parseInt(request.getParameter("id"));
			user = new User(id, userName, trueName, sex, birthday, statusID, phone, address, email, userType, password);
			try {
				exist = userDao.updateUser(user);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (exist) {
				jsonObject.put("success", true);

			} else {
				jsonObject.put("error", true);
			}
			response.getWriter().print(jsonObject);
		} else if ("deleteUser".equals(type)) {// 批量删除
			String[] s_ids = request.getParameterValues("ids");
			String[] ids = s_ids[0].split(",");

			try {
				userDao.deleteAll(ids);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonObject.put("success", true);
			response.getWriter().print(jsonObject);

		} else if ("register".equals(type)) {// 注册
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			user = new User(username, password);
			try {
				exist = userDao.addUser(user);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (exist) {
				request.setAttribute("regeState", true);

			} else {
				request.setAttribute("regeState", false);
			}
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else if ("mainLogin".equals(type)) {// 登录
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			user = new User(username, password);
			User temp = null;
			try {
				temp = userDao.mainLogin(user);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (temp != null) {
				request.getSession().setAttribute("user", temp);
				response.sendRedirect(request.getContextPath());
			} else {
				request.getSession().setAttribute("loginFail", username);
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			}

		}if ("userLogin".equals(type)) {// 登录
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			user = new User(username, password);
			User temp = null;
			try {
				temp = userDao.mainLogin(user);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (temp != null) {
				request.getSession().setAttribute("user", temp);
				HashMap<String, Goods> gwc = (HashMap<String, Goods>) session.getAttribute("gwc");
				if (gwc.size() > 0) {
					try {
						Set<String> keySet = gwc.keySet();
						System.out.println(keySet);
						Iterator<String> iterator = keySet.iterator();
						while (iterator.hasNext()) {
							String key = (String) iterator.next();
							Goods goods = gwc.get(key);
							int count = cartDao.count(user.getId(), Integer.parseInt(key));
							if (count > 0) {
								cartDao.updateCart(
										new Cart(new BigDecimal(user.getId()), new BigDecimal(Integer.parseInt(key)),
												new BigDecimal(count + goods.getNum()), new BigDecimal(0)));
							} else {
								cartDao.addCart(
										new Cart(new BigDecimal(user.getId()), new BigDecimal(Integer.parseInt(key)),
												new BigDecimal(goods.getNum()), new BigDecimal(goods.getPrice())));
							}
						}
						gwc.clear();
						session.setAttribute("gwc", gwc);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				jsonObject.put("status", 1);
				jsonObject.put("info", "登录成功");
			} else {
				jsonObject.put("status", 0);
				jsonObject.put("info", "账户或密码错误");
			}
			response.getWriter().print(jsonObject);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
