package com.daoReconsitution.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetOrderId {

	/**
	 * 生成订单号 当前时间加上两位随机数
	 * 
	 * @return
	 */
	public static String sjs() {
		String sjs = "";
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		int x = (int) (Math.random() * 100);
		if (x < 10) {
			x = x + 9;
		} else if (x == 100) {
			x--;
		}
		sjs = sdf.format(d) + x;
		return sjs;
	}
}
