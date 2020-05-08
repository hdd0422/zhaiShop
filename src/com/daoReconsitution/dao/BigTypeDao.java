package com.daoReconsitution.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.daoReconsitution.entity.BigType;
import com.daoReconsitution.util.DBUtil;
import com.daoReconsitution.util.DateFormateUtil;

/**
 * 
 * @ClassName: BigTypeDao
 * @Description:TODO(商品大类dao层)
 * @author: 韩豆豆
 * @date: 2020年4月11日 上午8:38:10
 * @context 使用工具类封装，简化代码 。
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class BigTypeDao {
	private ResultSet rs = null;
	private BigType temp = null;

	// 全查询
	// 返回总记录数
	public int getTotal() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		try {
			String sql = "select count(*) from  t_bigType";
			rs = DBUtil.executeQuery(sql, new Object[] {});
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);

		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
	}

	// 返回每页记录数
	public List<BigType> getBigTypeByPage(int pageSize, int pageNow) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<BigType> al = new ArrayList<BigType>();

		try {
			String sql = "select *from(select al.*,rownum rn from(select * from  t_bigType order by id) al where rownum<=?) where rn>=?";
			rs = DBUtil.executeQuery(sql, new Object[] { pageSize * pageNow, (pageSize * (pageNow - 1) + 1) });
			while (rs.next()) {
				// 循环读取数据,封装到BigType对象--存放到ArrayList集合【二次封装】
				temp = new BigType(rs.getInt("id"), rs.getString("name"), rs.getString("remarks"),
						rs.getString("proPic"));
				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

	// 模糊查询
	// 返回总记录数
	public int getTotal(String name) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		try {
			String sql = "select count(*) from  t_bigType where name like ?";
			rs = DBUtil.executeQuery(sql, new Object[] { "%" + name + "%" });
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
		return 0;
	}

	// 返回每页记录数
	public List<BigType> getBigTypeByPage(int pageSize, int pageNow, String name)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<BigType> al = new ArrayList<BigType>();

		try {
			String sql = "select *from(select al.*,rownum rn from(select * from  t_bigType where name like ? order by id) al where rownum<=?) where rn>=?";
			rs = DBUtil.executeQuery(sql,
					new Object[] { "%" + name + "%", pageSize * pageNow, (pageSize * (pageNow - 1) + 1) });

			while (rs.next()) {
				// 循环读取数据,封装到BigType对象--存放到ArrayList集合【二次封装】
				temp = new BigType(rs.getInt("id"), rs.getString("name"), rs.getString("remarks"),
						rs.getString("proPic"));
				// 添加到集合里
				al.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

	// 添加
	public boolean addBigType(BigType bigType) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_bigType values(t_bigType_seq.nextval,?,?,?) ";
		return DBUtil.executeUpdate(sql, new Object[] { bigType.getName(), bigType.getRemarks(), bigType.getProPic() });

	}

	// 根据id查询
	public BigType findBigType(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from t_bigType where id=? ";
			rs = DBUtil.executeQuery(sql, new Object[] { id });
			if (rs.next()) {
				temp = new BigType(rs.getInt("id"), rs.getString("name"), rs.getString("remarks"),
						rs.getString("proPic"));
			}
			return temp;
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
	}

	// 修改
	public boolean update(BigType bigType) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "update t_bigType set name=?,remarks=?,proPic=? where id=? ";
		return DBUtil.executeUpdate(sql,
				new Object[] { bigType.getName(), bigType.getRemarks(), bigType.getProPic(), bigType.getId() });

	}

	public void deleteAll(String[] ids) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		for (String id : ids) {
			String sql = "delete from t_bigType where id=?";
			DBUtil.executeUpdate(sql, new Object[] { id });
		}
	}

	// 商品大类信息全查询
	public List<BigType> selectAllBigType() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<BigType> al = new ArrayList<BigType>();

		try {
			String sql = "select * from  t_bigType order by id";
			rs = DBUtil.executeQuery(sql, new Object[] {});

			while (rs.next()) {
				// 循环读取数据,封装到BigType对象--存放到ArrayList集合【二次封装】
				temp = new BigType(rs.getInt("id"), rs.getString("name"), rs.getString("remarks"),
						rs.getString("proPic"));
				// 添加到集合里
				al.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

}
