-- ----------------------------
-- 新闻管理系统 - 补充菜单和角色关联脚本
-- 请在 MySQL 中执行此脚本以补全缺失的菜单数据
-- 执行前请确保已先执行 news_tables.sql 创建业务表
-- ----------------------------

-- ================================
-- 一、补充缺失的二级菜单（2003-2007）
-- ================================

-- 用户管理菜单（如果不存在则插入）
INSERT IGNORE INTO sys_menu VALUES(2003, '用户管理', '2000', '3', '/news/user', '', 'C', '0', '1', 'news:user:view', 'fa fa-users', 'admin', SYSDATE(), '', NULL, '用户管理菜单');
INSERT IGNORE INTO sys_menu VALUES(2300, '用户查询', '2003', '1', '#', '', 'F', '0', '1', 'news:user:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT IGNORE INTO sys_menu VALUES(2301, '用户查看', '2003', '2', '#', '', 'F', '0', '1', 'news:user:detail', '#', 'admin', SYSDATE(), '', NULL, '');

-- 评论管理菜单（如果不存在则插入）
INSERT IGNORE INTO sys_menu VALUES(2004, '评论管理', '2000', '4', '/news/comment', '', 'C', '0', '1', 'news:comment:view', 'fa fa-comments', 'admin', SYSDATE(), '', NULL, '评论管理菜单');
INSERT IGNORE INTO sys_menu VALUES(2400, '评论查询', '2004', '1', '#', '', 'F', '0', '1', 'news:comment:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT IGNORE INTO sys_menu VALUES(2401, '评论审核', '2004', '2', '#', '', 'F', '0', '1', 'news:comment:audit', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT IGNORE INTO sys_menu VALUES(2402, '评论删除', '2004', '3', '#', '', 'F', '0', '1', 'news:comment:remove', '#', 'admin', SYSDATE(), '', NULL, '');

-- 频道管理菜单（如果不存在则插入）
INSERT IGNORE INTO sys_menu VALUES(2005, '频道管理', '2000', '5', '/news/channel', '', 'C', '0', '1', 'news:channel:view', 'fa fa-tv', 'admin', SYSDATE(), '', NULL, '频道管理菜单');
INSERT IGNORE INTO sys_menu VALUES(2500, '频道查询', '2005', '1', '#', '', 'F', '0', '1', 'news:channel:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT IGNORE INTO sys_menu VALUES(2501, '频道新增', '2005', '2', '#', '', 'F', '0', '1', 'news:channel:add', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT IGNORE INTO sys_menu VALUES(2502, '频道修改', '2005', '3', '#', '', 'F', '0', '1', 'news:channel:edit', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT IGNORE INTO sys_menu VALUES(2503, '频道删除', '2005', '4', '#', '', 'F', '0', '1', 'news:channel:remove', '#', 'admin', SYSDATE(), '', NULL, '');

-- 活动管理菜单（如果不存在则插入）
INSERT IGNORE INTO sys_menu VALUES(2006, '活动管理', '2000', '6', '/news/activity', '', 'C', '0', '1', 'news:activity:view', 'fa fa-calendar', 'admin', SYSDATE(), '', NULL, '活动管理菜单');
INSERT IGNORE INTO sys_menu VALUES(2600, '活动查询', '2006', '1', '#', '', 'F', '0', '1', 'news:activity:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT IGNORE INTO sys_menu VALUES(2601, '活动新增', '2006', '2', '#', '', 'F', '0', '1', 'news:activity:add', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT IGNORE INTO sys_menu VALUES(2602, '活动修改', '2006', '3', '#', '', 'F', '0', '1', 'news:activity:edit', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT IGNORE INTO sys_menu VALUES(2603, '活动删除', '2006', '4', '#', '', 'F', '0', '1', 'news:activity:remove', '#', 'admin', SYSDATE(), '', NULL, '');

-- 举报管理菜单（如果不存在则插入）
INSERT IGNORE INTO sys_menu VALUES(2007, '举报管理', '2000', '7', '/news/report', '', 'C', '0', '1', 'news:report:view', 'fa fa-flag', 'admin', SYSDATE(), '', NULL, '举报管理菜单');
INSERT IGNORE INTO sys_menu VALUES(2700, '举报查询', '2007', '1', '#', '', 'F', '0', '1', 'news:report:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT IGNORE INTO sys_menu VALUES(2701, '举报处理', '2007', '2', '#', '', 'F', '0', '1', 'news:report:handle', '#', 'admin', SYSDATE(), '', NULL, '');


-- ================================
-- 二、为普通角色(role_id=2)关联新闻管理菜单权限
--    超级管理员(role_id=1)自动拥有所有权限，无需关联
-- ================================

-- 新闻管理一级目录
INSERT IGNORE INTO sys_role_menu VALUES('2', '2000');

-- 新闻分类及其按钮
INSERT IGNORE INTO sys_role_menu VALUES('2', '2001');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2100');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2101');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2102');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2103');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2104');

-- 新闻内容及其按钮
INSERT IGNORE INTO sys_role_menu VALUES('2', '2002');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2200');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2201');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2202');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2203');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2204');

-- 用户管理及其按钮
INSERT IGNORE INTO sys_role_menu VALUES('2', '2003');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2300');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2301');

-- 评论管理及其按钮
INSERT IGNORE INTO sys_role_menu VALUES('2', '2004');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2400');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2401');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2402');

-- 频道管理及其按钮
INSERT IGNORE INTO sys_role_menu VALUES('2', '2005');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2500');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2501');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2502');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2503');

-- 活动管理及其按钮
INSERT IGNORE INTO sys_role_menu VALUES('2', '2006');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2600');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2601');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2602');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2603');

-- 举报管理及其按钮
INSERT IGNORE INTO sys_role_menu VALUES('2', '2007');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2700');
INSERT IGNORE INTO sys_role_menu VALUES('2', '2701');


-- ================================
-- 三、验证：查询新闻管理下的所有菜单
-- ================================
SELECT menu_id, menu_name, parent_id, order_num, menu_type FROM sys_menu WHERE parent_id = 2000 ORDER BY order_num;
