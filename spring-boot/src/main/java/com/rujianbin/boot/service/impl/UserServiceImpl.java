package com.rujianbin.boot.service.impl;

import org.springframework.stereotype.Component;

import com.rujianbin.boot.service.IUserService;

@Component("userServiceImpl")
public class UserServiceImpl implements IUserService{

	@Override
	public String getName() {
		return "TESTNAME";
	}

}
