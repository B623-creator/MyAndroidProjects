package com.jmd.db;

import java.sql.Connection;
import java.util.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.jmd.domain.ExamBean;


public class DBUtils {
	private Connection conn;
	private String url = "jdbc:mysql://127.0.0.1:3306/registerdatabase?serverTimezone=UTC"; // 指定连接数据库的URL
	private String user = "root"; // 指定连接数据库的用户名
	private String password = "123456"; // 指定连接数据库的密码
	private Statement sta;
	private ResultSet rs; // 打开数据库连接
	private ResultSet rs_exam; // 打开数据库连接


	public void openConnect() {
	try {
	     // 加载数据库驱动
	     Class.forName("com.mysql.jdbc.Driver");
	     conn = DriverManager.getConnection(url, user, password);// 创建数据库连接
	if (conn != null) {
	    System.out.println("数据库连接成功"); // 连接成功的提示信息
	}
	} catch (Exception e) {
	   System.out.println("ERROR: " + e.getMessage());
	}
	}


	// 获得查询user表后的数据集
	public ResultSet getUser() {
	// 创建 statement对象
		try {
		   sta = conn.createStatement(); // 执行SQL查询语句
		   rs = sta.executeQuery("select * from user");
		} catch (SQLException e) {
		   e.printStackTrace();
		}
		   return rs;
		}
	
	// 获得查询exam表后的数据集
	public ResultSet getExam() {
		// 创建 statement对象
		try {
		   sta = conn.createStatement(); // 执行SQL查询语句
		   rs_exam = sta.executeQuery("select * from exam");
		} catch (SQLException e) {
		   e.printStackTrace();
		}
		   return rs_exam;
		}
	//
	public List<ExamBean> FindAll() {
		rs_exam = getExam();
		
		List<ExamBean> ebList=new ArrayList<>();
		if (rs_exam != null) {
			try {
				while(rs_exam.next()) {
					ExamBean eb=new ExamBean();
					int exam_id = rs_exam.getInt("exam_id");
					String exam_question = rs_exam.getString("exam_question");
					String exam_answera = rs_exam.getString("exam_answera");
					String exam_answerb = rs_exam.getString("exam_answerb");
					String exam_answerc = rs_exam.getString("exam_answerc");
					String exam_answerd = rs_exam.getString("exam_answerd");
					int exam_answer = rs_exam.getInt("exam_answer");
					String exam_explaination =  rs_exam.getString("exam_explaination");
					
					//表示没有任何选项(设置为-1)
					eb.setSelectedAnswer(-1);
					eb.setId(exam_id);
					eb.setQuestion(exam_question);
					eb.setAnswera(exam_answera);
					eb.setAnswerb(exam_answerb);
					eb.setAnswerc(exam_answerc);
					eb.setAnswerd(exam_answerd);
					eb.setAnswer(exam_answer);
					eb.setExplaination(exam_explaination);
					
					ebList.add(eb);
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ebList;
	}
		

	// 判断数据库中是否存在某个用户名及其密码,注册和登录的时候判断
	public boolean isExistInDB(String username, String password) {
	boolean isFlag = false; // 创建 statement对象
	try {
	     System.out.println("判断用户名密码");
	     sta = conn.createStatement(); // 执行SQL查询语句
	     rs = sta.executeQuery("select * from user");// 获得结果集
	if (rs != null) {
	   while (rs.next()) { // 遍历结果集
	        if (rs.getString("user_name").equals(username)) {
	        	if (rs.getString("user_pwd").equals(password)) {
	        		isFlag = true;
	        		break;
	      }
	    }
	   }
	  }
	} catch (SQLException e) {
	    e.printStackTrace();
	    isFlag = false;
	}
	   return isFlag;
	}


	// 注册 将用户名和密码插入到数据库(id设置的是自增长的，因此不需要插入)
	public boolean insertDataToDB(String username, String password) {
	     String sql = " insert into user ( user_name , user_pwd ) values ( " + "'" + username + "', " + "'" + password
	+ "' )";
	try {
	    sta = conn.createStatement();
	    System.out.println("向数据库中插入数据");
	    // 执行SQL查询语句
	    return sta.execute(sql);
	} catch (SQLException e) {
	  e.printStackTrace();
	}
	  return false;
	}

	

	// 关闭数据库连接
	public void closeConnect() {
	try {
	    if (rs != null) {
	    rs.close();
	   }
	    if (sta != null) {
	    sta.close();
	   }
	   if (conn != null) {
	     conn.close();
	   }
	  System.out.println("关闭数据库连接成功");
	} catch (SQLException e) {
	   System.out.println("Error: " + e.getMessage());
	}
  }

}
