package com.rujianbin.provider.security;

import com.rujianbin.provider.security.entity.AuthorityEntity;
import com.rujianbin.provider.security.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 汝建斌 on 2017/4/1.
 */
public class RjbSecurityUser extends UserEntity implements UserDetails {

    public RjbSecurityUser(){}

    public RjbSecurityUser(UserEntity user){
        super.setId(user.getId());
        super.setName(user.getName());
        super.setUsername(user.getUsername());
        super.setPassword(user.getPassword());
        super.setCreateDate(user.getCreateDate());
        super.setAuthorityEntityList(user.getAuthorityEntityList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<AuthorityEntity> AuthorityEntityList = this.getAuthorityEntityList();
        if(AuthorityEntityList != null){
            for (AuthorityEntity author : AuthorityEntityList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(author.getAuthorityCode());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getUsername(){
        return super.getUsername();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
