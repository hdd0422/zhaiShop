/**  
 * All rights Reserved, Designed By 韩豆豆
 * @Title:  CollectDao.java   
 * @Package com.daoReconsitution.dao   
 * @Description:    TODO(收藏模块dao)   
 * @author: 韩豆豆     
 * @date:   2020年4月14日 上午8:46:56   
 * @version V1.0 
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved. 
 */
package com.daoReconsitution.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.daoReconsitution.entity.Collect;
import com.daoReconsitution.util.DBUtil;
import com.daoReconsitution.util.DateFormateUtil;

/**
 * @ClassName: CollectDao
 * @Description:TODO(收藏模块dao)
 * @author: 韩豆豆
 * @date: 2020年4月14日 上午8:46:56
 * @context
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class CollectDao {
	private Collect temp = null;
	private ResultSet rs = null;

	/**
	 * @param userId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Title: CollectDao @Description: TODO(分页查询页面大小) @param: @param pageSize
	 *         每页显示记录数 @param: @param pageNow 当前页面
	 */
	public List<Collect> findAllInfo(int pageSize, int pageNow, int userId)
			throws ClassNotFoundException, SQLException {
		List<Collect> collects = new ArrayList<Collect>();
		String sql = "select *from(select al.*,rownum rn from(select * from  t_collect where userId=? order by id) al where rownum<=? ) where rn>=?";
		try {
			rs = DBUtil.executeQuery(sql, new Object[] { userId, pageSize * pageNow, (pageSize * (pageNow - 1) + 1) });
			while (rs.next()) {
				temp = new Collect(rs.getBigDecimal("id"), rs.getBigDecimal("userId"), rs.getBigDecimal("goodsId"),
						rs.getString("goodsName"), rs.getString("goodsProPic"), rs.getBigDecimal("goodsPrice"),
						DateFormateUtil.formatDate(rs.getDate("time")));
				collects.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return collects;
	}

	/**
	 * @param userId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Title: CollectDao @Description: TODO(总页数) @param: @param pageSize 每页显示记录数
	 */
	public int getTotal(int pageSize, int userId) throws ClassNotFoundException, SQLException {
		int rowCount = 0;
		String sql = "select count(1) from  t_collect where userId=? order by id";
		try {
			rs = DBUtil.executeQuery(sql, new Object[] { userId });
			rs.next();// 游标下移,一定不能忘
			rowCount = rs.getInt(1);
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		// 计算pageCount
		return (rowCount - 1) / pageSize + 1;

	}

	/**
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Title: CollectDao
	 * @Description: TODO(添加)
	 * @param: @param collect
	 */
	public boolean addCollect(Collect collect) throws ClassNotFoundException, SQLException {
		String sql = "insert into t_collect values (t_collect_seq.nextval, ?, ?, ?, ?, ?, ?)";
		// Object[] o=new Object[]
		// {collect.getUserId(),collect.getGoodsId(),collect.getGoodsName(),collect.getGoodsProPic(),collect.getGoodsPrice(),new
		// java.sql.Date(new Date().getTime())};
		//System.out.println(new java.sql.Date(new Date().getTime()));
		return DBUtil.executeUpdate(sql,
				new Object[] { collect.getUserId(), collect.getGoodsId(), collect.getGoodsName(),
						collect.getGoodsProPic(), collect.getGoodsPrice(), new java.sql.Date(new Date().getTime()) });
	}

	/**
	 * @Title: CollectDao
	 * @Description: TODO(用商品外键查询收藏信息)
	 * @param: @param id
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Collect findInfo(int id, int userId) throws ClassNotFoundException, SQLException {
		String sql = "select * from  t_collect where goodsId=? and userId=? order by id";
		try {
			rs = DBUtil.executeQuery(sql, new Object[] { id, userId });
			while (rs.next()) {
				temp = new Collect(rs.getBigDecimal("id"), rs.getBigDecimal("userId"), rs.getBigDecimal("goodsId"),
						rs.getString("goodsName"), rs.getString("goodsProPic"), rs.getBigDecimal("goodsPrice"),
						DateFormateUtil.formatDate(rs.getDate("time")));
			}
			return temp;
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
	}

	/**   
	 * @Title:  CollectDao   
	 * @Description:    TODO(删除)   
	 * @param:  @param id  
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean deleteCollect(int id) throws ClassNotFoundException, SQLException {
		String sql = "delete from t_collect where goodsId=?";
		return DBUtil.executeUpdate(sql, new Object[] {id});
	}
}
