package com.daoReconsitution.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoReconsitution.dao.OrderDao;
import com.daoReconsitution.entity.Goods;
import com.daoReconsitution.entity.Order;
import com.daoReconsitution.entity.OrderItem;
import com.daoReconsitution.entity.User;
import com.daoReconsitution.util.CommonUtil;
import com.daoReconsitution.util.DateFormateUtil;
import com.daoReconsitution.util.GetOrderId;
import com.google.gson.Gson;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import net.sf.json.JSONObject;

/**
 * 
 * @author 韩豆豆
 *
 * @title 商品小类模块
 * 
 * @context 1.分页查询(i.模糊查询,ii.全查询),2.单个详细信息查询,3.修改发货,前台:4.新增订单
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

	private static final String User = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Order order = new Order();
		OrderDao orderDao = new OrderDao();
		JSONObject jsonObject = new JSONObject();
		String type = request.getParameter("type");
		if ("findAll".equals(type)) {
			String userName = request.getParameter("userName");
			String orderNo = request.getParameter("orderNo");
			int pageNow = Integer.parseInt(request.getParameter("page")); // 获取当前页码，easyui默认传到后台
			int pageSize = Integer.parseInt(request.getParameter("rows")); // 获取每页显示多少行，easyui默认传到后台
			int pageCount = 0;
			List<Order> list = null;
			if (userName == null && orderNo == null) {
				// 全查询
				try {
					pageCount = orderDao.getTotal();
					list = orderDao.getBigTypeByPage(pageSize, pageNow);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				// 综合查询
				List<Object> params = new ArrayList<Object>();
				params.add(userName);
				params.add(orderNo);
				try {
					pageCount = orderDao.getTotal(params);
					list = orderDao.getBigTypeByPage(pageSize, pageNow, params);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			jsonObject = CommonUtil.toGridJson(pageCount, list);
			response.getWriter().print(jsonObject);
		} else if ("findOrderItemByOrderId".equals(type)) {
			String orderNo = request.getParameter("orderNo");
			List<OrderItem> list = null;
			try {
				list = orderDao.findOrderItemByOrderId(orderNo);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String json = new Gson().toJson(list);
			jsonObject.put("rows", json);
			response.getWriter().print(jsonObject);
		} else if ("updateOrderState".equals(type)) {
			String[] orderNos = request.getParameterValues("orderNos");
			String[] temp = orderNos[0].split(",");
			try {
				orderDao.updateOrderState(temp);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonObject.put("success", true);
			response.getWriter().print(jsonObject);
		}else if ("submitOrder".equals(type)) {//提交订单
			//接收参数
			String addressId = request.getParameter("sender");//地址id
			String PayType = request.getParameter("PayType");//支付类型
			String liuyan = request.getParameter("liuyan");//备注留言
			List<Goods> gwcGoodsList =(List<Goods>) request.getSession().getAttribute("gwcGoodsList");//商品列表
			User user =(User) request.getSession().getAttribute("user");
			double ze =(double) request.getSession().getAttribute("ze");//总价
			//定义实体类
			String orderId=GetOrderId.sjs();
			order=new Order(orderId, user.getId(), ze, Integer.parseInt(addressId), liuyan, DateFormateUtil.formatDate(new Date()), Integer.parseInt(PayType));
			try {
				//添加订单表
				orderDao.addOrder(order);
				//添加订单详情表
				for (Goods goods : gwcGoodsList) {
					OrderItem orderItem=new OrderItem(0,goods.getId(), goods.getName(), goods.getProPic(), goods.getPrice(),goods.getNum(),  goods.getTotal(), orderId);
					orderDao.addOrderItem(orderItem);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}	
			request.getRequestDispatcher("/orderState.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
