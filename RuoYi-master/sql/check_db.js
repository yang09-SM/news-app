const { execSync } = require('child_process');
const r = execSync('mysql -u root -p123456 -h localhost -P 3306 ry --default-character-set=utf8mb4 -e "SELECT menu_id, HEX(menu_name) as hex_name, LENGTH(menu_name) as char_len FROM sys_menu WHERE parent_id=2000 AND menu_id>=2003 ORDER BY order_num"', { encoding: 'utf8' });
console.log(r);
