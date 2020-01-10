/**
 * Copyright (C), 2015-2020
 * FileName: SysUserRole
 * Author:   Administrator
 * Date:     2020/1/10 16:10
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.simple.model;

/**
 * 用户角色关联表
 */
public class SysUserRole {
    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
