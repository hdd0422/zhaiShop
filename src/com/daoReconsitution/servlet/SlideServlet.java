package com.daoReconsitution.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.daoReconsitution.dao.SlideDao;
import com.daoReconsitution.entity.Slide;
import com.daoReconsitution.util.CommonUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author 韩豆豆
 *
 * @title 幻灯片模块
 * 
 * @context 1.分页查询,2.修改
 */
@WebServlet("/SlideServlet")
public class SlideServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Slide slide = new Slide();
		SlideDao slideDao = new SlideDao();
		JSONObject jsonObject = new JSONObject();
		boolean exist = false;
		String type = request.getParameter("type");
		if ("findAll".equals(type)) {
			String title = request.getParameter("title");
			int pageNow = Integer.parseInt(request.getParameter("page")); // 获取当前页码，easyui默认传到后台
			int pageSize = Integer.parseInt(request.getParameter("rows")); // 获取每页显示多少行，easyui默认传到后台
			int pageCount = 0;
			List<Slide> list = null;
			if (title == null) {
				// 全查询
				try {
					pageCount = slideDao.getTotal();
					list = slideDao.getBigTypeByPage(pageSize, pageNow);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			jsonObject = CommonUtil.toGridJson(pageCount, list);
			response.getWriter().print(jsonObject);
		} else if ("updateSlide".equals(type)) {
			int id = Integer.parseInt(request.getParameter("id"));
			slide.setId(id);
			// 文件上传
			// 判断前台的form是否有 mutipart属性
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				try {
					List<FileItem> items = upload.parseRequest(request);
					Iterator<FileItem> iterator = items.iterator();
					while (iterator.hasNext()) {
						FileItem fileItem = (FileItem) iterator.next();
						// getFieldName()是获取表单字段名
						String fieldName = fileItem.getFieldName();
						// 判断前台字段 是普通form表单字段(sno sname)，还是文件字段
						if (fileItem.isFormField()) {// 表单字段
							if (fieldName.equals("name")) {// 根据name属性 判断item是name remarks 还是proPic?
								slide.setName(fileItem.getString("UTF-8"));
							} else if (fieldName.equals("url")) {
								slide.setUrl(fileItem.getString("UTF-8"));

							}
						} else {
							// 文件上传
							// 获取文件名 getName()是获取文件名
							String fileName = fileItem.getName();
							// 判断文件是否上传
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
							String path = "D:\\newworkspace\\zhaiShop\\WebContent\\images\\slide";
							// 设置缓冲区 10MB DiskFileItemFactory
							factory.setSizeThreshold(10 * 1024 * 1024);
							// 限制文件大小 20MB,ServletFileUpload
							upload.setFileSizeMax(20 * 1024 * 1024);
							File file = new File(path, fileName);
							fileItem.write(file);// 上传
							System.out.println("上传成功" + fileName);
							// 地址存入实体类
							slide.setProPic("images/slide/" + fileName);
						}
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (slide.getProPic() == null) {
					Slide temp=null;
					try {
						temp = slideDao.findSlide(slide.getId());
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					slide.setProPic(temp.getProPic());
				}
			}
			// ------------数据封装完毕-------------
			// 执行修改操作
			try {
				exist  = slideDao.update(slide);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (exist ) {
				jsonObject.put("success", true);

			} else {
				jsonObject.put("error", true);
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
