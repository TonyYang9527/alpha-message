-- 客户表
DROP TABLE IF EXISTS sms_message;
CREATE TABLE sms_message (
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sms_message_template_id bigint(20) NOT NULL,
  mobiles varchar(3000) NOT NULL,
  priority tinyint(4) NOT NULL,
  state tinyint(4) NOT NULL,
  immediate tinyint(4) NOT NULL,
  schedule_time datetime DEFAULT NULL,
  expired_time datetime NOT NULL,
  sent_time datetime DEFAULT NULL,
  created_time datetime NOT NULL,
  created_by varchar(255) NOT NULL,
  sent_result varchar(50) DEFAULT NULL,
  `DataChange_LastTime` timestamp
);

DROP TABLE IF EXISTS sms_message_property;
CREATE TABLE sms_message_property (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  sms_message_id bigint(20) NOT NULL COMMENT '消息表主键',
  key varchar(50) DEFAULT NULL,
  value varchar(500) DEFAULT NULL,
  created_time datetime NOT NULL,
    `DataChange_LastTime` timestamp
);

DROP TABLE IF EXISTS sms_message_template;
CREATE TABLE sms_message_template (
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  content varchar(500) NOT NULL,
  priority tinyint(4) NOT NULL,
  `type` smallint(6) NOT NULL,
  created_time datetime NOT NULL,
  created_by varchar(36) NOT NULL,
  updated_time datetime,
  updated_by varchar(36),
  deleted_time datetime,
  deleted_by varchar(36),
  deleted tinyint(4) NOT NULL,
  `DataChange_LastTime` timestamp
);

DROP TABLE IF EXISTS message_job;
CREATE TABLE  message_job (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  last_schedule_time  datetime DEFAULT NULL  ,
  last_start_time  datetime NOT NULL ,
  last_end_time  datetime NOT NULL ,
  updated_time  datetime NOT NULL,
  created_time   datetime NOT NULL,
  DataChange_LastTime timestamp 
);

DROP TABLE IF EXISTS email_message;
CREATE TABLE `email_message` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `email_message_template_id` bigint(20) NOT NULL COMMENT '消息模板主键',
  `to_address` varchar(3000) NOT NULL COMMENT '接收方地址，多个地址用''|''分割',
  `cc_address` varchar(3000) DEFAULT NULL COMMENT '接收方地址，多个地址用''|''分割',
  `bcc_address` varchar(3000) DEFAULT NULL COMMENT '接收方地址，多个地址用''|''分割',
  `priority` tinyint(4) NOT NULL COMMENT '优先级：1-99。1为最优先。',
  `state` tinyint(4) NOT NULL COMMENT '状态，10待发送，15 JOB已处理，20发送中，30，已发送，99 发送失败',
  `immediate` tinyint(4) NOT NULL COMMENT '是否即时消息',
  `schedule_time` datetime NOT NULL COMMENT '预期发送时间',
  `expired_time` datetime NOT NULL COMMENT '过期时间',
  `sent_time` datetime DEFAULT NULL COMMENT '发送时间',
  `created_time` datetime NOT NULL,
  `created_by` varchar(36) NOT NULL,
  `DataChange_LastTime` timestamp
);

DROP TABLE IF EXISTS push_message_template;
CREATE TABLE `push_message_template` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '模板名字',
  `title` varchar(50) DEFAULT NULL COMMENT '消息标题',
  `content` varchar(500) NOT NULL COMMENT '消息标题',
  `type` smallint(6) NOT NULL COMMENT '消息类型',
  `priority` tinyint(4) NOT NULL COMMENT '优先级',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `created_by` varchar(36) NOT NULL,
  `updated_time` datetime DEFAULT NULL,
  `updated_by` varchar(36) DEFAULT NULL,
  `deleted_time` datetime DEFAULT NULL,
  `deleted_by` varchar(36) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT NULL,
  `DataChange_LastTime` timestamp
);

DROP TABLE IF EXISTS push_message_property;
CREATE TABLE `push_message_property` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `push_message_id` bigint(20) NOT NULL COMMENT '消息表主键',
  `prop_key` varchar(50) NOT NULL COMMENT '内容key',
  `prop_value` varchar(500) DEFAULT NULL COMMENT '内容value',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `DataChange_LastTime` timestamp
);

DROP TABLE IF EXISTS push_message;
CREATE TABLE `push_message` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `push_message_template_id` bigint(20) NOT NULL COMMENT '消息模板主键',
  `receiver_id` varchar(50) DEFAULT NULL COMMENT '接受者的user id',
  `device_id` varchar(120) NOT NULL COMMENT '接受者的设备号',
  `device_type` tinyint(4) NOT NULL COMMENT '接受者的设备类型。1为IOS，2为Android',
  `priority` tinyint(4) NOT NULL COMMENT '优先级：1-99。1为最优先。',
  `state` tinyint(4) NOT NULL COMMENT '状态，10待发送，15 JOB已处理，20发送中，30，已发送，99 发送失败',
  `immediate` tinyint(4) NOT NULL COMMENT '是否即时消息',
  `schedule_time` datetime NOT NULL COMMENT '预期发送时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `sent_time` datetime DEFAULT NULL COMMENT '发送时间',
  `sent_result` varchar(50) DEFAULT NULL COMMENT '发送结果',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `created_by` varchar(36) NOT NULL COMMENT '创建者。例如运营用户的id。',
  `DataChange_LastTime` timestamp
);
