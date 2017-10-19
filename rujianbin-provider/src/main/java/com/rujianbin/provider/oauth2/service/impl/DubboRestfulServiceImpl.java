package com.rujianbin.provider.oauth2.service.impl;

import javax.annotation.Resource;

import com.rujianbin.provider.oauth2.mybatis.entity.Person;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.rujianbin.api.DubboRestfulService;
import com.rujianbin.api.dto.DtoParam;
import com.rujianbin.api.dto.PersonBean;
import com.rujianbin.provider.oauth2.mybatis.dao.IPersonDao;

@Component
public class DubboRestfulServiceImpl implements DubboRestfulService{

	@Resource
	private IPersonDao personDao;
	
	@Override
	public String test(String param, Long version) {
		
		return param+"___"+version;
	}

	@Override
	public DtoParam updatePerson(DtoParam p) {
		p.setName("has modify "+p.getName());
		return p;
	}

	@Override
	public String getAge() {
		return "1234";
	}

	@Override
	public PersonBean getPersonBean(String name) {
		Person p = personDao.queryByName(name);
		PersonBean bean = new PersonBean();
		BeanUtils.copyProperties(p, bean);
		return bean;
	}

}
