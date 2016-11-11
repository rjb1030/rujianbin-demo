CREATE TABLE `xinyunlian_static_log` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`create_date`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`modify_date`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`operator`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人' ,
`status`  int(2) NULL DEFAULT 0 COMMENT '静态化任务状态 0-排队中  5-执行中   10-完成' ,
`batch_id`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批次号' ,
`total_task_count`  int(11) NOT NULL DEFAULT 0 COMMENT '本次操作产生的区域任务总数' ,
`finish_task_count`  int(11) NOT NULL DEFAULT 0 COMMENT '已完成区域任务数' ,
`static_type`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '静态化类型 微信首页:wx_index pc首页:pc_index' ,
PRIMARY KEY (`id`),
INDEX `idx_statictype` (`static_type`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


CREATE TABLE `xinyunlian_static_log_item` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`static_log_id`  bigint(20) NOT NULL COMMENT 'xinyunlian_static_log表主键' ,
`item`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '具体某个区域id' ,
`create_date`  datetime NULL DEFAULT NULL ,
`modify_date`  datetime NULL DEFAULT NULL ,
`item_status`  int(2) NULL DEFAULT NULL COMMENT '单个区域静态化状态' ,
PRIMARY KEY (`id`),
INDEX `idx_staticlogid` (`static_log_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;
