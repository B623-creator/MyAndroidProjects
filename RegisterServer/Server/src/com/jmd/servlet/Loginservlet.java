package com.jmd.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jmd.db.DBUtils;
import com.jmd.domain.BaseBean;
import com.jmd.domain.UserBean;

public class Loginservlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request--->" + request.getRequestURL() + "====" + request.getParameterMap().toString());
		String username = request.getParameter("username"); // 获取客户端传过来的参数
		String password = request.getParameter("password");
		response.setContentType("text/html;charset=utf-8");
		if (username==null||username.equals("")||password.equals("")|| password==null) {
			System.out.println("用户名或密码为空");

		} // 请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		// 打开数据库连接
		BaseBean basebean = new BaseBean(); // 基类对象，回传给客户端的json对象
		UserBean userBean = new UserBean(); // user的对象
		// 判断账号是否存在
		if (dbUtils.isExistInDB(username, password)) {
			//账号存在
			basebean.setCode(0);
			//basebean.setData(userBean);
			basebean.setMsg("登录成功");
		} else { //账号不存在
			// 注册成功
			basebean.setCode(-1);
			basebean.setMsg("还没有注册或者密码不正确!!");
			
			/*userBean.setUsername(username);
			userBean.setPassword(password);*/
			//basebean.setData(userBean);
			}
		 
		// 由Gson将对象序列化成json
		Gson gson = new Gson();
		// 将对象转化成json字符串
		String json = gson.toJson(basebean);
		try {
			response.getWriter().println(json);
			// 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭这个流，不然会发生错误的
		}
		dbUtils.closeConnect(); // 关闭数据库连接
	}

}
