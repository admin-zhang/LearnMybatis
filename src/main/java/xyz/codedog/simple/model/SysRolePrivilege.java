/**
 * Copyright (C), 2015-2020
 * FileName: SysRolePrivilege
 * Author:   Administrator
 * Date:     2020/1/10 16:40
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.simple.model;

/**
 * 角色权限关联表
 */
public class SysRolePrivilege {
    private Long roleId;
    private Long privilegeId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }
}
