/*
综合办公管理系统数据库设计
*/
-- drop table if exists `cm_template`;
-- create table `cm_template` (
--        `template_id` bigint(20) not null auto_increment comment 'id',
--        `type` varchar(20) not null comment '合同类型（0 合同类型a 1 合同类型b 2 合同类型c ……）',
--        `name` varchar(255) not null comment '范本名称',
--        `no` varchar(255) not null comment '范本编号',
--         `comment` varchar(255) default null comment '备注',
--        `enabled` varchar(10) not null comment '状态(0 启用 1 关闭)',
--        `deleted` int default 0  comment '逻辑删除',
--        `version` int default 0  comment '版本',
--       `create_by` varchar(255) default null comment '创建者',
--       `create_dept_by` varchar(255) default null comment '创建部门',
--       `update_by` varchar(255) default null comment '更新者',
--       `create_time` datetime default null comment '创建日期',
--       `update_time` datetime default null comment '更新时间',
--   primary key (`template_id`) using btree,
--   unique key `uniq_name` (`name`),
--   key `inx_enabled` (`enabled`)
-- ) engine=innodb auto_increment=1 default charset=utf8 row_format=compact comment='合同范本 ';


drop table if exists `cm_side`;
create table `cm_side` (
    `side_id` bigint(20) not null auto_increment comment 'id',
    `name` varchar(255) not null comment '相对方名称',
    `no` varchar(255) not null comment '编号',
    `type` bigint(20) not null comment '类型（0 已方 1 客户 2 供应商 3 合作伙伴 4 其他）',
    `con_per` varchar(255) not null comment '联系人',
    `con_tel` varchar(255) not null comment '联系电话',
    `bank` varchar(255) default null comment '开户银行',
    `bank_acc` varchar(255) default null comment '银行账号',
    `address` varchar(255) not null comment '地址',
    `addr_detail` varchar(255) not null comment '详细地址',
    `comment` varchar(255) default null comment '备注',
    `enabled` varchar(20)  not null comment '状态( 0 关闭 1 启用 )',
    `deleted` int default 0  comment '逻辑删除',
    `version` int default 0  comment '版本',
    `create_by` varchar(255) default null comment '创建者',
    `create_dept_by` varchar(255) default null comment '创建部门',
    `update_by` varchar(255) default null comment '更新者',
    `create_time` datetime default null comment '创建日期',
    `update_time` datetime default null comment '更新时间',
    primary key (`side_id`) using btree,
    unique key `uniq_name` (`name`)
) engine=innodb auto_increment=1 default charset=utf8 row_format=compact comment='合同相对方 ';


drop table if exists `cm_contract`;
create table `cm_contract` (
    `contract_id` bigint(20) not null auto_increment comment 'id',
    `name` varchar(255) not null comment '合同名称',
    `no` varchar(255) not null comment '合同编号',
    `party_a` bigint(20) not null comment '合同甲方',
    `charge_a` varchar(255) not null comment '甲方负责人',
    `party_b` bigint(20) not null comment '合同乙方',
    `charge_b` varchar(255) not null comment '乙方负责人',
    `type` bigint(20) default null comment '合同类型（0 合同类型a 1 合同类型b 2 合同类型c ……）',
    `dept` bigint(20) not null comment '部门/项目部',
    `rec_pay` varchar(20) not null comment '合同收付类型 （  0 付款 1 收款 2无）',
    `amount` decimal (15,2)  not null comment '合同金额/元',
    `status` bigint(20)  not null comment '合同状态（0 拟稿中 1 履行中 2 已完成 3 已作废 4 已归档）',
    `sign_time` datetime not null comment '签订日期',
    `effect_time` datetime not null comment '合同生效日期',
    `end_time` varchar(255) default null comment '合同结束日期',
    `key_terms` varchar(1000) default null comment '关键条款',
    `comment` varchar(255) default null comment '备注',
    `sort` int(5) default null comment '排序',
    `top` varchar (5) default null comment '置顶 (0 否 1 是)',
    `deleted` int default 0  comment '逻辑删除',
    `version` int default 0  comment '版本',
    `create_by` varchar(255) default null comment '创建者',
    `create_dept_by` varchar(255) default null comment '创建部门',
    `update_by` varchar(255) default null comment '更新者',
    `create_time` datetime default null comment '创建日期',
    `update_time` datetime default null comment '更新时间',
    primary key (`contract_id`) using btree,
    unique key `uniq_name` (`name`)
) engine=innodb auto_increment=1 default charset=utf8 row_format=compact comment='合同档案';


drop table if exists `cm_contract_users`;
create table `cm_contract_users` (
    `contract_id` bigint(20) not null comment '合同档案id',
    `user_id` bigint(20) not null comment '协作人id',
    `deleted` int default 0  comment '逻辑删除',
    `version` int default 0  comment '版本',
    primary key (`contract_id`,`user_id`) using btree
) engine=innodb default charset=utf8 row_format=compact comment='合同和协作人关联';


