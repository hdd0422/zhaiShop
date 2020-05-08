package com.daoReconsitution.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daoReconsitution.dao.AddressDao;
import com.daoReconsitution.entity.Address;
import com.daoReconsitution.entity.User;

import net.sf.json.JSONObject;

@WebServlet("/AddressServlet")
public class AddressServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Address address = new Address();
		AddressDao addressDao = new AddressDao();
		boolean exist = false;
		if ("addAddress".equals(type)) {
			String province = request.getParameter("province");
			String city = request.getParameter("city");
			String area = request.getParameter("area");
			String posi = request.getParameter("posi");
			String pho = request.getParameter("pho");
			String rel = request.getParameter("rel");
			Integer msg = new Integer(request.getParameter("msg"));
			address = new Address(new BigDecimal(0), province, city, area, posi, pho, rel, msg, user.getId());
			int id=0;
			try {
				if (msg!=0) {
					addressDao.clearDefault(user.getId());
				}
				id=addressDao.addInfo(address);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("AddressId", id);
			jsonObject.put("success", "true");
			out.print(jsonObject);
		}else if("deleteAddress".equals(type)) {
			Integer id=new Integer(request.getParameter("id"));
			try {
				exist = addressDao.deleteAddress(id);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (exist) {
				out.write("{\"success\":\"true\"}");
			} else {
				out.write("{\"error\":\"false\"}");
			}
		}else if("setDefault".equals(type)) {
			Integer id=new Integer(request.getParameter("id"));
			try {
				addressDao.clearDefault(user.getId());
				exist=addressDao.setDefault(id,user.getId());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			if (exist) {
				out.write("{\"success\":\"true\"}");
			} else {
				out.write("{\"error\":\"false\"}");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
