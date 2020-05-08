package com.daoReconsitution.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.daoReconsitution.entity.Order;
import com.daoReconsitution.entity.OrderItem;
import com.daoReconsitution.util.DBUtil;
import com.daoReconsitution.util.DateFormateUtil;

/**
 * 
 * @ClassName: OrderDao
 * @Description:TODO(订单dao层)
 * @author: 韩豆豆
 * @date: 2020年4月21日 上午9:02:24
 * @context
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class OrderDao {
	private ResultSet rs = null;
	private Order temp = null;

	// 全查询
	// 返回总记录数
	public int getTotal() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		try {
			String sql = "select count(*) from  t_order";
			rs = DBUtil.executeQuery(sql, new Object[] {});
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);

		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
	}

	// 返回每页记录数
	public List<Order> getBigTypeByPage(int pageSize, int pageNow) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Order> al = new ArrayList<Order>();

		try {
			String sql = "select *from(select al.*,rownum rn from(select t1.*,t2.province,t2.city,t2.area,t2.address,t2.phone,t3.userName,t3.trueName from t_order t1,t_address t2,t_user t3 where t1.addressId=t2.id and t1.userId=t3.id order by t1.id) al where rownum<=?) where rn>=?";
			rs = DBUtil.executeQuery(sql, new Object[] { pageSize * pageNow, pageSize * (pageNow - 1) + 1 });

			while (rs.next()) {
				// 循环读取数据,封装到Order对象--存放到ArrayList集合【二次封装】
				temp = new Order(rs.getString("id"), rs.getInt("userId"), rs.getFloat("total"), rs.getInt("addressId"),
						rs.getString("remarks"), DateFormateUtil.formatDate(rs.getDate("time")), rs.getInt("state"),
						rs.getString("trueName"), rs.getString("province"), rs.getString("city"), rs.getString("area"),
						rs.getString("address"), rs.getString("phone"), rs.getString("username"));
				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

	// 多个条件查询
	public int getTotal(List<Object> params) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			StringBuffer sql = new StringBuffer(
					"select count(*) from t_order t1,t_address t2,t_user t3 where t1.addressId=t2.id and t1.userId=t3.id ");
			if (params.get(0) != null && !"".equals(params.get(0))) {
				sql.append("and t3.userName like '%" + params.get(0) + "%'");
			}
			if (params.get(1) != null && !"".equals(params.get(1))) {
				sql.append("and t1.id like '%" + params.get(1) + "%'");
			}
			rs = DBUtil.executeQuery(sql.toString(), new Object[] {});
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);

		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
	}

	public List<Order> getBigTypeByPage(int pageSize, int pageNow, List<Object> params)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Order> al = new ArrayList<Order>();

		try {
			StringBuffer s_sql = new StringBuffer(
					"select t1.*,t2.province,t2.city,t2.area,t2.address,t2.phone,t3.userName,t3.trueName from t_order t1,t_address t2,t_user t3 where t1.addressId=t2.id and t1.userId=t3.id ");
			if (params.get(0) != null && !"".equals(params.get(0))) {
				s_sql.append("and t3.userName like '%" + params.get(0) + "%'");
			}
			if (params.get(1) != null && !"".equals(params.get(1))) {
				s_sql.append("and t1.id like '%" + params.get(1) + "%'");
			}
			String sql = "select *from(select al.*,rownum rn from(" + s_sql.toString()
					+ ") al where rownum<=?) where rn>=?";
			rs = DBUtil.executeQuery(sql, new Object[] { pageSize * pageNow, pageSize * (pageNow - 1) + 1 });

			while (rs.next()) {
				// 循环读取数据,封装到Order对象--存放到ArrayList集合【二次封装】
				temp = new Order(rs.getString("id"), rs.getInt("userId"), rs.getFloat("total"), rs.getInt("addressId"),
						rs.getString("remarks"), DateFormateUtil.formatDate(rs.getDate("time")), rs.getInt("state"),
						rs.getString("trueName"), rs.getString("province"), rs.getString("city"), rs.getString("area"),
						rs.getString("address"), rs.getString("phone"), rs.getString("username"));
				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

	// 查询单个订单商品的详细信息
	public List<OrderItem> findOrderItemByOrderId(String orderNo) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<OrderItem> al = new ArrayList<OrderItem>();
		try {
			String sql = "select * from t_orderItem where orderId=? ";
			rs = DBUtil.executeQuery(sql, new Object[] { orderNo });
			while (rs.next()) {
				// 循环读取数据,封装到OrderItem对象--存放到ArrayList集合【二次封装】
				OrderItem orderItem = new OrderItem(rs.getInt("id"), rs.getInt("goodsId"), rs.getString("goodsName"),
						rs.getString("proPic"), rs.getFloat("goodsPrice"), rs.getInt("sum"), rs.getFloat("subTotal"),
						rs.getString("orderId"));
				// 添加到集合里
				al.add(orderItem);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
		return al;
	}

	// 修改多个订单的状态
	public void updateOrderState(String[] temp) throws ClassNotFoundException, SQLException {
		for (String id : temp) {
			String sql = "update t_order set State=3 where id=?";
			DBUtil.executeUpdate(sql, new Object[] { id });
		}

	}

	/**
	 * @throws SQLException @throws ClassNotFoundException @Title:
	 * OrderDao @Description: TODO(添加订单表) @param: @param order @throws
	 */
	public void addOrder(Order order) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_order values(?,?,?,?,?,to_date(?,'yyyy-mm-dd'),?)";
		DBUtil.executeUpdate(sql, new Object[] { order.getId(), order.getUserId(), order.getTotal(),
				order.getAddressId(), order.getRemarks(), order.getTime(), order.getState() });
	}

	/**
	 * @throws SQLException @throws ClassNotFoundException @Title:
	 * OrderDao @Description: TODO(添加订单表) @param: @param orderItem @throws
	 */
	public void addOrderItem(OrderItem orderItem) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_orderItem values(t_orderItem_seq.nextval,?,?,?,?,?,?,?)";
		DBUtil.executeUpdate(sql,
				new Object[] { orderItem.getGoodsId(), orderItem.getGoodsName(), orderItem.getProPic(),
						orderItem.getGoodsPrice(), orderItem.getSum(), orderItem.getSubTotal(),
						orderItem.getOrderId() });
	}

}
