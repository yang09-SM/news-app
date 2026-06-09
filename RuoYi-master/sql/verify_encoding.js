const { execSync } = require('child_process');

console.log('=== Activity HEX check ===');
try {
  const r = execSync(`mysql -uroot -p123456 ry --default-character-set=utf8mb4 -e "SELECT activity_id, HEX(activity_name) as hex_name, LENGTH(activity_name) as char_len, CHAR_LENGTH(activity_name) as utf8_len FROM activity;"`, {encoding: 'utf8'});
  console.log(r);
} catch(e) { console.log(e.stdout || e.stderr); }

console.log('=== Channel HEX check ===');
try {
  const r = execSync(`mysql -uroot -p123456 ry --default-character-set=utf8mb4 -e "SELECT channel_id, HEX(channel_name), CHAR_LENGTH(channel_name) as utf8_len FROM channel;"`, {encoding: 'utf8'});
  console.log(r);
} catch(e) { console.log(e.stdout || e.stderr); }
