/**  
 * All rights Reserved, Designed By 韩豆豆
 * @Title:  Cart.java   
 * @Package com.daoReconsitution.entity   
 * @Description:    TODO(购物车模块实体类)   
 * @author: 韩豆豆     
 * @date:   2020年4月17日 上午10:18:33   
 * @version V1.0 
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved. 
 */
package com.daoReconsitution.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: Cart
 * @Description:TODO(购物车模块实体类)
 * @author: 韩豆豆
 * @date: 2020年4月17日 上午10:18:33
 * @context
 * @Copyright: 2020 韩豆豆 Inc. All rights reserved.
 */
public class Cart {
	private BigDecimal userId, goodsId, num, goodsPrice;// 用户编号,商品编号,数量,价格
	private List<Goods> gwcGoodsList;
	public List<Goods> getGwcGoodsList() {
		return gwcGoodsList;
	}

	public void setGwcGoodsList(List<Goods> gwcGoodsList) {
		this.gwcGoodsList = gwcGoodsList;
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

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Cart(BigDecimal userId, BigDecimal goodsId, BigDecimal num, BigDecimal goodsPrice) {
		super();
		this.userId = userId;
		this.goodsId = goodsId;
		this.num = num;
		this.goodsPrice = goodsPrice;
	}

	public Cart() {
		super();
	}

	@Override
	public String toString() {
		return "Cart [userId=" + userId + ", goodsId=" + goodsId + ", num=" + num + ", goodsPrice=" + goodsPrice + "]";
	}

}
