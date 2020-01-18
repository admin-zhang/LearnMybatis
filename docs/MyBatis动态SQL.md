## MyBatis的动态SQL在XML中支持的几种标签 ##

* if
* choose(when,otherwise)
* trim(where,set)
* foreach
* bind

### 1. if用法 ###
#### 1.1 在WHERE条件中使用if ####
> xyz.codedog.simple.mapper.UserMapper.selectByUser(SysUser sysUser);
```SQL
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
