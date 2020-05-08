package com.daoReconsitution.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daoReconsitution.dao.CollectDao;
import com.daoReconsitution.dao.GoodsDao;
import com.daoReconsitution.entity.Collect;
import com.daoReconsitution.entity.Goods;
import com.daoReconsitution.entity.User;
import com.daoReconsitution.util.DateFormateUtil;

/**
 * 
 * @ClassName: CollectServlet
 * @Description:TODO(收藏模块servlet)
 * @author: 韩豆豆
 * @date: 2020年4月14日 上午8:31:41
 * @context
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */

@WebServlet("/CollectServlet")
public class CollectServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collect collect = new Collect();
		CollectDao collectDao = new CollectDao();
		boolean exist = false;
		PrintWriter out = response.getWriter();
		String type = request.getParameter("type");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if ("myCollect".equals(type)) {
			if (null != user) {
				System.out.println("用户验证");
				int pageSize = 10;
				int pageNow = 1;
				String page = request.getParameter("page");
				if (page != null && !"".equals(page)) {
					pageNow = Integer.parseInt(page);
				}
				List<Collect> collects = null;
				int pageCount = 1;
				try {
					pageCount = collectDao.getTotal(pageSize, user.getId());
					collects = collectDao.findAllInfo(pageSize, pageNow, user.getId());
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("collectList", collects);
				request.setAttribute("count", pageCount);
				request.setAttribute("rightPage", "MyCollect");
				request.getRequestDispatcher("/PageServlet?type=MyCollect").forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			}

		} else if ("addCollect".equals(type)) {
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				collect = collectDao.findInfo(id, user.getId());
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (collect != null) {
				out.write("{\"msg\":\"重复添加收藏\"}");
				return;
			} else {
				try {
					Goods goods = new GoodsDao().selectGoods(id);
					collect = new Collect(new BigDecimal(0), new BigDecimal(user.getId()), new BigDecimal(id),
							goods.getName(), goods.getProPic(), new BigDecimal(goods.getPrice()),
							DateFormateUtil.formatDate(new Date()));
					exist = collectDao.addCollect(collect);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (exist) {
					out.write("{\"msg\":\"添加收藏成功\"}");
				} else {
					out.write("{\"msg\":\"添加收藏失败\"}");
				}
			}

		}else if("deleteCollect".equals(type)) {
			int id=Integer.parseInt(request.getParameter("id"));
			try {
				exist=collectDao.deleteCollect(id);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
