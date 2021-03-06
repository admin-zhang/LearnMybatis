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

### 数据库表的建立 ###
> 在名为mybatis的数据库内,创建`sys_user`,`sys_privilege`,`sys_role`,`sys_role_privilege`,`sys_user_role`几个表,具体代码如下,附件可见[mybatis.sql](docs/mybatis.sql)
```SQL
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `countryname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `countrycode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` VALUES (1, '中国', 'CN');
INSERT INTO `country` VALUES (2, '美国', 'US');
INSERT INTO `country` VALUES (3, '俄罗斯', 'RU');
INSERT INTO `country` VALUES (4, '英国', 'UK');
INSERT INTO `country` VALUES (5, '法国', 'FR');

-- ----------------------------
-- Table structure for sys_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `privilege_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `privilege_url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限URL',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_privilege
-- ----------------------------
INSERT INTO `sys_privilege` VALUES (1, '用户管理', '/users');
INSERT INTO `sys_privilege` VALUES (2, '角色管理', '/roles');
INSERT INTO `sys_privilege` VALUES (3, '系统日志', '/logs');
INSERT INTO `sys_privilege` VALUES (4, '人员维护', '/persons');
INSERT INTO `sys_privilege` VALUES (5, '单位维护', '/companies');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `enabled` int(11) NULL DEFAULT NULL COMMENT '有效标志',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 1, 1, '2020-01-10 15:49:41');
INSERT INTO `sys_role` VALUES (2, '普通用户', 1, 1, '2020-01-10 15:49:41');

-- ----------------------------
-- Table structure for sys_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_privilege`;
CREATE TABLE `sys_role_privilege`  (
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `privilege_id` bigint(20) NULL DEFAULT NULL COMMENT '权限ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_privilege
-- ----------------------------
INSERT INTO `sys_role_privilege` VALUES (1, 1);
INSERT INTO `sys_role_privilege` VALUES (1, 3);
INSERT INTO `sys_role_privilege` VALUES (1, 2);
INSERT INTO `sys_role_privilege` VALUES (2, 4);
INSERT INTO `sys_role_privilege` VALUES (2, 5);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `user_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `user_info` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '简介',
  `head_img` blob NULL COMMENT '头像',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1006 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '123456', 'admin@163.com', '管理员', NULL, '2020-01-10 15:46:59');
INSERT INTO `sys_user` VALUES (1001, 'test', '123456', 'test@163.com', '测试用户', NULL, '2020-01-10 15:48:15');
INSERT INTO `sys_user` VALUES (1005, 'test1', '123456', 'test1@163.com', 'test1 info', 0x010203, '2020-01-17 11:13:59');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (1001, 2);

SET FOREIGN_KEY_CHECKS = 1;
```