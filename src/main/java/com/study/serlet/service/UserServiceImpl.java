package com.study.serlet.service;

import com.study.serlet.entity.User;
import com.study.serlet.repository.UserRepository;
import com.study.serlet.repository.UserRepositoryImpl;

public class UserServiceImpl implements UserService {
	
	// Service 객체 Singleton 정의
	private static UserService instance;
	public static UserService getInstance() {
		if(instance == null) {
			instance = new UserServiceImpl();
		}
		return instance;
	}
	
	// Repository 객체 DI(Dependency Injection) : 의존관계 주입
	private UserRepository userRepository;
	
	public UserServiceImpl() {
		userRepository = UserRepositoryImpl.getInstance();
	}
	

	@Override
	//굳이 한번 더 거치는 이유는 ???? 하지만, 반드시 필요한 행위
	public int addUSer(User user) {
		
		return userRepository.save(user);
	}

	@Override
	public User getUser(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Override
	public boolean duplicatedUsername(String username) {
		User user = userRepository.findUserByUsername(username);
		
		
		return user != null;
	}

}
