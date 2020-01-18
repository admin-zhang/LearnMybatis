## MyBatis的动态SQL在XML中支持的几种标签

* if
* choose(when,otherwise)
* trim(where,set)
* foreach
* bind

### 1. if用法
#### 1.1 在WHERE条件中使用if
> xyz.codedog.simple.mapper.UserMapper.selectByUser()
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