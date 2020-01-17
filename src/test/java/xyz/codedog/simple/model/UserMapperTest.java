/**
 * Copyright (C), 2015-2020
 * FileName: UserMapperTest
 * Author:   Administrator
 * Date:     2020/1/13 18:49
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.simple.model;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import xyz.codedog.simple.mapper.UserMapper;

import java.util.Date;
import java.util.List;

public class UserMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById(){
        //获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用selectById方法,查询 id = 1 的用户
            SysUser user = userMapper.selectById(1l);
            //user 不为空
            Assert.assertNotNull(user);
            //username = admin
            Assert.assertEquals("admin",user.getUserName());
        }finally {
            //不要忘记关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用selectAll方法查询所有用户
            List<SysUser> userList = userMapper.selectAll();
            //结果不为空
            Assert.assertNotNull(userList);
            //用户数量大于0个
            Assert.assertTrue(userList.size() > 0);
        }finally {
            //关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRolesByUserId(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用selectRolesByUserId方法查询用户的角色
            List<SysRole> roleList = userMapper.selectRolesByUserId(1L);
            //结果不为空
            Assert.assertNotNull(roleList);
            //角色数量大于0个
            Assert.assertTrue(roleList.size() > 0);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test1@163.com");
            user.setUserInfo("test1 info");
            //正常情况下应该读入一张图片存到byte数组中
            user.setHeadImg(new  byte[]{1,2,3});
            user.setCreateTime(new Date());
            //将新建的对象插入数据库中,特别注意这里的返回值result是执行SQL影响的行数
            int result = userMapper.insert(user);
            //直插入1条数据
            Assert.assertEquals(1,result);
            //id为null,没有给id赋值,并且没有配置回写id的值
            Assert.assertNull(user.getId());
        }finally {
            //为了不影响其他测试,这里选择回滚
            //由于默认的sqlSessionFactory.openSession()是不自动提交的
            //因此不手动执行commit也不会提交的数据库
            sqlSession.rollback();
            //关闭sqlSesion
            sqlSession.close();
        }
    }

    @Test
    public void testInsert2(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test1@163.com");
            user.setUserInfo("test1 info");
            //正常情况下应该读入一张图片存到byte数组中
            user.setHeadImg(new  byte[]{1,2,3});
            user.setCreateTime(new Date());
            //将新建的对象插入数据库中,特别注意这里的返回值result是执行SQL影响的行数
            int result = userMapper.insert2(user);
            //直插入1条数据
            Assert.assertEquals(1,result);
            //id为null,没有给id赋值,并且没有配置回写id的值
            Assert.assertNotNull(user.getId());
        }finally {
            //为了不影响其他测试,这里选择回滚
            //由于默认的sqlSessionFactory.openSession()是不自动提交的
            //因此不手动执行commit也不会提交的数据库
            sqlSession.commit();
            //关闭sqlSesion
            sqlSession.close();
        }
    }
}
