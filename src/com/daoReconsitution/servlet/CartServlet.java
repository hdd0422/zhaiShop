package com.daoReconsitution.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daoReconsitution.dao.AddressDao;
import com.daoReconsitution.dao.CartDao;
import com.daoReconsitution.dao.GoodsDao;
import com.daoReconsitution.entity.Address;
import com.daoReconsitution.entity.Cart;
import com.daoReconsitution.entity.Goods;
import com.daoReconsitution.entity.User;

import net.sf.json.JSONObject;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		Cart cart = null;
		CartDao cartDao = new CartDao();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		boolean isSuccess = false;
		JSONObject jsonObject = new JSONObject();
		// 取出session中的购物车里的数据
		// 在session中取出购物车
		HashMap<String, Goods> gwc = null;
		if ("addCart".equals(type)) {
			String id = request.getParameter("id");
			Integer num = Integer.parseInt(request.getParameter("num"));
			Float price = Float.parseFloat(request.getParameter("price"));
			String i = request.getParameter("i");
			String sort = request.getParameter("sort");
			double fee = 0;// 总计
			int sum = 0;// 数量
			int status = 0;// 状态
			String msg = "";
			String exsit = "";
			DecimalFormat df = new DecimalFormat(".##");
			gwc = (HashMap<String, Goods>) session.getAttribute("gwc");
			if (user != null) {
				// 用户登录
				// 取出session的数据存入数据库
				if (gwc.size() > 0) {
					try {
						Set<String> keySet = gwc.keySet();
						System.out.println(keySet);
						Iterator<String> iterator = keySet.iterator();
						while (iterator.hasNext()) {
							String key = (String) iterator.next();
							Goods goods = gwc.get(key);
							int count = cartDao.count(user.getId(), Integer.parseInt(key));
							if (count > 0) {
								cartDao.updateCart(
										new Cart(new BigDecimal(user.getId()), new BigDecimal(Integer.parseInt(key)),
												new BigDecimal(count + goods.getNum()), new BigDecimal(0)));
							} else {
								cartDao.addCart(
										new Cart(new BigDecimal(user.getId()), new BigDecimal(Integer.parseInt(key)),
												new BigDecimal(goods.getNum()), new BigDecimal(goods.getPrice())));
							}
						}
						gwc.clear();
						session.setAttribute("gwc", gwc);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				// 判断商品是否存在
				int count = 0;
				try {
					count = cartDao.count(user.getId(), Integer.parseInt(id));
				} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (count > 0) {
					exsit = "1";
					try {
						cartDao.updateCart(new Cart(new BigDecimal(user.getId()), new BigDecimal(Integer.parseInt(id)),
								new BigDecimal(num), new BigDecimal(0)));
					} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					num += count;
				} else {
					exsit = "0";
					try {
						cartDao.addCart(new Cart(new BigDecimal(user.getId()), new BigDecimal(Integer.parseInt(id)),
								new BigDecimal(num), new BigDecimal(price)));
					} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 取出所有数据
				try {
					List<Cart> carts = cartDao.findAll(user.getId());
					for (Cart temp : carts) {
						sum += temp.getNum().intValue();
						fee += temp.getNum().intValue() * temp.getGoodsPrice().floatValue();

					}
				} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// 用户未登录

				// 判断购物车是否存在商品
				if (!gwc.containsKey(id)) {
					Goods goods = new Goods();
					goods.setId(Integer.parseInt(id));
					goods.setNum(num);
					goods.setPrice(price);
					gwc.put(id, goods);
					exsit = "0";
					msg = "没有，添加成功";
				} else {
					Goods goods = gwc.get(id);
					num = goods.getNum() + num;
					goods.setNum(num);
					gwc.put(id, goods);
					exsit = "1";
					msg = "有，添加成功";
				}
				Set<String> keySet = gwc.keySet();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String s_id = (String) iterator.next();
					Goods goods = gwc.get(s_id);
					sum += goods.getNum();
					fee += goods.getNum() * goods.getPrice();
				}
			}
			jsonObject.put("exsit", exsit);
			jsonObject.put("sum", sum);
			jsonObject.put("num", num);
			jsonObject.put("fee", df.format(fee));
			session.setAttribute("gwc", gwc);
			response.getWriter().print(jsonObject);
		} else if ("myCart".equals(type)) {
			List<Goods> gwcGoodsList = new ArrayList<Goods>();
			gwc = (HashMap<String, Goods>) session.getAttribute("gwc");
			// 是否登录
			if (user != null) {
				// 取出session的数据存入数据库
				if (gwc.size() > 0) {
					try {
						Set<String> keySet = gwc.keySet();
						System.out.println(keySet);
						Iterator<String> iterator = keySet.iterator();
						while (iterator.hasNext()) {
							String key = (String) iterator.next();
							Goods goods = gwc.get(key);
							int count = cartDao.count(user.getId(), Integer.parseInt(key));
							if (count > 0) {
								cartDao.updateCart(
										new Cart(new BigDecimal(user.getId()), new BigDecimal(Integer.parseInt(key)),
												new BigDecimal(count + goods.getNum()), new BigDecimal(0)));
							} else {
								cartDao.addCart(
										new Cart(new BigDecimal(user.getId()), new BigDecimal(Integer.parseInt(key)),
												new BigDecimal(goods.getNum()), new BigDecimal(goods.getPrice())));
							}
						}
						gwc.clear();
						session.setAttribute("gwc", gwc);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				try {
					// int num=cartDao.count(userId, goodsId)

					// 取出数据库里的数据
					List<Cart> carts = cartDao.findAll(user.getId());
					for (Cart tempCart : carts) {
						Goods tempGoods = new GoodsDao().selectGoods(tempCart.getGoodsId().intValue());
						tempGoods.setNum(tempCart.getNum().intValue());
						tempGoods.setTotal(tempGoods.getNum() * tempGoods.getPrice());
						gwcGoodsList.add(tempGoods);
					}

				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if (gwc != null) {
					Set<String> keySet = gwc.keySet();
					Iterator<String> iterator = keySet.iterator();
					while (iterator.hasNext()) {
						String id = (String) iterator.next();
						Goods goods = gwc.get(id);
						Goods temp = null;
						try {
							temp = new GoodsDao().selectGoods(goods.getId());
							temp.setNum(goods.getNum());
							temp.setTotal(goods.getNum() * goods.getPrice());
							gwcGoodsList.add(temp);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			// 传值
			session.setAttribute("gwcGoodsList", gwcGoodsList);
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		} else if ("jiaCart".equals(type)) {
			gwc = (HashMap<String, Goods>) session.getAttribute("gwc");
			String sort = request.getParameter("sort");
			int count = 0;
			if (user == null) {
				Goods goods = gwc.get(sort);
				count = goods.getNum();
				count++;
				goods.setNum(count);
				gwc.put(sort, goods);
				session.setAttribute("gwc", gwc);
				jsonObject.put("st", 1);
			} else {
				try {
					count = cartDao.count(user.getId(), Integer.parseInt(sort));
					count++;
					isSuccess = cartDao.updateCart(new Cart(new BigDecimal(user.getId()),
							new BigDecimal(Integer.parseInt(sort)), new BigDecimal(count), new BigDecimal(0)));
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isSuccess) {
					jsonObject.put("st", 1);
				} else {
					jsonObject.put("st", 0);
				}
			}
			// jsonObject.put("num", count);
			response.getWriter().print(jsonObject);
		} else if ("jianCart".equals(type)) {
			gwc = (HashMap<String, Goods>) session.getAttribute("gwc");
			String sort = request.getParameter("sort");
			int count = 0;
			if (user == null) {
				Goods goods = gwc.get(sort);
				count = goods.getNum();
				count--;
				goods.setNum(count);
				gwc.put(sort, goods);
				session.setAttribute("gwc", gwc);
				jsonObject.put("st", 1);
			} else {
				try {
					count = cartDao.count(user.getId(), Integer.parseInt(sort));
					count--;
					isSuccess = cartDao.updateCart(new Cart(new BigDecimal(user.getId()),
							new BigDecimal(Integer.parseInt(sort)), new BigDecimal(count), new BigDecimal(0)));
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isSuccess) {
					jsonObject.put("st", 1);
				} else {
					jsonObject.put("st", 0);
				}
			}
			// jsonObject.put("num", count);
			response.getWriter().print(jsonObject);
		} else if ("deleteCart".equals(type)) {
			gwc = (HashMap<String, Goods>) session.getAttribute("gwc");
			String sort = request.getParameter("sort");
			int sum = 0;
			double fee = 0;
			if (user == null) {
				gwc.remove(sort);
				Set<String> keySet = gwc.keySet();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String s_id = (String) iterator.next();
					Goods temp = gwc.get(s_id);
					sum += temp.getNum();
					fee += temp.getNum() * temp.getPrice();
				}
				session.setAttribute("gwc", gwc);
			} else {
				try {
					isSuccess = cartDao.deleteCart(Integer.parseInt(sort), user.getId());
					List<Cart> carts = cartDao.findAll(user.getId());
					for (Cart temp : carts) {
						sum += temp.getNum().intValue();
						fee += temp.getNum().intValue() * temp.getGoodsPrice().floatValue();

					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			jsonObject.put("sum", sum);
			jsonObject.put("st", 1);
			response.getWriter().print(jsonObject);
		} else if ("buy".equals(type)) {
			List<Goods> gwcGoodsList = new ArrayList<Goods>();
			List<Address> addressList = null;
			String num = request.getParameter("num");
			String id = request.getParameter("id");
			double ze = 0.0;
			try {
				Goods goods = new GoodsDao().selectGoods(Integer.parseInt(id));
				goods.setNum(Integer.parseInt(num));
				ze = goods.getNum() * goods.getPrice();
				goods.setTotal(ze);
				gwcGoodsList.add(goods);
				addressList = new AddressDao().findAllInfo(user.getId());
			} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("gwcGoodsList", gwcGoodsList);
			request.setAttribute("addressList", addressList);
			session.setAttribute("ze", ze);
			request.getRequestDispatcher("/order.jsp").forward(request, response);
		} else if ("submitCart".equals(type)) {
			List<Goods> gwcGoodsList = new ArrayList<Goods>();
			List<Address> addressList = null;
			String cartItemIds[] = request.getParameter("cartItemIds").split(",");
			String total = request.getParameter("total");
			String method = request.getParameter("method");
			double ze = Double.parseDouble(total);
			for (String id : cartItemIds) {
				try {
					cart = cartDao.selectCart(Integer.parseInt(id), user.getId());
					Goods goods = new GoodsDao().selectGoods(cart.getGoodsId().intValue());
					goods.setNum(cart.getNum().intValue());
					goods.setTotal(goods.getNum() * goods.getPrice());
					gwcGoodsList.add(goods);
					addressList = new AddressDao().findAllInfo(user.getId());
				} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			session.setAttribute("gwcGoodsList", gwcGoodsList);
			request.setAttribute("addressList", addressList);
			session.setAttribute("ze", ze);
			request.getRequestDispatcher("/order.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
