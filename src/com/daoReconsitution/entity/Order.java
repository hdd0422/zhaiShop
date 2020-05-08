package com.daoReconsitution.entity;
/**
 * 
 * @author 韩豆豆
 * 
 * @title 订单实体类
 */
public class Order {
	private String id;// 订单编号
	private int userId;// 用户id,外键
	private double total;// 订单总额
	private int addressId;// 收货地址ID，外键
	private String remarks, time;// 买家留言,下单时间
	private int state;// 当前状态 1:待付款 2:已付款 3:已发货 4:交易取消 5：交易完成 6:货到付款
	private String trueName, province, city, area, address, phone, userName;// 真实姓名,省,市,县,详细地址,手机号,收货人姓名

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Order() {
		super();
	}

	public Order(String id, int userId, double total, int addressId, String remarks, String time, int state,
			String trueName, String province, String city, String area, String address, String phone, String userName) {
		super();
		this.id = id;
		this.userId = userId;
		this.total = total;
		this.addressId = addressId;
		this.remarks = remarks;
		this.time = time;
		this.state = state;
		this.trueName = trueName;
		this.province = province;
		this.city = city;
		this.area = area;
		this.address = address;
		this.phone = phone;
		this.userName = userName;
	}

	public Order(String id, int userId, double total, int addressId, String remarks, String time, int state) {
		super();
		this.id = id;
		this.userId = userId;
		this.total = total;
		this.addressId = addressId;
		this.remarks = remarks;
		this.time = time;
		this.state = state;
	}

}
