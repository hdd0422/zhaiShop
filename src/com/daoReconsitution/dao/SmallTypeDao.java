package com.daoReconsitution.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.daoReconsitution.entity.SmallType;
import com.daoReconsitution.util.DBUtil;

/**
 * 
 * @ClassName: SmallTypeDao
 * @Description:TODO(商品小类dao层)
 * @author: 韩豆豆
 * @date: 2020年4月11日 上午9:30:02
 * @context 使用工具类封装，简化代码 。
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class SmallTypeDao {
	private ResultSet rs = null;
	private SmallType temp = null;

	// 全查询
	// 返回总记录数
	public int getTotal() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select count(*) from  t_smallType";
			rs = DBUtil.executeQuery(sql, new Object[] {});
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
	public List<SmallType> getBigTypeByPage(int pageSize, int pageNow) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<SmallType> al = new ArrayList<SmallType>();
		try {
			String sql = "select *from(select al.*,rownum rn from(select t1.*,t2.name bigTypeName from t_smallType t1,t_bigType t2 where t1.bigTypeId=t2.id order by t1.id) al where rownum<=?) where rn>=?";
			rs = rs = DBUtil.executeQuery(sql, new Object[] { pageSize * pageNow, (pageSize * (pageNow - 1) + 1) });

			while (rs.next()) {
				// 循环读取数据,封装到SmallType对象--存放到ArrayList集合【二次封装】
				temp = new SmallType(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
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
	public int getTotal(String name) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select count(*) from  t_smallType where name like ?";
			rs = DBUtil.executeQuery(sql, new Object[] { "%" + name + "%" });
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);

		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
	}

	// 返回每页记录数
	public List<SmallType> getBigTypeByPage(int pageSize, int pageNow, String name)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<SmallType> al = new ArrayList<SmallType>();

		try {
			String sql = "select *from(select al.*,rownum rn from(select t1.*,t2.name bigTypeName from t_smallType t1,t_bigType t2 where t1.bigTypeId=t2.id and t1.name like ? order by t1.id) al where rownum<=?) where rn>=?";
			rs = DBUtil.executeQuery(sql,
					new Object[] { "%" + name + "%", pageSize * pageNow, (pageSize * (pageNow - 1) + 1) });
			while (rs.next()) {
				// 循环读取数据,封装到SmallType对象--存放到ArrayList集合【二次封装】
				temp = new SmallType(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

	// 添加
	public boolean addSmallType(SmallType smallType) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_smallType values(t_smallType_seq.nextval,?,?,?) ";
		return DBUtil.executeUpdate(sql,
				new Object[] { smallType.getName(), smallType.getBigTypeId(), smallType.getRemarks() });
	}

	// 修改
	public boolean updateSmallType(SmallType smallType) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "update t_smallType set name=?,bigTypeId=?,remarks=? where id=? ";
		return DBUtil.executeUpdate(sql, new Object[] { smallType.getName(), smallType.getBigTypeId(),
				smallType.getRemarks(), smallType.getId() });
	}

	// 删除
	public void deleteAllSmallType(String[] ids) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		for (String id : ids) {
			String sql = "delete from t_smallType where id=?";
			DBUtil.executeUpdate(sql, new Object[] { id });
		}
	}

	// 查询所有商品小类 信息
	public List<SmallType> selectAllSmallType() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<SmallType> al = new ArrayList<SmallType>();
		try {
			String sql = "select t1.*,t2.name bigTypeName from t_smallType t1,t_bigType t2 where t1.bigTypeId=t2.id order by t1.id";
			rs = DBUtil.executeQuery(sql, new Object[] {});

			while (rs.next()) {
				// 循环读取数据,封装到SmallType对象--存放到ArrayList集合【二次封装】
				temp = new SmallType(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

	// 以商品大类id为条件查询所有商品小类 信息
	public List<SmallType> selectAllSmallTypeByBigTypeId(int bigTypeId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<SmallType> al = new ArrayList<SmallType>();
		try {
			String sql = "select t1.*,t2.name bigTypeName from t_smallType t1,t_bigType t2 where t1.bigTypeId=t2.id and bigTypeId=? order by t1.id";
			rs = DBUtil.executeQuery(sql, new Object[] { bigTypeId });

			while (rs.next()) {
				// 循环读取数据,封装到SmallType对象--存放到ArrayList集合【二次封装】
				temp = new SmallType(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

	//查询单个商品小类信息
	public SmallType findSmallType(int sid) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select t1.*,t2.name bigTypeName from t_smallType t1,t_bigType t2 where t1.bigTypeId=t2.id and t1.id =? ";
			rs = DBUtil.executeQuery(sql, new Object[] { sid });

			while (rs.next()) {
				// 循环读取数据,封装到SmallType对象--存放到ArrayList集合【二次封装】
				temp = new SmallType(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
		return null;
	}

}
