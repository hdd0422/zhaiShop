package com.daoReconsitution.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoReconsitution.dao.GoodsDao;
import com.daoReconsitution.entity.Goods;

/**
 * 
 * @ClassName: GoodsDetailServlet
 * @Description:TODO(展示商品详情)
 * @author: 韩豆豆
 * @date: 2020年4月11日 上午10:05:13
 * @context
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
@WebServlet("/GoodsDetailServlet")
public class GoodsDetailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		GoodsDao goodsDao = new GoodsDao();
		int id = Integer.parseInt(request.getParameter("id"));
		Goods goods = null;
		List<Goods> xgGoods = null;
		try {
			goods = goodsDao.selectGoods(id);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			xgGoods = goodsDao.findAllGoods(goods.getSmallTypeId());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("goods", goods);
		request.setAttribute("xgGoods", xgGoods);
		request.getRequestDispatcher("/goods.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
