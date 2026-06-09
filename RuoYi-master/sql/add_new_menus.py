# -*- coding: utf-8 -*-
import subprocess

sql = """SET NAMES utf8mb4;

-- 主题管理 (2800)
INSERT INTO sys_menu VALUES(2800, '主题管理', 2000, '4', '/system/news/topic', '', 'C', '0', '1', 'news:topic:view', 'fa fa-tags', 1, SYSDATE(), '', NULL, '主题管理');
INSERT INTO sys_menu VALUES(2801, '主题查询', 2800, '1', '', '', 'F', '0', '1', 'news:topic:list', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2802, '主题新增', 2800, '2', '', '', 'F', '0', '1', 'news:topic:add', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2803, '主题修改', 2800, '3', '', '', 'F', '0', '1', 'news:topic:edit', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2804, '主题删除', 2800, '4', '', '', 'F', '0', '1', 'news:topic:remove', '#', 1, SYSDATE(), '', NULL, '');

-- 作者管理 (2900)
INSERT INTO sys_menu VALUES(2900, '作者管理', 2000, '5', '/system/news/author', '', 'C', '0', '1', 'news:author:view', 'fa fa-user-secret', 1, SYSDATE(), '', NULL, '作者管理');
INSERT INTO sys_menu VALUES(2901, '作者查询', 2900, '1', '', '', 'F', '0', '1', 'news:author:list', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2902, '作者新增', 2900, '2', '', '', 'F', '0', '1', 'news:author:add', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2903, '作者修改', 2900, '3', '', '', 'F', '0', '1', 'news:author:edit', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2904, '作者删除', 2900, '4', '', '', 'F', '0', '1', 'news:author:remove', '#', 1, SYSDATE(), '', NULL, '');

-- 积分记录 (3000)
INSERT INTO sys_menu VALUES(3000, '积分记录', 2000, '6', '/system/news/points', '', 'C', '0', '1', 'news:points:view', 'fa fa-diamond', 1, SYSDATE(), '', NULL, '积分记录');
INSERT INTO sys_menu VALUES(3001, '积分查询', 3000, '1', '', '', 'F', '0', '1', 'news:points:list', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(3002, '积分删除', 3000, '5', '', '', 'F', '0', '1', 'news:points:remove', '#', 1, SYSDATE(), '', NULL, '');

-- 兑换记录 (3100)
INSERT INTO sys_menu VALUES(3100, '兑换记录', 2000, '7', '/system/news/exchange', '', 'C', '0', '1', 'news:exchange:view', 'fa fa-exchange', 1, SYSDATE(), '', NULL, '兑换记录');
INSERT INTO sys_menu VALUES(3101, '兑换查询', 3100, '1', '', '', 'F', '0', '1', 'news:exchange:list', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(3102, '兑换删除', 3100, '5', '', '', 'F', '0', '1', 'news:exchange:remove', '#', 1, SYSDATE(), '', NULL, '');

-- 消息管理 (3200)
INSERT INTO sys_menu VALUES(3200, '消息管理', 2000, '8', '/system/news/message', '', 'C', '0', '1', 'news:message:view', 'fa fa-envelope-o', 1, SYSDATE(), '', NULL, '消息管理');
INSERT INTO sys_menu VALUES(3201, '消息查询', 3200, '1', '', '', 'F', '0', '1', 'news:message:list', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(3202, '消息新增', 3200, '2', '', '', 'F', '0', '1', 'news:message:add', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(3203, '消息修改', 3200, '3', '', '', 'F', '0', '1', 'news:message:edit', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(3204, '消息删除', 3200, '4', '', '', 'F', '0', '1', 'news:message:remove', '#', 1, SYSDATE(), '', NULL, '');

-- 离线新闻 (3300)
INSERT INTO sys_menu VALUES(3300, '离线新闻', 2000, '9', '/system/news/offline', '', 'C', '0', '1', 'news:offline:view', 'fa fa-download', 1, SYSDATE(), '', NULL, '离线新闻');
INSERT INTO sys_menu VALUES(3301, '离线查询', 3300, '1', '', '', 'F', '0', '1', 'news:offline:list', '#', 1, SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(3302, '离线删除', 3300, '5', '', '', 'F', '0', '1', 'news:offline:remove', '#', 1, SYSDATE(), '', NULL, '');
"""

result = subprocess.run(
    ['mysql', '-uroot', '-p123456', 'ry', '--default-character-set=utf8mb4'],
    input=sql.encode('utf-8'),
    capture_output=True
)

output = result.stdout.decode('utf-8', errors='replace')
error = result.stderr.decode('utf-8', errors='replace')

print("=== STDOUT ===")
print(output)
if error:
    print("=== STDERR ===")
    print(error)
print(f"\nReturn code: {result.returncode}")
if result.returncode == 0:
    print("菜单插入成功！共新增6个管理模块，24条菜单记录。")
else:
    print("菜单插入失败！")
