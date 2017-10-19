package com.rujianbin.provider.oauth2.security.dao;

import com.rujianbin.provider.oauth2.security.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by 汝建斌 on 2017/4/1.
 */
@Mapper
public interface IUserDao {

    public UserEntity findUser(String username);
}
