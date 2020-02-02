## MyBatis的动态SQL在XML中支持的几种标签 ##

* if
* choose(when,otherwise)
* trim(where,set)
* foreach
* bind

### 1. if用法 ###
#### 1.1 在WHERE条件中使用if ####
> [xyz.codedog.simple.mapper.UserMapper](https://github.com/admin-zhang/LearnMybatis/tree/master/src/main/java/xyz/codedog/simple/mapper/UserMapper.java).selectByUser(SysUser sysUser);
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
> [xyz.codedog.simple.mapper.UserMapper](https://github.com/admin-zhang/LearnMybatis/tree/master/src/main/java/xyz/codedog/simple/mapper/UserMapper.java).updateByIdSelective(SysUser sysUser);
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
> [xyz.codedog.simple.mapper.UserMapper](https://github.com/admin-zhang/LearnMybatis/tree/master/src/main/java/xyz/codedog/simple/mapper/UserMapper.java).insert4(SysUser sysUser);

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

> [xyz.codedog.simple.mapper.UserMapper](https://github.com/admin-zhang/LearnMybatis/tree/master/src/main/java/xyz/codedog/simple/mapper/UserMapper.java).selectByIdOrUserName(SysUser sysUser);
```SQL
# xyz/codedog/simple/mapper/UserMapper.xml
    <select id="selectByIdOrUserName" resultType="xyz.codedog.simple.model.SysUser">
        SELECT
            id,
            user_name,
            user_password,
            user_email,
            user_info,
            head_img,
            create_time
        FROM
            sys_user
        WHERE
            1 = 1
        <choose>
            <when test="id != null">
                and id = #{id}
            </when>
            <when test="userName != null and userName != ''">
                and user_name = #{userName}
            </when>
            <otherwise>
                and 1 = 2
            </otherwise>
        </choose>
    </select>
 ```
### 3. where、set、trim用法 ###
#### 3.1 where用法 ####
> [xyz.codedog.simple.mapper.UserMapper](https://github.com/admin-zhang/LearnMybatis/tree/master/src/main/java/xyz/codedog/simple/mapper/UserMapper.java).selectByUser1(SysUser sysUser);

> `if`条件不满足的时候,where元素中没有内容,即在SQL中不会出现where

```SQL
    <select id="selectByUser1" resultType="xyz.codedog.simple.model.SysUser">
        SELECT
            id,
            user_name userName,
            user_password userPassword,
            user_email userEmail,
            user_info userInfo,
            head_img headImg,
            create_time createTime
        FROM
            sys_user
            <where>
                <if test="userName != null and userName != ''">
                    and user_name like concat('%',#{userName},'%')
                </if>
                <if test="userEmail != null and userEmail != ''">
                    and user_email = #{userEmail}
                </if>
            </where>
    </select>
```

#### 3.2 set用法 ####
> [xyz.codedog.simple.mapper.UserMapper](https://github.com/admin-zhang/LearnMybatis/tree/master/src/main/java/xyz/codedog/simple/mapper/UserMapper.java).updateByIdSelective1(SysUser sysUser)
```SQL
    <update id="updateByIdSelective1">
        update sys_user
        <set>
            <if test="userName != null and userName !=''">
                user_name = #{userName},
            </if>
            <if test="userPassword != null and userPassword != ''">
                user_password = #{userPassword},
            </if>
            <if test="userEmail != null and userEmail != ''">
                user_email = #{userEmail},
            </if>
            <if test="userInfo != null and userInfo != ''">
                user_info = #{userInfo},
            </if>
            <if test="headImg != null">
                head_img = #{headImg,jdbcType=BLOB},
            </if>
            <if test="createTime != null">
                create_time = #{createTime,jdbcType=TIMESTAMP},
            </if>
            id = #{id},
        </set>
        where id = #{id}
    </update>
```

### 4. foreach用法 ###
#### 4.1 in 集合 ####
> [xyz.codedog.simple.mapper.UserMapper](https://github.com/admin-zhang/LearnMybatis/tree/master/src/main/java/xyz/codedog/simple/mapper/UserMapper.java).selectByIdList(List<Long> idList);
##### foreach属性 #####
* collection:必填,值为要迭代循环的属性名
* item:变量名,值为从迭代对象中取出的每一个值
* index:索引的属性名,集合数组时为当前索引值;Map类型时,值为Map的key
* open:整个循环内容开头的字符串
* cloase:整个循环内容结尾的字符串
* separator:每次循环的分隔符
```SQL
    <select id="selectByIdList" resultType="xyz.codedog.simple.model.SysUser">
        SELECT
            id,
            user_name userName,
            user_password userPassword,
            user_email userEmail,
            user_info userInfo,
            head_img headImg,
            create_time createTime
        FROM
            sys_user
        WHERE id in 
        <foreach collection="list" open="(" close=")" separator="," item="id" index="i">
            #{id}
        </foreach>
    </select>
```
#### 4.2 foreach实现批量插入 ####
> 批量插入语法如下
```SQL
INSERT INTO tablename (column-a,[column-b,...])
VALUES ('value-1a',['value-1b',...]),
        ('value-2a',['value-2b',...]),
        ...
```

> [xyz.codedog.simple.mapper.UserMapper](https://github.com/admin-zhang/LearnMybatis/tree/master/src/main/java/xyz/codedog/simple/mapper/UserMapper.java).selectByIdList(List<Long> idList);
```SQL
    <insert id="insertList">
        insert into sys_user(
        user_name,user_password,user_email,user_info,head_img,create_time
        )values 
        <foreach collection="list" item="user" separator=",">
            (
            #{user.userName},#{user.userPassword},#{user.userEmail},
            #{user.userInfo},#{user.headImg,jdbcType=BLOB},
            #{user.createTime,jdbcType=TIMESTAMP}
            )
        </foreach>
    </insert>
```
#### 4.3 foreach实现动态UPDATE ####
> [xyz.codedog.simple.mapper.UserMapper](https://github.com/admin-zhang/LearnMybatis/tree/master/src/main/java/xyz/codedog/simple/mapper/UserMapper.java).updateByMap(Map<String,Object> map);

```SQL
    <update id="updateByMap">
        update sys_user
        set 
        <foreach collection="_parameter" item="val" index="key" separator=",">
            ${key} = #{val}
        </foreach>
        where id = #{id}
    </update>
```
### 5. bind用法 ###
> 对使用like查询条件进行修改,如 UserMapper.xml中的selectByUser方法,其修改后代码如下:
```SQL
<if test="userName != null and userName != ''">
    <bind name = "userNameLike" value="'%' + userName  + '%'"/>
    and user_name like #{userNameLIke}
</if>
```
### 6. 多数据库支持 ###
> 只需要在[mybatis-config.xml](https://github.com/admin-zhang/LearnMybatis/tree/master/src/main/resources/mybatis-config.xml)文件中加入`databaseIdProvider`配置即可
```SQL
    <databaseIdProvider type="DB_VENDOR">
        <property name="SQL Server" value="sqlserver"/>
        <property name="DB2" value="db2"/>  
        <property name="Oracle" value="oracle" />
        <property name="MySQL" value="mysql" />
        <property name="PostgreSQL" value="postgresql" />
        <property name="Derby" value="derby" />
        <property name="HSQL" value="hsqldb" />
        <property name="H2" value="h2" />
    </databaseIdProvider>
```
### 7. OGNL用法 ###
1. e1 or e2
2. e1 and e2
3. e1 == e2 或 e1 eq e2
4. e1 != e2 或 e1 neq e2
5. e1 lt e2 : 小于
6. e1 lte e2:小于等于,其他表示为gt(大于),gte(大于等于)
7. e1 + e2 , e1 * e2 . e1/e2 , e1 - e2 , e1%e2
8. !e 或 not e:非,取反
9. e.method(args):调用对象方法
10. e.property:对象属性值
11. e1[ e2 ]:按索引取值(List,数组和Map) 
12. @class@method(args):调用类的静态方法
13. @class@field: 调用类的静态字段值

> 如表达式12通常用于简化一些校验,或者进行更特殊的校验.
```SQL
<if test="@xyz.codedog.util.StringUtil@isNotEmpty(userName)">
  and user_name like concat('%',#{userName},'%')
</if>
```
> 其中StringUtil类如下

```JAVA 
package xyz.codedog.util;

public class StringUtil {
    public static boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }
    
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
```



