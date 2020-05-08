/**  
 * All rights Reserved, Designed By 韩豆豆
 * @Title:  Collect.java   
 * @Package com.daoReconsitution.entity   
 * @Description:    TODO(收藏实体类)   
 * @author: 韩豆豆     
 * @date:   2020年4月14日 上午8:34:13   
 * @version V1.0 
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved. 
 */
package com.daoReconsitution.entity;

import java.math.BigDecimal;

/**
 * @ClassName: Collect
 * @Description:TODO(收藏实体类)
 * @author: 韩豆豆
 * @date: 2020年4月14日 上午8:34:13
 * @context
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class Collect {

	private BigDecimal id, userId, goodsId;// 编号,用户编号,商品编号
	private String goodsName, goodsProPic;// 商品名称,商品图片
	private BigDecimal goodsPrice;// 商品价格
	private String time;// 收藏时间

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public BigDecimal getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(BigDecimal goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsProPic() {
		return goodsProPic;
	}

	public void setGoodsProPic(String goodsProPic) {
		this.goodsProPic = goodsProPic;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Collect(BigDecimal id, BigDecimal userId, BigDecimal goodsId, String goodsName, String goodsProPic,
			BigDecimal goodsPrice, String time) {
		super();
		this.id = id;
		this.userId = userId;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsProPic = goodsProPic;
		this.goodsPrice = goodsPrice;
		this.time = time;
	}

	public Collect() {
		super();
	}

	@Override
	public String toString() {
		return "Collect [id=" + id + ", userId=" + userId + ", goodsId=" + goodsId + ", goodsName=" + goodsName
				+ ", goodsProPic=" + goodsProPic + ", goodsPrice=" + goodsPrice + ", time=" + time + "]";
	}

}
