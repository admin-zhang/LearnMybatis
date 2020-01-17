# LearnMybatis #

## 目录

### 一. [环境搭建](#环境搭建) ####
### 二. 数据库表的建立 ###
### 三. 使用XML方式配置 ###

---

### 环境搭建 ###
#### 1. 创建maven项目 ####
##### 在Intellij IDEA中创建maven项目
![maven](https://github.com/admin-zhang/LearnMybatis/raw/master/images/01.png)

![maven](https://github.com/admin-zhang/LearnMybatis/raw/master/images/02.png)

![maven](https://github.com/admin-zhang/LearnMybatis/raw/master/images/03.png)

![maven](https://github.com/admin-zhang/LearnMybatis/raw/master/images/04.png)
##### 项目创建完成后,我们需要添加一些依赖才能使接下来的工作能够顺利进行.可以通过[maven中心仓库](https://mvnrepository.com/)来查找所需依赖坐标,具体的配置可参考[pom.xml](pom.xml) ####

#### 2. 创建数据库 ####
##### 创建一个名为mybatis的数据库
```SQL
CREATE DATABASE mybatis DEFAULT CHARACTERS SET utf8 COLLATE utf8_general_ci;

USE mybatis;

CREATE TABLE `country` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `countryname` varchar(255) DEFAULT NULL,
  `countrycode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

# 添加简单数据
INSERT INTO `country` VALUES (1, '中国', 'CN');
INSERT INTO `country` VALUES (2, '美国', 'US');
INSERT INTO `country` VALUES (3, '俄罗斯', 'RU');
INSERT INTO `country` VALUES (4, '英国', 'UK');
INSERT INTO `country` VALUES (5, '法国', 'FR');
```

#### 3. 配置MyBatis ####
> 使用XML方式来配置MyBatis

具体代码见[mybatis-config.xml](src/main/resources/mybatis-config.xml)

##### 属性简解 #####
1. `<setting>` 当中的LogImpl属性配置指定使用LOG4J输出日志
2. `<typeAliases>` 配置包的别名,确定类时同时使用类的全限定类名,如`xyz.codedog.simple.model.Country`
3. `<enviroments>` 配置了数据库的相关内容
4. `<mappers>` 配置了一个包含完整类路径的xml文件,是MyBatis的SQL语句和映射配置文件.