package com.study.serlet.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("/user")
public class UserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		
		System.out.println("GET 요청");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		
		Map<String, String> userMap = new HashMap<>();
		userMap.put("name", name);
		userMap.put("phone",phone);
		
		String userJson = gson.toJson(userMap);
		System.out.println(userJson);
		
		
		/**
		 *  1. 주소창, src, href, replace 등으로 요청할 수 있음
		 *  2. 데이터를 전달하는 방법 (Query String) 
			   http(s) : //서버주소:포트/요청메시지?key=value&key=value
		 */
//		System.out.println(response.getCharacterEncoding());
		
		// HTML 응답
//		response.addHeader("Content-type", "text/html;charset=UTF-8");
//		response.addHeader("test", "test data");
//		response.setContentType("text/html;charset=UTF-8");  
		response.setContentType("application/json;charset=UTF-8");
		
		
		PrintWriter out = response.getWriter();
		out.println(userJson);
//		out.println("이름: " + name);
//		out.println("연락처: " + phone);
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST 요청");
		request.setCharacterEncoding("UTF-8");
		//System.out.println(request.getParameter("address"));
//		AtomicReference<String> jsonAtomic = new AtomicReference<>("");
//		bufferedReader.lines().forEach(line -> {
//			jsonAtomic.getAndUpdate(t -> t+ line);
//		});
//		String json = jsonAtomic.get().replaceAll(" ", "");
		
//		String josn = bufferedReader.lines().collect(Collectors.joining());
		
		Gson gson = new Gson();
		
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		String json = "";
		String line = null;
		
		while((line = bufferedReader.readLine()) != null) {
			json +=line;
		}
		json = json.replaceAll(" ", "");
		System.out.println(json);
		
		
		Map<String, String> jsonMap = gson.fromJson(json, Map.class);
		
		System.out.println(jsonMap);
		
		
		/**
		 * 1. <form action="http://localhost:8080/Servlet_study_20230331/user" method="post">
		 *		<input name = "key" value ="value">
    	 *		<button type = "submit"요청</button>
    	 *	  </from>
    	 *		
		 * 
		 */
	}

}
