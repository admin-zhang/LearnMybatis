/**
 * Copyright (C), 2015-2020
 * FileName: PrivilegeMapperTest
 * Author:   Administrator
 * Date:     2020/1/17 18:55
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.simple.model;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import xyz.codedog.simple.mapper.PrivilegeMapper;

public class PrivilegeMapperTest extends BaseMapperTest {
    @Test
    public void testSelectBuId(){
        SqlSession sqlSession = getSqlSession();
        try {
            PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
            SysPrivilege privilege = privilegeMapper.selectById(1L);
            Assert.assertNotNull(privilege);
            Assert.assertEquals("用户管理",privilege.getPrivilegeName());
        }finally {
            sqlSession.close();
        }
    }
}
