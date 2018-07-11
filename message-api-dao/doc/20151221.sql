#模板支持冻结解冻操作
alter table email_message_template add state tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-启用的  1-禁用的' AFTER `type`;

alter table push_message_template add state tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-启用的  1-禁用的' AFTER `type`;

alter table site_message_template add state tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-启用的  1-禁用的' AFTER `type`;

alter table sms_message_template add state tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-启用的  1-禁用的' AFTER `type`;
