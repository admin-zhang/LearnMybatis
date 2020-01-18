/**
 * Copyright (C), 2015-2020
 * FileName: PrivilegeProvider
 * Author:   Administrator
 * Date:     2020/1/17 18:52
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.simple.mapper;

import org.apache.ibatis.jdbc.SQL;

public class PrivilegeProvider {
    public String selectById(final Long id){
        return new SQL(){
            {
                SELECT("id,privilege_name,privilege_url");
                FROM("sys_privilege");
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
