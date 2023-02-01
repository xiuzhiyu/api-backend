use my_db;
 -- 接口信息
create table if not exists my_db.`interface_info`
(
`id` bigint not null auto_increment comment '主键' primary key,
`name` varchar(256) not null comment '名称',
`description` varchar(256) null comment '描述',
`url` varchar(512) not null comment '接口地址',
'requestParams' text not null comment '请求参数',
`requestHeader` text null comment '请求头',
`responseHeader` text null comment '响应头',
`status` int default 0 not null comment '接口状态（0-关闭，1-开启）',
`method` varchar(256) not null comment '请求类型',

`userId` bigint not null comment '用户名',
`createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
`update_Tme` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
`isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';

insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('姜苑博', '程泽洋', 'www.wilfredo-thiel.org', '蒋致远', '刘思远', 0, '尹峻熙', 4300567);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('高晓博', '洪晓博', 'www.maryln-howell.com', '黎远航', '范涛', 0, '阎浩', 44295);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('曾哲瀚', '万子骞', 'www.francisco-dubuque.biz', '姜乐驹', '韦立诚', 0, '段浩', 2);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('雷思源', '贺子涵', 'www.rusty-spinka.name', '郑俊驰', '范熠彤', 0, '王哲瀚', 6670393619);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('孟凯瑞', '贺鹏煊', 'www.steven-hoppe.co', '余烨华', '洪鑫鹏', 0, '朱天磊', 1882894148);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('陆思远', '马伟泽', 'www.rick-bins.info', '彭明辉', '钱擎苍', 0, '韦炫明', 534);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('孟靖琪', '万远航', 'www.casey-weimann.com', '杨思', '杜凯瑞', 0, '李擎宇', 545496912);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('曾烨伟', '陆子默', 'www.rolf-schneider.com', '丁昊焱', '史琪', 0, '郝烨霖', 3898296576);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('金越泽', '汪鹏飞', 'www.adelina-jast.co', '韦烨伟', '朱彬', 0, '余昊焱', 23621196);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('雷思', '罗立轩', 'www.evon-zieme.io', '钟语堂', '丁熠彤', 0, '崔越彬', 782);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('赵鹏', '姜锦程', 'www.annamaria-schoen.com', '王峻熙', '夏金鑫', 0, '莫瑾瑜', 52);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('沈鸿煊', '贾博涛', 'www.dalene-monahan.name', '薛明杰', '龚弘文', 0, '钟煜祺', 42);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('武煜祺', '贺锦程', 'www.glory-sawayn.org', '陈智宸', '曹烨华', 0, '姜瑾瑜', 4143183);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('韦语堂', '罗煜城', 'www.lauran-rempel.org', '戴越泽', '钱鸿煊', 0, '杜峻熙', 980250996);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('彭果', '严梓晨', 'www.alfonzo-parker.com', '孙梓晨', '徐建辉', 0, '江建辉', 4782);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('戴子默', '潘鹭洋', 'www.onita-balistreri.co', '戴绍齐', '罗果', 0, '刘楷瑞', 4393);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('夏思源', '戴鑫磊', 'www.gregg-stanton.biz', '侯耀杰', '姚立诚', 0, '尹弘文', 770);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('邹明辉', '覃驰', 'www.john-becker.io', '丁鑫磊', '白烨伟', 0, '彭绍辉', 759785432);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('冯乐驹', '段果', 'www.carlotta-bechtelar.name', '沈旭尧', '段鹤轩', 0, '顾嘉懿', 9380985288);
insert into my_db.`interface_info` (`name`, `desciption`, `url`, `requsetHeaher`, `responseHeader`, `status`, `method`, `userId`) values ('黄明哲', '苏煜祺', 'www.keila-wilkinson.com', '杜智宸', '潘子轩', 0, '贾鹏', 19459358);

create table if not exists my_db.`user.interface_info`
(
    id bigint not null auto_increment comment '主键' primary key,
    userId bigint not null comment  '调用用户 id',
    interfaceInfoId bigint not null  comment '接口 id',
    totalNum int default 0 not null comment '总调用次数 ',
leftNum int default 0 not null comment '剩余调用次数',
status int default 0 not null comment '0-正常，1-禁用',
createTime datetime default  CURRENT_TIMESTAMP not null comment '创建时间',
updateTime datetime default CURRENT_TIMESTAMP not null on update current_timestamp comment ' 更新时间',
isDelete tinyint default 0 not null comment '是否删除（0-未删，1-以删除）'


)comment '用户调用接口关系';