package com.daoReconsitution.entity;

import java.io.Serializable;

/**
 * 
 * @author 韩豆豆
 * 
 * @title 商品小类实体类
 */
public class SmallType implements Serializable {
	private int id;// 主键
	private String name;// --小类名称
	private int bigTypeId;// --大类编号，外键
	private String remarks;// --小类描述
	private String bigTypeName;// --大类名字，自己加入

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

	public int getBigTypeId() {
		return bigTypeId;
	}

	public void setBigTypeId(int bigTypeId) {
		this.bigTypeId = bigTypeId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBigTypeName() {
		return bigTypeName;
	}

	public void setBigTypeName(String bigTypeName) {
		this.bigTypeName = bigTypeName;
	}

	public SmallType() {
		super();
	}

	public SmallType(int id, String name, int bigTypeId, String remarks, String bigTypeName) {
		super();
		this.id = id;
		this.name = name;
		this.bigTypeId = bigTypeId;
		this.remarks = remarks;
		this.bigTypeName = bigTypeName;
	}

	@Override
	public String toString() {
		return "SmallType [id=" + id + ", name=" + name + ", bigTypeId=" + bigTypeId + ", remarks=" + remarks
				+ ", bigTypeName=" + bigTypeName + "]";
	}

}
