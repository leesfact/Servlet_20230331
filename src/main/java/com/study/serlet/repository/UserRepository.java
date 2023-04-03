package com.study.serlet.repository;

import com.study.serlet.entity.User;

public interface UserRepository {
	
	public int save(User user);
	public User findUserByUsername(String username);
}
