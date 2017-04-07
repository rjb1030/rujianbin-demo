package com.rujianbin.provider.security;

import com.rujianbin.provider.security.dao.IAuthorityDao;
import com.rujianbin.provider.security.dao.IUserDao;
import com.rujianbin.provider.security.entity.AuthorityEntity;
import com.rujianbin.provider.security.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 汝建斌 on 2017/4/1.
 */
@Component("rjbUserDetailsService")
public class RjbUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IAuthorityDao authorityDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findUser(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("UserName " + username + " not found");
        }
        List<AuthorityEntity> AuthorityEntityList = authorityDao.getAuthorityEntityList(userEntity.getId());
        userEntity.setAuthorityEntityList(AuthorityEntityList);
        RjbSecurityUser seu = new RjbSecurityUser(userEntity);
        return  seu;
    }
}
