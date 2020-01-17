/**
 * Copyright (C), 2015-2020
 * FileName: RoleMapperTest
 * Author:   Administrator
 * Date:     2020/1/17 18:15
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.simple.model;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import xyz.codedog.simple.mapper.RoleMapper;

import java.util.Date;
import java.util.List;

public class RoleMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById(){
        //获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取RoleMapper接口
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //调用selectbyId方法,查询 id = 1 的角色
            SysRole role = roleMapper.selectById(1l);
            //role不为空
            Assert.assertNotNull(role);
            //roleName = 管理员
            Assert.assertEquals("管理员",role.getRoleName());
        }finally {
            //关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectById2(){
        //获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取RoleMapper接口
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //调用selectbyId方法,查询 id = 1 的角色
            SysRole role = roleMapper.selectById2(1l);
            //role不为空
            Assert.assertNotNull(role);
            //roleName = 管理员
            Assert.assertEquals("管理员",role.getRoleName());
        }finally {
            //关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll(){
        //获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取RoleMapper接口
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //调用selectAll方法查询所有角色
            List<SysRole> roleList = roleMapper.selectAll();
            //结果不为空
            Assert.assertNotNull(roleList);
            //角色数量大于0个
            Assert.assertTrue(roleList.size() > 0);
            //验证下画线字段是否映射成功
            Assert.assertNotNull(roleList.get(0).getRoleName());
        }finally {
            //关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testInsert(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = new SysRole();
//            role.setId(5L);
            role.setRoleName("啦啦队");
            role.setEnabled(1l);
            role.setCreateBy(1l);
            role.setCreateTime(new Date());
            int retult = roleMapper.insert(role);
            Assert.assertEquals(1,retult);
            Assert.assertNull(role.getId());
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }
}
