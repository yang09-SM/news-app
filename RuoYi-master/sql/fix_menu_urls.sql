SET NAMES utf8mb4;

UPDATE sys_menu SET url = '/system/news/user' WHERE menu_id = 2003;
UPDATE sys_menu SET url = '/system/news/comment' WHERE menu_id = 2004;
UPDATE sys_menu SET url = '/system/news/channel' WHERE menu_id = 2005;
UPDATE sys_menu SET url = '/system/news/activity' WHERE menu_id = 2006;
UPDATE sys_menu SET url = '/system/news/report' WHERE menu_id = 2007;

SELECT menu_id, menu_name, url FROM sys_menu WHERE menu_id IN (2003, 2004, 2005, 2006, 2007);
