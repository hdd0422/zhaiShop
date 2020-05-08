package com.daoReconsitution.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.daoReconsitution.entity.User;
import com.daoReconsitution.util.DBUtil;
import com.daoReconsitution.util.DataSourceUtil;
import com.daoReconsitution.util.DateFormateUtil;

/**
 * 
 * @ClassName: UserDao
 * @Description:TODO(用户管理模块dao层)
 * @author: 韩豆豆
 * @date: 2020年4月8日 下午12:10:26
 * @context 使用工具类封装，简化代码 。方法(后台登录,分页查询,添加,修改,批量删除,模糊分页查询,验证,前台登录)
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class UserDao {
	private User temp = null;
	private ResultSet rs = null;

	// 后台登录
	public User check(User user) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from t_user where userName = ? and password = ? and userType=0";
			ResultSet rs = DBUtil.executeQuery(sql, new Object[] { user.getUserName(), user.getPassword() });
			if (rs.next()) {
				temp = new User(rs.getInt("id"), rs.getString("userName"), rs.getString("trueName"),
						rs.getString("sex"), DateFormateUtil.formatDate(rs.getDate("birthday")),
						rs.getString("statusID"), rs.getString("phone"), rs.getString("address"), rs.getString("email"),
						rs.getInt("userType"), rs.getString("password"));
				return temp;
			} else {
				return null;
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

	}

	// easyUI分页查询所有用户信息
	// 返回总记录数
	public int getTotal() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select count(*) from  t_user";
			rs = DBUtil.executeQuery(sql, new Object[] {});
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
	}

	// 返回每页记录数
	public List<User> getUsersByPage(int pageSize, int pageNow) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		ArrayList<User> al = new ArrayList<User>();
		try {
			String sql = "select *from(select al.*,rownum rn from(select * from  t_user order by id) al where rownum<=? ) where rn>=?";
			rs = DBUtil.executeQuery(sql, new Object[] { pageSize * pageNow, (pageSize * (pageNow - 1) + 1) });
			while (rs.next()) {
				// 循环读取数据,封装到Users对象--存放到ArrayList集合【二次封装】
				temp = new User(rs.getInt("id"), rs.getString("userName"), rs.getString("trueName"),
						rs.getString("sex"), DateFormateUtil.formatDate(rs.getDate("birthday")),
						rs.getString("statusID"), rs.getString("phone"), rs.getString("address"), rs.getString("email"),
						rs.getInt("userType"), rs.getString("password"));

				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;

	}

	// 添加
	public boolean addUser(User user) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_user values(t_user_seq.nextval,?,?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?) ";
		return DBUtil.executeUpdate(sql,
				new Object[] { user.getUserName(), user.getTrueName(), user.getSex(), user.getBirthday(),
						user.getStatusID(), user.getPhone(), user.getAddress(), user.getEmail(), user.getUserType(),
						user.getPassword() });

	}

	// 修改
	public boolean updateUser(User user) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "update t_user set userName=?,trueName=?,sex=?,birthday=to_date(?,'yyyy-mm-dd'),statusID=?,phone=?,address=?,email=?,password=? where id=?";
		return DBUtil.executeUpdate(sql,
				new Object[] { user.getUserName(), user.getTrueName(), user.getSex(), user.getBirthday(),
						user.getStatusID(), user.getPhone(), user.getAddress(), user.getEmail(), user.getPassword(),
						user.getId() });
	}

	// 批量删除
	public void deleteAll(String[] ids) throws ClassNotFoundException, SQLException {

		for (String id : ids) {
			String sql = "delete from t_user where id=?";
			DBUtil.executeUpdate(sql, new Object[] { id });
		}
	}

	// 模糊分页查询
	// 返回总记录数
	public int getTotal(String userName) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select count(*) from  t_user where userName like ?";
			rs = DBUtil.executeQuery(sql, new Object[] { "%" + userName + "%" });
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

	}

	// 返回每页记录数
	public List<User> getUsersByPage(int pageSize, int pageNow, String userName)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		ArrayList<User> al = new ArrayList<User>();
		try {
			String sql = "select *from(select al.*,rownum rn from(select * from  t_user where userName like ? order by id) al where rownum<= ?) where rn>= ?";
			rs = DBUtil.executeQuery(sql,
					new Object[] { "%" + userName + "%", pageSize * pageNow, (pageSize * (pageNow - 1) + 1) });
			while (rs.next()) {
				// 循环读取数据,封装到Users对象--存放到ArrayList集合【二次封装】
				temp = new User(rs.getInt("id"), rs.getString("userName"), rs.getString("trueName"),
						rs.getString("sex"), DateFormateUtil.formatDate(rs.getDate("birthday")),
						rs.getString("statusID"), rs.getString("phone"), rs.getString("address"), rs.getString("email"),
						rs.getInt("userType"), rs.getString("password"));
				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

	// 验证用户名是否在数据库存在
	public User checkName(String userName) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from t_user where userName = ?";
			 rs = DBUtil.executeQuery(sql, new Object[] { userName });
			if (rs.next()) {
				temp = new User(rs.getString("userName"), rs.getString("password"));
				return temp;
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return null;
	}

	// 前台登录
	public User mainLogin(User user) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from t_user where userName = ? and password = ?";
			 rs = DBUtil.executeQuery(sql, new Object[] { user.getUserName(), user.getPassword() });
			if (rs.next()) {
				temp = new User(rs.getString("userName"), rs.getString("password"));
				temp.setId(rs.getInt("id"));
				return temp;
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return null;
	}

}
