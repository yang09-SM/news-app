@echo off
chcp 65001 >nul
mysql -u root -p123456 -h localhost -P 3306 ry --default-character-set=utf8mb4 < "f:\Git Hub Project\news app\RuoYi-master\sql\fix_menus.sql"
