package com.daoReconsitution.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;


public class DBUtil {
	private  final static String  DRIVER="oracle.jdbc.OracleDriver";
	private  final static String  URL="jdbc:oracle:thin:@localhost:1521:orcl";
	private  final static String  USERNAME="scott";
	private  final static String  PASSWORD="123456";
    private static Connection con = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;
    
    
    public static Connection getCon() {
		return con;
	}
	public static PreparedStatement getPstmt() {
		return pstmt;
	}
	
    public static ResultSet executeQuery(String sql,Object[] params) throws SQLException, ClassNotFoundException{
    	pstmt = getPreparedStatement(sql, params);
    	rs = pstmt.executeQuery();
    	return rs;
    }
	
    public static PreparedStatement getPreparedStatement(String sql,Object[] params) throws ClassNotFoundException, SQLException{
    	Class.forName(DRIVER);
    	DataSource ds = DataSourceUtil.getDataSourceWithC3P0();
		con = ds.getConnection();
    	pstmt =  con.prepareStatement(sql);
    	if(params !=null){
    		for(int i=0;i<params.length;i++){
    			pstmt.setObject(i+1, params[i]);
    		}
    	}
    	return pstmt;
    }
    public static boolean   executeUpdate(String sql,Object[] params) throws SQLException, ClassNotFoundException{
    	boolean flag = false;
    	pstmt =  getPreparedStatement(sql, params);
    	int result  = pstmt.executeUpdate();
    	if(result>0){
    		flag = true;
    	}
    	closeAll(null, pstmt, con);
    	return flag;
    }
    public static void closeAll(ResultSet rs,Statement stmt,Connection con) throws SQLException{
    	if(rs!=null) rs.close();
    	if(stmt!=null) stmt.close();
    	if(con !=null) con.close();
    }
 
    }

