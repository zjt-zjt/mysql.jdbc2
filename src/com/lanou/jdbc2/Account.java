package com.lanou.jdbc2;

public class Account {
	private String name;
	private int id;
	private double money;
	public String getUsername() {
		return name;
	}
	public void setUsername(String username) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Account(String username, int id, double money) {
		super();
		this.name = name;
		this.id = id;
		this.money = money;
	}
	@Override
	public String toString() {
		return "Account [name=" + name + ", id=" + id + ", money=" + money + "]";
	}
	
	
	
	
}
