package com.daoReconsitution.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daoReconsitution.dao.AddressDao;
import com.daoReconsitution.dao.BigTypeDao;
import com.daoReconsitution.dao.GoodsDao;
import com.daoReconsitution.dao.SlideDao;
import com.daoReconsitution.dao.SmallTypeDao;
import com.daoReconsitution.dao.UserDao;
import com.daoReconsitution.entity.Address;
import com.daoReconsitution.entity.BigType;
import com.daoReconsitution.entity.Goods;
import com.daoReconsitution.entity.Slide;
import com.daoReconsitution.entity.SmallType;
import com.daoReconsitution.entity.User;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: PageServlet
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 韩豆豆
 * @date: 2020年4月8日 上午9:23:10
 * 
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
@WebServlet("/PageServlet")
public class PageServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type = request.getParameter("type");
		UserDao userDao = new UserDao();
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Address address = new Address();
		AddressDao addressDao = new AddressDao();
		boolean exist = false;
		if ("refreshSystem".equals(type)) {
			List<Slide> slideList=null;
			try {
				slideList = new SlideDao().selectAllslide();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletContext application = request.getSession().getServletContext();
			application.setAttribute("slideList", slideList);
			application.setAttribute("floor", getData());

			jsonObject.put("success", true);
			response.getWriter().print(jsonObject);
		} else if ("userCenter".equals(type)) {
			if (null == user) {
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			} else {
				response.sendRedirect(request.getContextPath() + "/userMain.jsp");
			}

		} else if ("userData".equals(type)) {
			request.setAttribute("rightPage", "userData");
			request.getRequestDispatcher("/userMain.jsp").forward(request, response);
		} else if ("MyCollect".equals(type)) {
			request.setAttribute("rightPage", "MyCollect");
			request.getRequestDispatcher("/userMain.jsp").forward(request, response);
		} else if ("userDataUpdate".equals(type)) {
			String userName = request.getParameter("userName");
			String trueName = request.getParameter("trueName");
			String sex = request.getParameter("sex");
			String birthday = request.getParameter("birthday");
			String statusID = request.getParameter("statusID");
			String phone = request.getParameter("phone");
			String s_address = request.getParameter("address");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			int id = Integer.parseInt(request.getParameter("uid"));
			User temp = new User(id, userName, trueName, sex, birthday, statusID, phone, s_address, email, 0, password);
			try {
				exist = userDao.updateUser(temp);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (exist) {
				session.setAttribute("user", temp);
				out.write("{\"success\":\"true\"}");
			} else {
				out.write("{\"success\":\"false\"}");
			}
		} else if ("userAddress".equals(type)) {
			List<Address> addresses = null;
			try {
				addresses = addressDao.findAllInfo(user.getId());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("addressList", addresses);
			request.setAttribute("rightPage", "address");
			request.getRequestDispatcher("/userMain.jsp").forward(request, response);
		}else if ("goAddAddress".equals(type)) {
			request.setAttribute("rightPage", "addAddress");
			request.getRequestDispatcher("/userMain.jsp").forward(request, response);
		}else if("goChangePassword".equals(type)) {
			request.setAttribute("rightPage", "changePassword");
			request.getRequestDispatcher("/userMain.jsp").forward(request, response);
		}else if("changePassword".equals(type)) {
			String old = request.getParameter("old");
			String password = request.getParameter("password");
			if (user.getPassword().equals(old)) {
				user.setPassword(password);
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
			}else {
				jsonObject.put("oldError", true);
				response.getWriter().print(jsonObject);
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public static List<BigType> getData() {
		List<BigType> floor = new ArrayList<BigType>();

		try {
			List<BigType> bigTypeList = new BigTypeDao().selectAllBigType();
			Iterator<BigType> iterator = bigTypeList.iterator();
			while (iterator.hasNext()) {
				BigType bigType = (BigType) iterator.next();
				List<SmallType> smallTypeList = new SmallTypeDao().selectAllSmallTypeByBigTypeId(bigType.getId());
				List<Goods> goodsList = new GoodsDao().selectAllGoodsByBigTypeId(bigType.getId());
				bigType.setGoodsList(goodsList);
				bigType.setSmallTypeList(smallTypeList);
				floor.add(bigType);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return floor;
	}
}
