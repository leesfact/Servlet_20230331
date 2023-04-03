package com.study.serlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.study.serlet.dto.ResponseDto;
import com.study.serlet.entity.User;
import com.study.serlet.service.UserService;
import com.study.serlet.service.UserServiceImpl;


@WebServlet("/auth/signin")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    private UserService userService;
    private Gson gson;
 
    public SignIn() {
    	userService = UserServiceImpl.getInstance();
    	gson = new Gson();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		User user = userService.getUser(username);
		if(user == null) {
			// 로그인 실패 1 (아이디 찾을 수 없음)
			ResponseDto<Boolean> responseDto = 
					new ResponseDto<Boolean>(400, "사용자 인증 실패", false);
			out.println(gson.toJson(responseDto));
		}
		
		if(!user.getPassword().equals(password)) {
			// 로그인 실패 2 (비밀번호 틀림)
			ResponseDto<Boolean> responseDto = 
					new ResponseDto<Boolean>(400, "사용자 인증 실패", false);
			out.println(gson.toJson(responseDto));
		}
		
		// 로그인 성공
			HttpSession session = request.getSession();
			session.setAttribute("AuthenticationPrincipal", user.getUserId());
			
			ResponseDto<Boolean> responseDto = 
					new ResponseDto<Boolean>(200, "사용자 인증 성공", true);
			out.println(gson.toJson(responseDto));
		
	}

}
