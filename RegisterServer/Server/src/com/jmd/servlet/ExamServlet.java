package com.jmd.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jmd.db.DBUtils;
import com.jmd.domain.BaseBean;
import com.jmd.domain.ExamBean;
import com.jmd.domain.UserBean;

public class ExamServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request--->" + request.getRequestURL() + "====" + request.getParameterMap().toString());
		response.setContentType("text/html;charset=utf-8");
		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		// 打开数据库连接
		
		List<ExamBean> ebList=dbUtils.FindAll();
		// 由Gson将集合序列化成json
		Gson gson = new Gson();
		// 将对象转化成json字符串
		String json = gson.toJson(ebList);
		try {
			response.getWriter().println(json);
			// 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭这个流，不然会发生错误的
		}
		dbUtils.closeConnect(); // 关闭数据库连接}
	}
	}

