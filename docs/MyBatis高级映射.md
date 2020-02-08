# 1. 高级结果映射 #
## 1.1 一对一映射 ##
### 1.1.1 使用自动映射处理一对一关系 ###
1. 在`SysUser.java`类中添加`SysRole`字段,代码如下.
```java
    /**
     * 用户角色
     */
    private SysRole role;

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }
```
2. 根据自动映射规则,在`UserMapper.xml`中添加如下方法
```xml
    <select id="selectUserAndRoleById" resultType="xyz.codedog.simple.model.SysUser">
        select u.id,
            u.user_name userName,
            u.user_password userPassword,
            u.user_email userEmail,
            u.user_info userInfo,
            u.head_img headImg,
            u.create_time createTime,
            r,id "role.id",
            r.role_name "role.roleName",
            r.enabled "role.enabled",
            r.create_by "role.craeteBy",
            r.create_time "role.createTime"
        from sys_user u 
        inner join sys_user_role ur on u.id = ur.user_id
        inner join sys_role r on ur.role_id = r.id
        where u.id = #{id}
    </select>
```
3. `UserMapper.java`的接口中添加对应方法,代码如下.
```java
    /**
     * 根据用户 id 获取用户信息和用户的角色信息
     * 
     * @param id
     * @return
     */
    SysUser selectUserAndRoleById(Long id);
```
4. 在`UserMapperTest.java`中编写如下测试代码
```java
    @Test
    public void testSelectUserAndRoleById(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            final SysUser user = userMapper.selectUserAndRoleById(1001L);
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getRole());
        }finally {
            sqlSession.close();
        }
    }
```