package com.daoReconsitution.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.daoReconsitution.entity.Slide;
import com.daoReconsitution.util.DBUtil;

/**
 * 
 * @ClassName: SlideDao
 * @Description:TODO(幻灯片dao层)
 * @author: 韩豆豆
 * @date: 2020年4月21日 上午8:48:49
 * @context
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class SlideDao {

	private ResultSet rs = null;
	private Slide temp = null;

	// 分页查询
	// 查询总记录数
	public int getTotal() throws ClassNotFoundException, SQLException {
		String sql = "select count(*) from  t_slide";
		try {
			rs = DBUtil.executeQuery(sql, new Object[] {});
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);

		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}
	}

	// 查询页面数据
	public List<Slide> getBigTypeByPage(int pageSize, int pageNow) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Slide> al = new ArrayList<Slide>();
		String sql = "select *from(select al.*,rownum rn from(select * from t_slide order by id ) al where rownum<=?) where rn>=?";
		try {
			rs = DBUtil.executeQuery(sql, new Object[] { pageSize * pageNow, (pageSize * (pageNow - 1) + 1) });

			while (rs.next()) {
				// 循环读取数据,封装到Slide对象--存放到ArrayList集合【二次封装】
				temp = new Slide(rs.getInt("id"), rs.getString("name"), rs.getString("proPic"), rs.getString("url"));
				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}

	// 查询详细信息
	public Slide findSlide(int id) throws ClassNotFoundException, SQLException {
		try {
			String sql = "select * from t_slide where id =?";
			rs = DBUtil.executeQuery(sql, new Object[] { id });
			if (rs.next()) {
				// 读取数据,封装到Slide对象
				temp = new Slide(rs.getInt("id"), rs.getString("name"), rs.getString("proPic"), rs.getString("url"));
				// 添加到集合里
				return temp;
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return null;
	}

	// 修改幻灯片
	public boolean update(Slide slide) throws ClassNotFoundException, SQLException {
		String sql = "update t_slide set name=?,proPic=?,url=? where id =?";
		return DBUtil.executeUpdate(sql,
				new Object[] { slide.getName(), slide.getProPic(), slide.getUrl(), slide.getId() });
	}

	public List<Slide> selectAllslide() throws ClassNotFoundException, SQLException {
		List<Slide> al = new ArrayList<Slide>();

		try {
			String sql = "select * from t_slide order by id";
			rs = DBUtil.executeQuery(sql, new Object[] {});

			while (rs.next()) {
				// 循环读取数据,封装到Slide对象--存放到ArrayList集合【二次封装】
				temp = new Slide(rs.getInt("id"), rs.getString("name"), rs.getString("proPic"), rs.getString("url"));
				// 添加到集合里
				al.add(temp);
			}
		} finally {
			DBUtil.closeAll(rs, DBUtil.getPstmt(), DBUtil.getCon());
		}

		return al;
	}
}
