package com.daoReconsitution.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoReconsitution.dao.SmallTypeDao;
import com.daoReconsitution.entity.SmallType;
import com.daoReconsitution.util.CommonUtil;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: SmallTypeServlet
 * @Description:TODO(商品小类模块Servlet)
 * @author: 韩豆豆
 * @date: 2020年4月11日 上午10:48:28
 * @context 1.分页查询(i.模糊查询,ii.全查询),2.添加,3.修改,4.批量删除,5.通过大类外键查询所有小类信息(商品添加，修改使用)
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
@WebServlet("/SmallTypeServlet")
public class SmallTypeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		SmallType smallType = new SmallType();
		SmallTypeDao smallTypeDao = new SmallTypeDao();
		JSONObject jsonObject = new JSONObject();
		boolean exist = false;
		int pageCount = 0;
		List<SmallType> list = null;
		String type = request.getParameter("type");
		if ("findAll".equals(type)) {// 分页查询
			String name = request.getParameter("name");
			int pageNow = Integer.parseInt(request.getParameter("page")); // 获取当前页码，easyui默认传到后台
			int pageSize = Integer.parseInt(request.getParameter("rows")); // 获取每页显示多少行，easyui默认传到后台
			if (name != null) {
				// 模糊查询
				try {
					pageCount = smallTypeDao.getTotal(name);
					list = smallTypeDao.getBigTypeByPage(pageSize, pageNow, name);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				// 全查询
				try {
					pageCount = smallTypeDao.getTotal();
					list = smallTypeDao.getBigTypeByPage(pageSize, pageNow);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			jsonObject = CommonUtil.toGridJson(pageCount, list);
			response.getWriter().print(jsonObject);
		} else if ("addSmallType".equals(type)) {// 添加
			int bigTypeId = Integer.parseInt(request.getParameter("bigTypeId"));
			String name = request.getParameter("name");
			String remarks = request.getParameter("remarks");
			smallType = new SmallType(0, name, bigTypeId, remarks, "");
			try {
				exist = smallTypeDao.addSmallType(smallType);
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
		} else if ("updateSmallType".equals(type)) {// 修改
			int id = Integer.parseInt(request.getParameter("productSmallTypeId"));
			int bigTypeId = Integer.parseInt(request.getParameter("bigTypeId"));
			String name = request.getParameter("name");
			String remarks = request.getParameter("remarks");
			smallType = new SmallType(id, name, bigTypeId, remarks, "");
			try {
				exist = smallTypeDao.updateSmallType(smallType);
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
		} else if ("deleteSmallType".equals(type)) {// 批量删除
			String[] s_ids = request.getParameterValues("ids");
			String[] ids = s_ids[0].split(",");
			try {
				smallTypeDao.deleteAllSmallType(ids);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonObject.put("success", true);
			response.getWriter().print(jsonObject);
		} else if ("findAllByBigTypeId".equals(type)) {// 通过大类外键查询所有小类信息
			String id = request.getParameter("bigTypeId");
			if (!"".equals(id) && id != null) {
				int bigTypeId = Integer.parseInt(request.getParameter("bigTypeId"));
				try {
					list = smallTypeDao.selectAllSmallTypeByBigTypeId(bigTypeId);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			JSONArray array = new JSONArray();
			if ("".equals(id)) {
				jsonObject.put("id", "");
				jsonObject.put("name", "请选择大类信息-----");
			} else {
				jsonObject.put("id", "");
				jsonObject.put("name", "请选择-----");
			}

			array.add(jsonObject);
			array.addAll(JSONArray.fromObject(list));
			response.getWriter().print(array);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
