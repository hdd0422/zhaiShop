package com.daoReconsitution.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.daoReconsitution.dao.GoodsDao;
import com.daoReconsitution.entity.BigType;
import com.daoReconsitution.entity.Goods;
import com.daoReconsitution.util.CommonUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author 韩豆豆
 *
 * @title 
 * 
 * @context 
 */
/**
 * 
 * @ClassName: GoodsServlet
 * @Description:TODO(商品模块Servlet)
 * @author: 韩豆豆
 * @date: 2020年4月11日 上午10:06:55
 * @context 1.分页查询(i.模糊查询,ii.全查询),2.添加,3.修改,4.批量删除
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
@WebServlet("/GoodsServlet")
public class GoodsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Goods goods = new Goods();
		GoodsDao goodsDao = new GoodsDao();
		JSONObject jsonObject = new JSONObject();
		boolean exist = false;
		int pageCount = 0;
		List<Goods> list = null;
		String type = request.getParameter("type");
		if ("findAll".equals(type)) {// 分页查询
			String name = request.getParameter("name");
			int pageNow = Integer.parseInt(request.getParameter("page")); // 获取当前页码，easyui默认传到后台
			int pageSize = Integer.parseInt(request.getParameter("rows")); // 获取每页显示多少行，easyui默认传到后台
			if (name != null) {
				// 模糊查询
				try {
					pageCount = goodsDao.getTotal(name);
					list = goodsDao.getBigTypeByPage(pageSize, pageNow, name);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				// 全查询
				try {
					pageCount = goodsDao.getTotal();
					list = goodsDao.getBigTypeByPage(pageSize, pageNow);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			jsonObject = CommonUtil.toGridJson(pageCount, list);
			response.getWriter().print(jsonObject);
			request.getRequestDispatcher("/admin/productManage.jsp");
		} else if ("addGoods".equals(type)) {// 添加
			// 文件上传
			// 判断前台的form是否有 mutipart属性
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items;
				try {
					items = upload.parseRequest(request);
					Iterator<FileItem> iterator = items.iterator();
					while (iterator.hasNext()) {
						FileItem fileItem = (FileItem) iterator.next();
						// getFieldName()是获取表单字段名
						String fieldName = fileItem.getFieldName();
						// 判断前台字段 是普通form表单字段(name,price,brand,sales,views,stock,cotents,bigTypeId,
						// smallTypeId,state)，还是文件字段
						if (fileItem.isFormField()) {// 表单字段
							if (fieldName.equals("name")) {// 根据name属性
															// 判断item是name,price,brand,sales,views,stock,cotents,bigTypeId,
															// smallTypeId,state 还是proPic?
								goods.setName(fileItem.getString("UTF-8"));// --商品名称
							} else if (fieldName.equals("price")) {
								goods.setPrice(Float.parseFloat(fileItem.getString("UTF-8")));// --商品价格
							} else if (fieldName.equals("brand")) {
								goods.setBrand(fileItem.getString("UTF-8"));// 商品品牌
							} else if (fieldName.equals("sales")) {
								goods.setSales(Integer.parseInt(fileItem.getString("UTF-8")));// 商品销量
							} else if (fieldName.equals("views")) {
								goods.setViews(Integer.parseInt(fileItem.getString("UTF-8")));// 商品浏览量
							} else if (fieldName.equals("stock")) {
								goods.setStock(Integer.parseInt(fileItem.getString("UTF-8")));// 商品库存
							} else if (fieldName.equals("cotents")) {
								goods.setContents(fileItem.getString("UTF-8"));// 商品描述
							} else if (fieldName.equals("bigTypeId")) {
								goods.setBigTypeId(Integer.parseInt(fileItem.getString("UTF-8")));// 大类ID
							} else if (fieldName.equals("smallTypeId")) {
								goods.setSmallTypeId(Integer.parseInt(fileItem.getString("UTF-8")));// 小类ID
							} else if (fieldName.equals("state")) {
								goods.setState(fileItem.getString("UTF-8"));// 状态
							}
						} else {// 文件上传
							// 获取文件名 getName()是获取文件名
							String fileName = fileItem.getName();
							// 限制文件格式,必须是gif，png,jpg
							String limit = fileName.substring(fileName.indexOf(".") + 1);
							if (!(limit.equalsIgnoreCase("png") || limit.equalsIgnoreCase("gif")
									|| limit.equalsIgnoreCase("jpg"))) {
								System.out.println("照片格式不对");
								return;
							}
							// 获取文件内容
							// 定义文件路径，指定上传位置(服务器路径)
							// 获取项目路径
							String path = "D:\\newworkspace\\zhaiShop\\WebContent\\images\\goodsImg";
							// 设置缓冲区 10MB DiskFileItemFactory
							factory.setSizeThreshold(10 * 1024 * 1024);
							// 限制文件大小 20MB,ServletFileUpload
							upload.setFileSizeMax(20 * 1024 * 1024);
							File file = new File(path, fileName);
							fileItem.write(file);// 上传
							System.out.println("上传成功" + fileName);
							// 地址存入实体类
							goods.setProPic("images/goodsImg/" + fileName);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// ------------数据封装完毕-------------
			// 执行添加操作
			try {
				exist = goodsDao.addGoods(goods);
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
			request.getRequestDispatcher("/admin/productManage.jsp");
		} else if ("updateGoods".equals(type)) {// 修改
			goods.setId(Integer.parseInt(request.getParameter("id")));
			// 文件上传
			// 判断前台的form是否有 mutipart属性
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items;
				try {
					items = upload.parseRequest(request);
					Iterator<FileItem> iterator = items.iterator();
					while (iterator.hasNext()) {
						FileItem fileItem = (FileItem) iterator.next();
						// getFieldName()是获取表单字段名
						String fieldName = fileItem.getFieldName();
						// 判断前台字段 是普通form表单字段(name,price,brand,sales,views,stock,cotents,bigTypeId,
						// smallTypeId,state)，还是文件字段
						if (fileItem.isFormField()) {// 表单字段
							if (fieldName.equals("name")) {// 根据name属性
															// 判断item是name,price,brand,sales,views,stock,cotents,bigTypeId,
															// smallTypeId,state 还是proPic?
								goods.setName(fileItem.getString("UTF-8"));// --商品名称
							} else if (fieldName.equals("price")) {
								goods.setPrice(Float.parseFloat(fileItem.getString("UTF-8")));// --商品价格
							} else if (fieldName.equals("brand")) {
								goods.setBrand(fileItem.getString("UTF-8"));// 商品品牌
							} else if (fieldName.equals("sales")) {
								goods.setSales(Integer.parseInt(fileItem.getString("UTF-8")));// 商品销量
							} else if (fieldName.equals("views")) {
								goods.setViews(Integer.parseInt(fileItem.getString("UTF-8")));// 商品浏览量
							} else if (fieldName.equals("stock")) {
								goods.setStock(Integer.parseInt(fileItem.getString("UTF-8")));// 商品库存
							} else if (fieldName.equals("cotents")) {
								goods.setContents(fileItem.getString("UTF-8"));// 商品描述
							} else if (fieldName.equals("bigTypeId")) {
								goods.setBigTypeId(Integer.parseInt(fileItem.getString("UTF-8")));// 大类ID
							} else if (fieldName.equals("smallTypeId")) {
								goods.setSmallTypeId(Integer.parseInt(fileItem.getString("UTF-8")));// 小类ID
							} else if (fieldName.equals("state")) {
								goods.setState(fileItem.getString("UTF-8"));// 状态
							}
						} else {// 文件上传
							// 获取文件名 getName()是获取文件名
							String fileName = fileItem.getName();
							// 判断是否有文件
							if (fileName.equals("")) {
								continue;
							}
							// 限制文件格式,必须是gif，png,jpg
							String limit = fileName.substring(fileName.indexOf(".") + 1);
							if (!(limit.equalsIgnoreCase("png") || limit.equalsIgnoreCase("gif")
									|| limit.equalsIgnoreCase("jpg"))) {
								System.out.println("照片格式不对");
								return;
							}
							// 获取文件内容
							// 定义文件路径，指定上传位置(服务器路径)
							// 获取项目路径
							String path = "D:\\newworkspace\\zhaiShop\\WebContent\\images\\goodsImg";
							// 设置缓冲区 10MB DiskFileItemFactory
							factory.setSizeThreshold(10 * 1024 * 1024);
							// 限制文件大小 20MB,ServletFileUpload
							upload.setFileSizeMax(20 * 1024 * 1024);
							File file = new File(path, fileName);
							fileItem.write(file);// 上传
							System.out.println("上传成功" + fileName);
							// 地址存入实体类
							goods.setProPic("images/goodsImg/" + fileName);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// 判断是否有文件名存入goods，若没有,则查询以前的数据
			if (goods.getProPic() == null) {
				Goods temp = null;
				try {
					temp = goodsDao.selectGoods(goods.getId());
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				goods.setProPic(temp.getProPic());
			}
			// ------------数据封装完毕-------------
			// 执行添加操作
			try {
				exist = goodsDao.updateGoods(goods);
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
			request.getRequestDispatcher("/admin/productManage.jsp");
		} else if ("deleteGoods".equals(type)) {
			String[] s_ids = request.getParameterValues("ids");
			String[] ids = s_ids[0].split(",");
			try {
				goodsDao.deleteAll(ids);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonObject.put("success", true);
			response.getWriter().print(jsonObject);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
