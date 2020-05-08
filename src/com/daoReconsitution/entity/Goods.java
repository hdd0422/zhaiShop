package com.daoReconsitution.entity;

/**
 * 
 * @author 韩豆豆
 * 
 * @title 商品实体类
 */
public class Goods {
	private int id;
	private String name;// --商品名称
	private float price;// --商品价格
	private String proPic, brand;// --商品图片,商品品牌
	private int sales, views, stock;// --商品销量,商品浏览量,商品库存
	private String contents;// --商品描述
	private int bigTypeId, smallTypeId;// --大类ID,小类ID
	private String state;// --状态
	private String bigTypeName, smallTypeName;// --大类名称,小类名称
	private int num;// --商品数量
	private double total;// --总计

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getProPic() {
		return proPic;
	}

	public void setProPic(String proPic) {
		this.proPic = proPic;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getBigTypeId() {
		return bigTypeId;
	}

	public void setBigTypeId(int bigTypeId) {
		this.bigTypeId = bigTypeId;
	}

	public int getSmallTypeId() {
		return smallTypeId;
	}

	public void setSmallTypeId(int smallTypeId) {
		this.smallTypeId = smallTypeId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBigTypeName() {
		return bigTypeName;
	}

	public void setBigTypeName(String bigTypeName) {
		this.bigTypeName = bigTypeName;
	}

	public String getSmallTypeName() {
		return smallTypeName;
	}

	public void setSmallTypeName(String smallTypeName) {
		this.smallTypeName = smallTypeName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Goods() {
		super();
	}

	public Goods(int id, String name, float price, String proPic, String brand, int sales, int views, int stock,
			String contents, int bigTypeId, int smallTypeId, String state, String bigTypeName, String smallTypeName) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.proPic = proPic;
		this.brand = brand;
		this.sales = sales;
		this.views = views;
		this.stock = stock;
		this.contents = contents;
		this.bigTypeId = bigTypeId;
		this.smallTypeId = smallTypeId;
		this.state = state;
		this.bigTypeName = bigTypeName;
		this.smallTypeName = smallTypeName;
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", price=" + price + ", proPic=" + proPic + ", brand=" + brand
				+ ", sales=" + sales + ", views=" + views + ", stock=" + stock + ", contents=" + contents
				+ ", bigTypeId=" + bigTypeId + ", smallTypeId=" + smallTypeId + ", state=" + state + ", bigTypeName="
				+ bigTypeName + ", smallTypeName=" + smallTypeName + "]";
	}

}
