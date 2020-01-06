package com.lanou.jdbc2;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jdbcUtils {
  private static final String DRIVERNAME = "com.mysql.jdbc.Driver";
  private static final String URL = "jdbc:mysql://localhost:3306/student?useSSL=false";
  private static final String USENAME = "root";
  private static final String PASSWORD = "zjtzjy137381";
  
  
  //获取一个Connection对象，每调用一次，获取一个新的Connection对象
  
  public static Connection getConnection() throws Exception {
	  Connection con = DriverManager.getConnection(URL, USENAME, PASSWORD);
	  return con;
}
  
  
  //通用的增删改(适用于事务)
   public static int UpDate(Connection con, String sql,Object...parameters) throws Exception {
	     Class.forName(DRIVERNAME);
	     try( PreparedStatement ps = con.prepareStatement(sql);){
	    	       for(int i=0;i<parameters.length;i++) {
	    	    	   ps.setObject(i+1, parameters[i]);
	    	       }
	    	 return ps.executeUpdate();
	    	 
	     }
	        
}
 //通用的增删改
   public static int UpDate( String sql,Object...parameters) throws Exception {
	     Class.forName(DRIVERNAME);
	     try( Connection con = DriverManager.getConnection(URL, USENAME, PASSWORD);
	    		 PreparedStatement ps = con.prepareStatement(sql);){
	    	       for(int i=0;i<parameters.length;i++) {
	    	    	   ps.setObject(i+1, parameters[i]);
	    	       }
	    	 return ps.executeUpdate();
	    	 
	     }
	        
}
 //通用的查询
   public static<T> List<T> query( String sql,Class<T>class1,Object...parameters) throws Exception {
		Class.forName(DRIVERNAME);
		List<T> list = new ArrayList<T>();
		Map<String, String> map = getFieldMap(class1);
		try(Connection con = DriverManager.getConnection(URL, USENAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql);){
			
			for(int i = 0;i<parameters.length;i++) {
				 ps.setObject(i+1, parameters[i]);
			}
			    ResultSet  rs = ps.executeQuery();
			  //获取结果集元数据
			    ResultSetMetaData rsmd = rs.getMetaData();
			    int columnCount = rsmd.getColumnCount();
			    while(rs.next()) {
			    	T t= class1.newInstance();//实例话某类型的类
			    	for(int i = 1;i<=columnCount;i++) {
			    		//读取列名
			    		String columnName = rsmd.getColumnLabel(i);
			    		//通过反射获取与列名相同的java类的属性。
			    		
			    		String fieldName = map.get(columnName);
			    		if (fieldName != null) {
							Field field = class1.getDeclaredField(fieldName);
							field.setAccessible(true);
							field.set(t, rs.getObject(i));
						}
			    	}
			    	list.add(t);
			    }
			
			
		}
		
		 return list;
		 
	}  
	 
	
 
   
 
 //通用的查询(适用于事务) 
 public static<T> List<T> query(Connection con, String sql,Class<T>class1,Object...parameters) throws Exception {
	Class.forName(DRIVERNAME);
	List<T> list = new ArrayList<T>();
	Map<String, String> map = getFieldMap(class1);
	try(PreparedStatement ps = con.prepareStatement(sql);){
		
		for(int i = 0;i<parameters.length;i++) {
			 ps.setObject(i+1, parameters[i]);
		}
		    ResultSet  rs = ps.executeQuery();
		  //获取结果集元数据
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int columnCount = rsmd.getColumnCount();
		    while(rs.next()) {
		    	T t= class1.newInstance();//实例话某类型的类
		    	for(int i = 1;i<=columnCount;i++) {
		    		//读取列名
		    		String columnName = rsmd.getColumnLabel(i);
		    		//通过反射获取与列名相同的java类的属性。
		    		
		    		String fieldName = map.get(columnName);
		    		if (fieldName != null) {
						Field field = class1.getDeclaredField(fieldName);
						field.setAccessible(true);
						field.set(t, rs.getObject(i));
					}
		    	}
		    	list.add(t);
		    }
		
		
	}
	
	 return list;
	 
}  
 
	// 查询单个信息
	public static <T> T queryOne(String sql, Class<T> class1, Object... parameters) throws Exception {
		List<T> list = query(sql, class1, parameters);
		return list.size() == 0 ? null : list.get(0);
	}

	// 查询单个信息(支持事务)
	public static <T> T queryOne(Connection con,String sql, Class<T> class1, Object... parameters) throws Exception {
		List<T> list = query(con,sql, class1, parameters);
		return list.size() == 0 ? null : list.get(0);
	}
 
 
 
 
 private static Map<String, String> getFieldMap(Class class1){
	  Map<String, String > map = new HashMap<>();
	  Field [] field = class1.getDeclaredFields();
	 for(Field f:field) {
		 TableFileld tf = f.getAnnotation(TableFileld.class);
		 String name = f.getName();
		 if(tf==null) {
			 map.put(name, name);
		 }else {
			 map.put(tf.value(), name);
		 }
	 }
	  
	 return map;
	 
 }
 
 
 
 
   
}
