ALTER TABLE `email_message_template`
ADD COLUMN `description`  varchar(200) NULL AFTER `from_address`;

ALTER TABLE `push_message_template`
ADD COLUMN `description`  varchar(200) NULL AFTER `addition`;

ALTER TABLE `site_message_template`
ADD COLUMN `description`  varchar(200) NULL AFTER `sender`;

ALTER TABLE `sms_message_template`
ADD COLUMN `description`  varchar(200) NULL AFTER `channel`;