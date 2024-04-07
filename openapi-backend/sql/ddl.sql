-- 创建库
create database if not exists openapi;

-- 切换库
use openapi;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户';


-- api接口表
CREATE TABLE interface_info
(
    id              INT AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    user_id         BIGINT COMMENT '用户ID',
    name            VARCHAR(255) COMMENT '名称',
    method          VARCHAR(255) COMMENT '请求类型',
    url             VARCHAR(255) COMMENT '接口地址',
    status          INT COMMENT '接口状态（0-关闭，1-开启）',
    request_params  TEXT COMMENT '请求参数',
    response_params TEXT COMMENT '响应参数',
    request_header  TEXT COMMENT '请求头',
    response_header TEXT COMMENT '响应头',
    description     TEXT COMMENT '接口描述',
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    is_delete       INT      DEFAULT 0 COMMENT '删除标志(0 未删除 1 已删除)'
);

-- 用户接口关联表
CREATE TABLE IF NOT EXISTS user_interface_info
(
    id              INT AUTO_INCREMENT PRIMARY KEY COMMENT '接口ID',
    userId          INT                 NOT NULL COMMENT '调用用户ID',
    interfaceInfoId INT                 NOT NULL COMMENT '接口ID',
    totalNum        INT       DEFAULT 0 NOT NULL COMMENT '总调用次数',
    status          INT       DEFAULT 0 NOT NULL COMMENT '0-正常 1-禁用',
    updateTime      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    createTime      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    isDelete        TINYINT   DEFAULT 0 NOT NULL COMMENT '删除标志(0 未删除 1 已删除)'

) comment '用户接口关系表';

