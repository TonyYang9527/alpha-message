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
  id bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sms_message_id bigint(20) NOT NULL COMMENT '消息表主键',
  prop_key varchar(50) DEFAULT NULL,
  prop_value varchar(500) DEFAULT NULL,
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

DROP TABLE IF EXISTS email_message_attachment;
CREATE TABLE `email_message_attachment` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `email_message_id` bigint(20) NOT NULL COMMENT '消息表主键',
  `name` varchar(50) NOT NULL COMMENT '附件名称，会显示在邮件里',
  `path` varchar(100) DEFAULT NULL COMMENT '附件的文件路径',
  `created_time` datetime NOT NULL,
  `DataChange_LastTime` timestamp
);

DROP TABLE IF EXISTS email_message_property;
CREATE TABLE `email_message_property` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `email_message_id` bigint(20) NOT NULL COMMENT '消息表主键',
  `prop_key` varchar(50) DEFAULT NULL,
  `prop_value` varchar(500) NOT NULL,
  `created_time` datetime NOT NULL,
  `DataChange_LastTime` timestamp
);

DROP TABLE IF EXISTS email_message_template;
CREATE TABLE `email_message_template` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '模板名字',
  `title` varchar(100) DEFAULT NULL COMMENT '消息标题',
  `content` varchar(5000) NOT NULL COMMENT '消息内容',
  `type` smallint(6) NOT NULL COMMENT '消息类型',
  `priority` tinyint(4) NOT NULL COMMENT '优先级：1-99。1为最优先。',
  `from_address` varchar(50) DEFAULT NULL COMMENT '发送地址方',
  `sender_name` varchar(100) DEFAULT NULL COMMENT '发送者名称',
  `created_time` datetime NOT NULL,
  `created_by` varchar(36) NOT NULL,
  `updated_time` datetime DEFAULT NULL,
  `updated_by` varchar(36) DEFAULT NULL,
  `deleted_time` datetime DEFAULT NULL,
  `deleted_by` varchar(36) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL,
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
  `addition` varchar(500) DEFAULT NULL,
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

DROP TABLE IF EXISTS `push_message`;
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


CREATE TABLE `site_message` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `site_message_content_id` bigint(20) DEFAULT NULL COMMENT '消息内容表主键',
  `receiver_id` varchar(50) DEFAULT NULL COMMENT '消息接受者，用户pkey',
  `sender` varchar(50) DEFAULT NULL COMMENT '消息发送者，用户pkey或者其他',
  `type` smallint(6) DEFAULT NULL COMMENT '消息类型',
  `priority` tinyint(4) DEFAULT NULL COMMENT '优先级：1-99。1为最优先。',
  `state` tinyint(4) NOT NULL COMMENT '状态，0 草稿，10待发送，15 JOB已处理，20发送中，30，已发送，40 已阅读，99 发送失败',
  `immediate` tinyint(4) DEFAULT NULL COMMENT '是否即时消息',
  `schedule_time` datetime DEFAULT NULL COMMENT '预期发送时间',
  `expired_time` datetime DEFAULT NULL COMMENT '过期时间',
  `sent_time` datetime DEFAULT NULL COMMENT '发送时间',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `deleted_time` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `DataChange_LastTime` timestamp
);

CREATE TABLE `site_message_content` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `site_message_template_id` bigint(20) DEFAULT NULL COMMENT '消息模板主键',
  `title` varchar(50) DEFAULT NULL COMMENT '消息标题',
  `content` varchar(500) DEFAULT NULL COMMENT '消息内容',
  `addition` varchar(400) DEFAULT NULL COMMENT '消息附加信息',
  `type` smallint(6) DEFAULT NULL COMMENT '消息类型',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(36) DEFAULT NULL COMMENT '创建消息的人',
  `updated_time` datetime DEFAULT NULL COMMENT '修改时间',
  `updated_by` varchar(36) DEFAULT NULL COMMENT '修改消息的人',
  `DataChange_LastTime` timestamp
);

CREATE TABLE `site_message_property` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `site_message_id` bigint(20) NOT NULL COMMENT '消息表主键',
  `prop_key` varchar(50) NOT NULL,
  `prop_value` varchar(500) DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `DataChange_LastTime` timestamp
);


CREATE TABLE `site_message_template` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '模板名字',
  `title` varchar(50) DEFAULT NULL COMMENT '消息标题',
  `content` varchar(500) DEFAULT NULL COMMENT '消息内容',
  `addition` varchar(400) DEFAULT NULL COMMENT '消息附加信息',
  `type` smallint(6) NOT NULL COMMENT '消息类型',
  `priority` tinyint(4) NOT NULL COMMENT '优先级：1-99。1为最优先。',
  `sender` varchar(50) NOT NULL COMMENT '消息发送者',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `created_by` varchar(36) NOT NULL COMMENT '创建者。例如运营用户的id。',
  `updated_time` datetime DEFAULT NULL,
  `updated_by` varchar(36) DEFAULT NULL,
  `deleted_time` datetime DEFAULT NULL,
  `deleted_by` varchar(36) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未删除；1：删除',
  `DataChange_LastTime` timestamp
);