## MyBatis的动态SQL在XML中支持的几种标签 ##

* if
* choose(when,otherwise)
* trim(where,set)
* foreach
* bind

### 1. if用法 ###
#### 1.1 在WHERE条件中使用if ####
> [xyz.codedog.simple.mapper.UserMapper](xyz/codedog/simple/UserMapper).selectByUser(SysUser sysUser);
```SQL
# xyz/codedog/simple/mapper/UserMapper.xml
    <select id="selectByUser" resultType="xyz.codedog.simple.model.SysUser">
        select id,
              user_name userName,
              user_password userPassword,
              user_email userEmail,
              user_info userInfo,
              head_img headImg,
              create_time createTime
        from sys_user
        where 1 = 1
        <if test="userName != null and userName != ''">
            and user_name like concat('%',#{userName},'%')
        </if>
        <if test="userEmail != null and userEmail != ''">
            and user_email = #{userEmail}
        </if>
    </select>
```
> `WHERE 1 = 1 `这个使整个SQL语句在if条件不满足时不会出现语法上的错误.

#### 1.2 在UPDATE更新列中使用if ####
> xyz.codedog.simple.mapper.UserMapper.updateByIdSelective(SysUser sysUser);
```SQL
# xyz/codedog/simple/mapper/UserMapper.xml
   <update id="updateByIdSelective">
        update sys_user
        set
            <if test="userName != null and userName != ''">
                user_name = #{userName},
            </if>
            <if test="userPassword != null and userPassword!= ''">
                user_password = #{userPassword},
            </if>
            <if test="userEmail != null and userEmail != ''">
                user_email = #{userEmail},
            </if>
            <if test="userInfo != null and userInfo != ''">
                user_info = #{userInfo},
            </if>
            <if test="headImg != null">
                head_img = #{headImg,jdbcType = BLOB},
            </if>
            <if test="createTime != null">
                create_time = #{createTime,jdbcType = TIMESTAMP},
            </if>
            id = #{id}
        where id = #{id}
    </update>
```
> `id = #{id} `这个条件可以最大限度保证方法不出错,如查询条件全部为null或空以及只有一个不是null也不为空

#### 1.3 在INSERT动态插入列中使用if ####
> xyz.codedog.simple.mapper.UserMapper.insert4(SysUser sysUser);

> 先修改`sys_user`表,在数据库中执行如下SQL语句给user_eail添加默认值test@codedog.xyz

```SQL
ALTER TABLE `sys_user` MODIFY COLUMN `user_email` VARCHAR ( 50 ) DEFAULT 'test@codedog.xyz' COMMENT '邮箱' AFTER `user_password`;
```
> 插入SQL语句如下
```SQL
# xyz/codedog/simple/mapper/UserMapper.xml
   <insert id="insert4" useGeneratedKeys="true" keyProperty="id">
        insert into sys_user(
        user_name,
        user_password,
        <if test="userEmail != null and userEmail != ''">
            user_email,
        </if>
        user_info,
        head_img,
        create_time
        )
        values (
        #{userName},
        #{userPassword},
        <if test="userEmail != null and userEmail != ''">
            #{userEmail},
        </if>
        #{headImg,jdbcType=BLOB},
        #{userInfo},
        #{createTime,jdbcType=TIMESTAMP}
        )
    </insert>
 ```
 
### 2. choose用法 ###
> `if`标签提供基本的条件判断,但无法实现`if...else`的逻辑,故而就需要用到`choose`,`when`,`otherwise`标签.`choose`元素包含`when(至少一个)`和`otherwise(0个或者1个)`

> `xyz.codedog.simple.mapper.UserMapper.selectByIdOrUserName(SysUser sysUser);`
