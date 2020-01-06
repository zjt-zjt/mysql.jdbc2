package com.lanou.jdbc2;

import java.sql.Connection;
import java.util.List;

public class Test {

	public static void main(String[] args) throws Exception {
		Connection con = jdbcUtils.getConnection();
		
		try{
			con.setAutoCommit(false);//等价于 begin
			double jie = 100000;
			String sql1 = "update account set money = money - ? where id = ?";
			jdbcUtils.UpDate(con,sql1, jie, 1);

			String sql2 = "update account set money = money + ? where id = ?";
			jdbcUtils.UpDate(con,sql2, jie, 2);
		
			con.commit();//确认
			String sql3 = "select * from account";
			List<Account> list = jdbcUtils.query(con,sql3, Account.class);
			for(Account a : list) {
				System.out.println(a);
			}
			
			
		}catch (Exception e) {
			con.rollback();
		}finally {
			con.close();
		}
	}

}
