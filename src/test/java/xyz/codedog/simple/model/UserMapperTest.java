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

    @Test
    public void testUpdateById(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //从数据库查询1个user对象
            SysUser user = userMapper.selectById(1L);
            //当前userName为admin
            Assert.assertEquals("admin",user.getUserName());
            //修改用户名
            user.setUserName("admin_test");
            //修改邮箱
            user.setUserEmail("admintest@codedog.xyz");
            //更新数据,特别注意,这里的result是执行的SQL影响的行数
            int result = userMapper.updateById(user);
            //只更新1条数据
            Assert.assertEquals(1,result);
            //根据当前id查询修改后的数据
            user = userMapper.selectById(1L);
            //修改后的名字是admin_test
            Assert.assertEquals("admin_test",user.getUserName());
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteById(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper  userMapper = sqlSession.getMapper(UserMapper.class);
            //从数据库查询1个user对象,根据 id = 1 查询
            SysUser user1 = userMapper.selectById(1L);
            //现在还能查询出user对象
            Assert.assertNotNull(user1);
            //调用方法删除
            Assert.assertEquals(1,userMapper.deleteById(1L));
            //再次查询,这时应该没有值,为null
            Assert.assertNull(userMapper.selectById(1L));

            //使用SysUser参数再进行一次测试,根据 id = 1001 查询
            SysUser user2 = userMapper.selectById(1001L);
            //现在还能查询处user对象
            Assert.assertNotNull(user2);
            //调用方法删除
            Assert.assertEquals(1,userMapper.deleteById(user2));
            //再次查询,这时应该没有值,为null
            Assert.assertNull(userMapper.selectById(1001L));
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRolesByUserIdAndRoleEnabled(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用 selectRolesByUserIdAndRoleEnabled 方法查询用户的角色
            List<SysRole> userList = userMapper.selectRolesByUserIdAndRoleEnabled(1L,1);
            //结果不为空
            Assert.assertNotNull(userList);
            //角色数量大于0个
            Assert.assertTrue(userList.size() > 0);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByUser(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //只查询用户名时
            SysUser query = new SysUser();
            query.setUserName("ad");
            List<SysUser> userList = userMapper.selectByUser(query);
            Assert.assertTrue(userList.size() > 0);

            //只查询用户邮箱时
            query = new SysUser();
            query.setUserEmail("test@163.com");
            userList = userMapper.selectByUser(query);
            Assert.assertTrue(userList.size() > 0);

            //同时查询用户名和邮箱时
            query = new SysUser();
            query.setUserName("ad");
            query.setUserEmail("test@163.com");
            userList = userMapper.selectByUser(query);
            //由于没有同时符合这两个条件的用户,因此查询结果为0
            Assert.assertTrue(userList.size() == 0);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public  void testUpdateByIdSelective(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个新的user对象
            SysUser user = new SysUser();
            //更新 id = 1 的用户
            user.setId(1L);
            //修改邮箱
            user.setUserEmail("test@codedog.xyz");
            //更新邮箱,特别注意,这里的返回值result执行的时SQL影响的行数
            int result = userMapper.updateByIdSelective(user);
            //只更新一条数据
            Assert.assertEquals(1,result);
            //根据当前id查询修改后的数据
            user = userMapper.selectById(1L);
            //修改后的名字保持不变,但是邮箱变成了新的
            Assert.assertEquals("admin",user.getUserName());
            Assert.assertEquals("test@codedog.xyz",user.getUserEmail());
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testInsert4Selective(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个 user 对象
            SysUser user = new SysUser();
            user.setUserName("test-selective");
            user.setUserPassword("123456");
            user.setUserInfo("test-selective info");
            user.setCreateTime(new Date());
            //插入数据库
            userMapper.insert4(user);
            //获取插入的这条数据
            user = userMapper.selectById(user.getId());
            Assert.assertEquals("test@codedog.xyz",user.getUserEmail());
        }finally {
//            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByIdOrUserName(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //只查询用户名时
            SysUser sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setUserName("admin");
            SysUser user = userMapper.selectByIdOrUserName(sysUser);
            Assert.assertNotNull(user);

            //当没有 id 时
            sysUser.setId(null);
            user = userMapper.selectByIdOrUserName(sysUser);
            Assert.assertNotNull(user);

            //当 id 和 name 都为空时
            sysUser.setUserName(null);
            user = userMapper.selectByIdOrUserName(sysUser);
            Assert.assertNull(user);

        }finally {
            sqlSession.close();
        }
    }

}
