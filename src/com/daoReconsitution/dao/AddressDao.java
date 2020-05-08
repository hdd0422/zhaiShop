/**  
 * All rights Reserved, Designed By 韩豆豆
 * @Title:  AddressDao.java   
 * @Package com.daoReconsitution.dao   
 * @Description:    TODO(地址模块dao)   
 * @author: 韩豆豆     
 * @date:   2020年4月17日 上午8:20:48   
 * @version V1.0 
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved. 
 */
package com.daoReconsitution.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.daoReconsitution.entity.Address;
import com.daoReconsitution.util.DBUtil;
import com.daoReconsitution.util.DataSourceUtil;

/**
 * @ClassName: AddressDao
 * @Description:TODO(地址模块dao)
 * @author: 韩豆豆
 * @date: 2020年4月17日 上午8:20:48
 * @context
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class AddressDao {
	private ResultSet rs = null;
	private Address temp = null;

	/**
	 * @Title: AddressDao
	 * @Description: TODO(根据用户id查找地址信息)
	 * @param: @param id
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Address> findAllInfo(int id) throws ClassNotFoundException, SQLException {
		List<Address> addresses = new ArrayList<Address>();
		String sql = "select * from t_address where userId=? order by id";
		try {
			rs = DBUtil.executeQuery(sql, new Object[] { id });
			while (rs.next()) {
				temp = new Address(rs.getBigDecimal("id"), rs.getString("province"), rs.getString("city"),
						rs.getString("area"), rs.getString("address"), rs.getString("phone"), rs.getString("username"),
						rs.getInt("msg"), rs.getInt("userId"));
				addresses.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return addresses;
	}

	/**
	 * @Title: AddressDao
	 * @Description: TODO(添加)
	 * @param: @param address
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int addInfo(Address address) throws ClassNotFoundException, SQLException {
		Connection ct=DataSourceUtil.getDataSourceWithC3P0().getConnection();
		String sql = "insert into t_address values(t_address_seq.nextval,?,?,?,?,?,?,?,?)";
		PreparedStatement ps=ct.prepareStatement(sql);
		ps.setString(1, address.getProvince());
		ps.setString(2, address.getCity());
		ps.setString(3, address.getArea());
		ps.setString(4, address.getAddress());
		ps.setString(5, address.getPhone());
		ps.setString(6, address.getUsername());
		ps.setInt(7, address.getMsg());
		ps.setInt(8, address.getUserId());
		ps.executeUpdate();
		 int id=0;
		 try {
			 sql="select t_address_seq.nextval from t_address ";
			rs=DBUtil.executeQuery(sql, new Object[] {});
			while (rs.next()) {
				id=rs.getInt(1);
			}
		} finally {
			DBUtil.closeAll(rs, ps, ct);
		}
		 return id;
	}

	/**
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Title: AddressDao
	 * @Description: TODO(删除)
	 * @param: @param id
	 */
	public boolean deleteAddress(Integer id) throws ClassNotFoundException, SQLException {
		String sql = "delete from t_address where id=?";
		return DBUtil.executeUpdate(sql, new Object[] { id });

	}

	/**   
	 * @Title:  AddressDao   
	 * @Description:    TODO(清除默认地址)   
	 * @param:  @param id    
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void clearDefault(int id) throws ClassNotFoundException, SQLException {
		String sql="update t_address set msg=0 where userId=?";
		DBUtil.executeUpdate(sql, new Object[] {id});
		
	}

	/**   
	 * @Title:  AddressDao   
	 * @Description:    TODO(设置默认地址)   
	 * @param:  @param id
	 * @param:  @param userId   
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean setDefault(Integer id, int userId) throws ClassNotFoundException, SQLException {
		String sql="update t_address set msg=1 where id=? and userId=?";
		return DBUtil.executeUpdate(sql, new Object[] {id,userId});
	}

}
