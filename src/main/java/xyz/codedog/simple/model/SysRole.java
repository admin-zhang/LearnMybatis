/**
 * Copyright (C), 2015-2020
 * FileName: SysRole
 * Author:   Administrator
 * Date:     2020/1/10 16:11
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.simple.model;

import java.util.Date;

/**
 * 角色表
 */
public class SysRole {
    private Long id;
    private String roleName;
    private Long enabled;
    private Long createBy;
    private Date createTime;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public Long getEnabled() {
        return enabled;
    }

    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
