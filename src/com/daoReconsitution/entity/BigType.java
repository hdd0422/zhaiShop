package com.daoReconsitution.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author 韩豆豆
 * 
 * @title 商品大类实体类
 */
public class BigType implements Serializable {
	private int id;// 主键
	private String name, remarks, proPic;// 大类名称,大类描述,大类图片
	private List<SmallType> smallTypeList;// 小类列表
	private List<Goods> goodsList;// 商品列表

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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProPic() {
		return proPic;
	}

	public void setProPic(String proPic) {
		this.proPic = proPic;
	}

	public BigType() {
		super();
	}

	public List<SmallType> getSmallTypeList() {
		return smallTypeList;
	}

	public void setSmallTypeList(List<SmallType> smallTypeList) {
		this.smallTypeList = smallTypeList;
	}

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}

	public BigType(int id, String name, String remarks, String proPic) {
		super();
		this.id = id;
		this.name = name;
		this.remarks = remarks;
		this.proPic = proPic;
	}

	@Override
	public String toString() {
		return "BigType [id=" + id + ", name=" + name + ", remarks=" + remarks + ", proPic=" + proPic
				+ ", smallTypeList=" + smallTypeList + ", goodsList=" + goodsList + "]";
	}

	
}
