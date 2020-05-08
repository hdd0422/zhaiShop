package com.daoReconsitution.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.daoReconsitution.entity.Goods;
import com.daoReconsitution.util.DBUtil;

/**
 * 
 * @ClassName: GoodsDao
 * @Description:TODO(商品dao层)
 * @author: 韩豆豆
 * @date: 2020年4月11日 上午9:09:07
 * @context 使用工具类封装，简化代码 。
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class GoodsDao {
	private ResultSet rs = null;
	private Goods temp = null;

	// 全查询
	// 返回总记录数
	public int getTotal() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select count(*) from  t_goods";
			rs = DBUtil.executeQuery(sql, new Object[] {});
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);

		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
	}

	// 返回每页记录数
	public List<Goods> getBigTypeByPage(int pageSize, int pageNow) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Goods> al = new ArrayList<Goods>();

		try {
			String sql = "select *from(select al.*,rownum rn from(select t1.*,t2.name bigTypeName,t3.name smallTypeName from t_goods t1,t_bigType t2,t_smallType t3 where t1.bigTypeId=t2.id and t1.smallTypeId=t3.id order by t1.id) al where rownum<=?) where rn>=?";
			rs = DBUtil.executeQuery(sql, new Object[] { pageSize * pageNow, (pageSize * (pageNow - 1) + 1) });

			while (rs.next()) {
				// 循环读取数据,封装到Goods对象--存放到ArrayList集合【二次封装】
				temp = new Goods(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getString("proPic"),
						rs.getString("brand"), rs.getInt("sales"), rs.getInt("views"), rs.getInt("stock"),
						rs.getString("cotents"), rs.getInt("bigTypeId"), rs.getInt("smallTypeId"),
						rs.getString("state"), rs.getString("bigTypeName"), rs.getString("smallTypeName"));
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

	// 模糊查询
	// 返回总记录数
	public int getTotal(String name) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {

			String sql = "select count(*) from  t_goods where name like ?";
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
	public List<Goods> getBigTypeByPage(int pageSize, int pageNow, String name)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Goods> al = new ArrayList<Goods>();
		try {
			String sql = "select *from(select al.*,rownum rn from(select t1.*,t2.name bigTypeName,t3.name smallTypeName from t_goods t1,t_bigType t2,t_smallType t3 where t1.bigTypeId=t2.id and t1.smallTypeId=t3.id and t1.name like ? order by t1.id) al where rownum<=?) where rn>=?";
			rs = DBUtil.executeQuery(sql,
					new Object[] { "%" + name + "%", pageSize * pageNow, (pageSize * (pageNow - 1) + 1) });
			while (rs.next()) {
				// 循环读取数据,封装到Goods对象--存放到ArrayList集合【二次封装】
				temp = new Goods(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getString("proPic"),
						rs.getString("brand"), rs.getInt("sales"), rs.getInt("views"), rs.getInt("stock"),
						rs.getString("cotents"), rs.getInt("bigTypeId"), rs.getInt("smallTypeId"),
						rs.getString("state"), rs.getString("bigTypeName"), rs.getString("smallTypeName"));
				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

	// 添加
	public boolean addGoods(Goods goods) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_goods(id,name,price,brand,sales,views,stock,cotents,bigTypeId,smallTypeId,state,proPic) values(t_goods_seq.nextval,?,?,?,?,?,?,?,?,?,?,?)";
		return DBUtil.executeUpdate(sql,
				new Object[] { goods.getName(), goods.getPrice(), goods.getBrand(), goods.getSales(), goods.getViews(),
						goods.getStock(), goods.getContents(), goods.getBigTypeId(), goods.getSmallTypeId(),
						goods.getState(), goods.getProPic() });
	}

	// 修改
	public boolean updateGoods(Goods goods) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "update t_goods set name=?,price=?,brand=?,sales=?,views=?,stock=?,cotents=?,bigTypeId=?,smallTypeId=?,state=?,proPic=? where id=?";
		return DBUtil.executeUpdate(sql,
				new Object[] { goods.getName(), goods.getPrice(), goods.getBrand(), goods.getSales(), goods.getViews(),
						goods.getStock(), goods.getContents(), goods.getBigTypeId(), goods.getSmallTypeId(),
						goods.getState(), goods.getProPic(), goods.getId() });
	}

	// 查询单个商品详细信息
	public Goods selectGoods(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			String sql = "select t1.*,t2.name bigTypeName,t3.name smallTypeName from t_goods t1,t_bigType t2,t_smallType t3 where t1.bigTypeId=t2.id and t1.smallTypeId=t3.id and t1.id = ?";
			rs = DBUtil.executeQuery(sql, new Object[] { id });

			if (rs.next()) {
				// 读取数据,封装到Goods对象
				temp = new Goods(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getString("proPic"),
						rs.getString("brand"), rs.getInt("sales"), rs.getInt("views"), rs.getInt("stock"),
						rs.getString("cotents"), rs.getInt("bigTypeId"), rs.getInt("smallTypeId"),
						rs.getString("state"), rs.getString("bigTypeName"), rs.getString("smallTypeName"));
				// 返回对象
				return temp;
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
		return null;
	}

	// 批量删除
	public void deleteAll(String[] ids) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		for (String id : ids) {
			String sql = "delete from t_goods where id=?";
			DBUtil.executeUpdate(sql, new Object[] { id });
		}
	}

	public List<Goods> selectAllGoodsByBigTypeId(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Goods> al = new ArrayList<Goods>();

		try {
			String sql = "select al.* from(select t1.*,t2.name bigTypeName,t3.name smallTypeName from t_goods t1,t_bigType t2,t_smallType t3 where t1.bigTypeId=t2.id and t1.smallTypeId=t3.id and t1.bigTypeId=? order by t1.sales) al where rownum<=10";
			rs = DBUtil.executeQuery(sql, new Object[] { id });
			while (rs.next()) {
				// 循环读取数据,封装到Goods对象--存放到ArrayList集合【二次封装】
				temp = new Goods(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getString("proPic"),
						rs.getString("brand"), rs.getInt("sales"), rs.getInt("views"), rs.getInt("stock"),
						rs.getString("cotents"), rs.getInt("bigTypeId"), rs.getInt("smallTypeId"),
						rs.getString("state"), rs.getString("bigTypeName"), rs.getString("smallTypeName"));
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

	public List<Goods> findAllGoods(int smallTypeId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Goods> al = new ArrayList<Goods>();

		try {
			String sql = "select al.* from(select t1.*,t2.name bigTypeName,t3.name smallTypeName from t_goods t1,t_bigType t2,t_smallType t3 where t1.bigTypeId=t2.id and t1.smallTypeId=t3.id and t1.smallTypeId=? order by t1.sales) al where rownum<=6";
			rs = DBUtil.executeQuery(sql, new Object[] { smallTypeId });
			while (rs.next()) {
				// 循环读取数据,封装到Goods对象--存放到ArrayList集合【二次封装】
				temp = new Goods(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getString("proPic"),
						rs.getString("brand"), rs.getInt("sales"), rs.getInt("views"), rs.getInt("stock"),
						rs.getString("cotents"), rs.getInt("bigTypeId"), rs.getInt("smallTypeId"),
						rs.getString("state"), rs.getString("bigTypeName"), rs.getString("smallTypeName"));
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
