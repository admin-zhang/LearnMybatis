# INSERT的用法 #
## 属性 ##
### `<id>`:命名空间中的唯一标识符,可用来代表这条语句 ###
### ~~`<parameterType>`~~:即将传入的语句参数的完全限定类名或别名,[可选属性],*不建议配置* ###
### `<flushCache>`:默认值为`true`,一旦被调用,将会清空一级缓存和二级缓存 ###
### `<timeout>`:抛出异常之前,驱动程序等待数据库返回请求结果的秒数 ###
### `<statementType>`:对于STATEMENT,PREPARED,CALLABLE,MyBatis会分别使用对应的Statement,PreparedStatement,CallableStatement,默认值为`PREPARED` ###
### `<useGeneratedKeys>`:使用JDBC的getGeneratedkeys方法取出数据库内部生成的主键.默认值是`false` ###
### `<keyProperty>`:通过getGeneratedkeys获取主键值后将要赋值的属性名 ###
### `<keyColumn>`:仅对INSERT和UPDATE有用. ###
### `<databaseId>`:如果配置databaseIdProvider,Mybatis会加载所有不带databaseId或匹配当前databaseId的语句 ###
> Tips:
>> values值通过#{property}方式从参数中取出属性值
>> 对一些特殊的数据类型,指定具体的jdbcType值