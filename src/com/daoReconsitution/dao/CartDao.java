/**  
 * All rights Reserved, Designed By 韩豆豆
 * @Title:  CartDao.java   
 * @Package com.daoReconsitution.dao   
 * @Description:    TODO(购物车dao层)   
 * @author: 韩豆豆     
 * @date:   2020年4月23日 下午2:09:25   
 * @version V1.0 
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved. 
 */
package com.daoReconsitution.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.daoReconsitution.entity.Cart;
import com.daoReconsitution.util.DBUtil;

/**
 * @ClassName: CartDao
 * @Description:TODO(购物车dao层)
 * @author: 韩豆豆
 * @date: 2020年4月23日 下午2:09:25
 * @context
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class CartDao {
	private ResultSet rs = null;
	private Cart temp = null;

	public int count(int userId, int goodsId) throws ClassNotFoundException, SQLException {
		String sql = "select num from t_cart where userId=? and goodsId=?";
		int count = 0;
		try {
			rs = DBUtil.executeQuery(sql, new Object[] { userId, goodsId });
			if (rs.next()) {
				count = rs.getInt("num");
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
		return count;
	}

	/**
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Title: CartDao
	 * @Description: TODO(修改数量)
	 * @param: @param cart
	 */
	public boolean updateCart(Cart cart) throws ClassNotFoundException, SQLException {
		String sql = "update  t_cart set num=? where userId=? and goodsId=?";
		return DBUtil.executeUpdate(sql, new Object[] { cart.getNum(), cart.getUserId(), cart.getGoodsId() });
	}

	/**
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Title: CartDao
	 * @Description: TODO(添加)
	 * @param: @param cart
	 */
	public boolean addCart(Cart cart) throws ClassNotFoundException, SQLException {
		String sql = "insert into t_cart values(?,?,?,?)";
		return DBUtil.executeUpdate(sql,
				new Object[] { cart.getUserId(), cart.getGoodsId(), cart.getNum(), cart.getGoodsPrice() });
	}

	/**
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @param rs
	 * @Title: CartDao
	 * @Description: TODO(查询所有)
	 * @param: @param id
	 */
	public List<Cart> findAll(int id) throws ClassNotFoundException, SQLException {
		List<Cart> carts = new ArrayList<Cart>();
		String sql = "select * from t_cart where userId=?";
		try {
			rs = DBUtil.executeQuery(sql, new Object[] { id });
			while (rs.next()) {
				Cart cart = new Cart(rs.getBigDecimal("userId"), rs.getBigDecimal("goodsId"), rs.getBigDecimal("num"),
						rs.getBigDecimal("goodsPrice"));
				carts.add(cart);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return carts;
	}

	/**
	 * @Title: CartDao
	 * @Description: TODO(删除)
	 * @param: @param parseInt
	 */
	public boolean deleteCart(int goodsId, int userId) throws ClassNotFoundException, SQLException {
		String sql = "delete from t_cart where goodsId=? and userId=?";
		return DBUtil.executeUpdate(sql, new Object[] { goodsId, userId });
	}

	/**
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Title: CartDao @Description: TODO(查询单个商品信息) @param: @param
	 *         id @param: @return @throws
	 */
	public Cart selectCart(int goodsId, int userId) throws SQLException, ClassNotFoundException {
		String sql = "select * from t_cart where goodsId=? and userId=?";
		try {
			rs = DBUtil.executeQuery(sql, new Object[] { goodsId, userId });
			while (rs.next()) {
				temp = new Cart(rs.getBigDecimal("userId"), rs.getBigDecimal("goodsId"), rs.getBigDecimal("num"),
						rs.getBigDecimal("goodsPrice"));
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
		return temp;
	}

}
