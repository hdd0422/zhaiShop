/**  
 * All rights Reserved, Designed By 韩豆豆
 * @Title:  Address.java   
 * @Package com.daoReconsitution.entity   
 * @Description:    TODO(地址表实体类)   
 * @author: 韩豆豆     
 * @date:   2020年4月17日 上午8:10:58   
 * @version V1.0 
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved. 
 */
package com.daoReconsitution.entity;

import java.math.BigDecimal;

/**
 * @ClassName: Address
 * @Description:TODO(地址表实体类)
 * @author: 韩豆豆
 * @date: 2020年4月17日 上午8:10:58
 * @context
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class Address {
	private BigDecimal id;// 编号
	private String province, city, area, address, phone, username;// 省,市,县,详细地址,手机号,收货人姓名
	private int msg, userId;// 是否默认,用户外键

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getMsg() {
		return msg;
	}

	public void setMsg(int msg) {
		this.msg = msg;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Address(BigDecimal id, String province, String city, String area, String address, String phone,
			String username, int msg, int userId) {
		super();
		this.id = id;
		this.province = province;
		this.city = city;
		this.area = area;
		this.address = address;
		this.phone = phone;
		this.username = username;
		this.msg = msg;
		this.userId = userId;
	}

	public Address() {
		super();
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", province=" + province + ", city=" + city + ", area=" + area + ", address="
				+ address + ", phone=" + phone + ", username=" + username + ", msg=" + msg + ", userId=" + userId + "]";
	}

}