drop table if exists `cm_rp_item`;
create table `cm_rp_item` (
    `rpi_id` bigint(20) not null auto_increment comment 'id',
    `contract_id` bigint(20) not null  comment '合同档案id',
    `num` int(5) not null comment '序号',
    `no` varchar(255) not null comment '编号',
    `name` varchar(255) not null comment '收付款项(预付款 进度款 质保金)',
    `condition` varchar(255) not null comment '收付条件',
    `proportion` decimal(6,4) not null comment '收付比例',
    `amount` decimal(15,2) not null comment '应收付金额',
    `estimated_time` datetime not null comment '预计收付日期',
    `name_full` varchar(255) not null comment '收付款项全称',
    `deleted` int default 0  comment '逻辑删除',
    `version` int default 0  comment '版本',
    `create_by` varchar(255) default null comment '创建者',
    `create_dept_by` varchar(255) default null comment '创建部门',
    `update_by` varchar(255) default null comment '更新者',
    `create_time` datetime default null comment '创建日期',
    `update_time` datetime default null comment '更新时间',
    primary key (`rpi_id`) using btree
) engine=innodb auto_increment=1 default charset=utf8 row_format=compact comment='应收应付项';


drop table if exists `cm_rp_record`;
create table `cm_rp_record` (
    `rpr_id` bigint(20) not null auto_increment comment 'id',
    `no` varchar(255) not null comment '收付款编号',
    `estimated_time` datetime not null comment '收付日期',
    `payer` bigint(20) not null  comment '收付款人',
    `contract_id` bigint(20) not null  comment '合同档案id',
    `condition` varchar(255) not null comment '收款方名称',
    `method` bigint(20) not null  comment '收付方式（0  现金 1 汇款 3 其他）',
    `rec_pay_amount` decimal(15,2) not null comment '已收已付金额',
    `comment` varchar(255) default null comment '备注',
    `invoice_status` bigint(20) default 0 comment '发票情况 (0 没有 1 未到 2 已到)',
    `deleted` int default 0  comment '逻辑删除',
    `version` int default 0  comment '版本',
    `create_by` varchar(255) default null comment '创建者',
    `create_dept_by` varchar(255) default null comment '创建部门',
    `update_by` varchar(255) default null comment '更新者',
    `create_time` datetime default null comment '创建日期',
    `update_time` datetime default null comment '更新时间',
    primary key (`rpr_id`) using btree
) engine=innodb auto_increment=1 default charset=utf8 row_format=compact comment='合同收付款';


drop table if exists `cm_rp_detail`;
create table `cm_rp_detail` (
    `rpd_id` bigint(20) not null auto_increment comment 'id',
    `contract_id` bigint(20) not null  comment '合同档案id',
    `rpr_id` bigint(20) not null  comment '合同收付款id',
    `rpi_id` bigint(20) not null  comment '应收付款项id',
    `amount` decimal(15,2) not null comment '本次收付款金额',
    `deleted` int default 0  comment '逻辑删除',
    `version` int default 0  comment '版本',
    `create_by` varchar(255) default null comment '创建者',
    `create_dept_by` varchar(255) default null comment '创建部门',
    `update_by` varchar(255) default null comment '更新者',
    `create_time` datetime default null comment '创建日期',
    `update_time` datetime default null comment '更新时间',
    primary key (`rpd_id`) using btree
) engine=innodb auto_increment=1 default charset=utf8 row_format=compact comment='合同收付明细';


 drop table if exists `cm_file`;
 create table `cm_file` (
    `file_id` bigint(20) not null auto_increment comment 'id',
    `relation_id` bigint(20) not null  comment '附件关联id',
    `storage_id` bigint(20) not null  comment '本地存储id',
    `deleted` int default 0  comment '逻辑删除',
    `version` int default 0  comment '版本',
    `create_by` varchar(255) default null comment '创建者',
    `create_dept_by` varchar(255) default null comment '创建部门',
    `update_by` varchar(255) default null comment '更新者',
    `create_time` datetime default null comment '创建日期',
    `update_time` datetime default null comment '更新时间',
    primary key (`file_id`) using btree
 ) engine=innodb auto_increment=1 default charset=utf8 row_format=compact comment='附件';

 drop table if exists `cm_change_log`;
 create table `cm_change_log` (
    `change_log_id` bigint(20) not null auto_increment  comment 'id',
    `log_id` bigint(20) not null  comment '操作日志id',
    `operator_id` varchar(255) not null  comment '操作人id',
    `operator_dept` varchar(255) not null  comment '操作人部门',
    `operator` varchar(255) not null  comment '操作人',
    `operator_time` datetime not null  comment '操作时间',
    `operator_describe` varchar(255) not null  comment '操作描述',
    `operator_content` varchar(1000) not null  comment '操作内容',
    `deleted` int default 0  comment '逻辑删除',
    `version` int default 0  comment '版本',
    `create_by` varchar(255) default null comment '创建者',
    `create_dept_by` varchar(255) default null comment '创建部门',
    `update_by` varchar(255) default null comment '更新者',
    `create_time` datetime default null comment '创建日期',
    `update_time` datetime default null comment '更新时间',
   primary key (`change_log_id`) using btree
 ) engine=innodb auto_increment=1 default charset=utf8 row_format=compact comment='变更记录';

/*附件表:合同附件、发票附件、其他文件、合同相对方资质*/


drop table if exists `tool_weixin_config`;
create table `tool_weixin_config` (
  `config_id` bigint(20) not null comment 'id',
  `corpid` varchar(255) not null comment '企业id',
  `corpsecret` varchar(255) not null comment '凭证密钥',
  `agentid` varchar(255) not null comment '	应用id',
  `comment` varchar(255) default null comment '备注',
  primary key (`config_id`) using btree
) engine=innodb default charset=utf8 row_format=compact comment='企业微信配置';



/*部门表添加企业微信部门ID 字段*/
alter table sys_dept  add wx_dept_id  Long  not null;

