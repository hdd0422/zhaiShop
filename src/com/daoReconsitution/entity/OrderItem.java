package com.daoReconsitution.entity;

public class OrderItem {

	private int id, goodsId; // --订单项编号,商品编号
	private String goodsName, proPic; // 商品名称,商品图片
	private float goodsPrice; // 商品价格
	private int sum; // 购买数量
	private double subTotal; // 小计金额
	private String orderId; // 订单号Id

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getProPic() {
		return proPic;
	}

	public void setProPic(String proPic) {
		this.proPic = proPic;
	}

	public float getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(float goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderItem(int id, int goodsId, String goodsName, String proPic, float goodsPrice, int sum, double subTotal,
			String orderId) {
		super();
		this.id = id;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.proPic = proPic;
		this.goodsPrice = goodsPrice;
		this.sum = sum;
		this.subTotal = subTotal;
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", goodsId=" + goodsId + ", goodsName=" + goodsName + ", proPic=" + proPic
				+ ", goodsPrice=" + goodsPrice + ", sum=" + sum + ", subTotal=" + subTotal + ", orderId=" + orderId
				+ "]";
	}

}
