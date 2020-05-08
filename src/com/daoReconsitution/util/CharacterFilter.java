package com.daoReconsitution.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 
 * @author 韩豆豆
 *
 */
// url映射/*，给所有页面处理中文乱码
@WebFilter(filterName = "CharacterFilter", urlPatterns = "/*", initParams = {
		@WebInitParam(name = "encoding", value = "utf-8") })
public class CharacterFilter implements Filter {// 实现filter接口
	private static final String CURRENT_USER = "user";
	// 字符编码
	private String encoding;
	// 配置白名单
	protected static List<Pattern> patterns = new ArrayList<Pattern>();

	// 静态代码块，在虚拟机加载类的时候就会加载执行，而且只执行一次
	static {
		patterns.add(Pattern.compile("/admin/index.jsp"));
		patterns.add(Pattern.compile("/admin/login.jsp"));
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 获取初始化参数
		encoding = filterConfig.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (encoding != null) {
			// 设置request字符编码
			servletRequest.setCharacterEncoding(encoding);
			// 设置response字符编码
			servletResponse.setCharacterEncoding(encoding);
			
		}
		// 传递给下一个过滤器
		filterChain.doFilter(servletRequest, servletResponse);
		/*
		 * HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		 * HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		 * 
		 * String url =
		 * httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		 * if (isInclude(url)) { // 在白名单中的url,放行访问 filterChain.doFilter(httpRequest,
		 * httpResponse); return; } if (SessionUtils.getSessionAttribute(CURRENT_USER)
		 * != null) { // 若为登录状态 放行访问 filterChain.doFilter(httpRequest, httpResponse);
		 * return; }
		 */
	}

	// 判断当前请求是否在白名单
	private boolean isInclude(String url) {
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(url);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {
		encoding = null;
	}
}