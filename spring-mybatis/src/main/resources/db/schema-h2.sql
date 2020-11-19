DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	role_id VARCHAR(50) NULL DEFAULT NULL COMMENT '角色ID',
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS role;

CREATE TABLE role
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '角色名称',
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS auth;

CREATE TABLE auth
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '权限名称',
	PRIMARY KEY (id)
);
