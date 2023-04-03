package com.study.serlet.service;

import com.study.serlet.entity.User;

public interface UserService {
	public int addUSer(User user);
	public User getUser(String username);
	public boolean duplicatedUsername(String username);
}
