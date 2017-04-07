package com.rujianbin.provider.security.entity;

import java.io.Serializable;

/**
 * Created by 汝建斌 on 2017/4/1.
 */
public class RoleEntity implements Serializable{

    private static final long serialVersionUID = 1635767850110589130L;
    private Long id;

    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
