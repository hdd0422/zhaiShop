package com.daoReconsitution.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.daoReconsitution.dao.BigTypeDao;
import com.daoReconsitution.dao.GoodsDao;
import com.daoReconsitution.dao.SlideDao;
import com.daoReconsitution.dao.SmallTypeDao;
import com.daoReconsitution.entity.BigType;
import com.daoReconsitution.entity.Goods;
import com.daoReconsitution.entity.Slide;
import com.daoReconsitution.entity.SmallType;
@WebListener
public class MyApplicationListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		List<Slide> slideList =null;
		try {
			slideList = new SlideDao().selectAllslide();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletContext application = sce.getServletContext();
		application.setAttribute("slideList", slideList);
		application.setAttribute("floor", getData());
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
