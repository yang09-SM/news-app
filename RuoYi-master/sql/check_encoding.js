const { execSync } = require('child_process');

console.log('=== Checking activity data encoding ===');
try {
  const r = execSync(`mysql -uroot -p123456 ry --default-character-set=utf8mb4 -e "SELECT activity_id, HEX(activity_name) as hex_name, activity_name FROM activity LIMIT 5;"`, {encoding: 'utf8'});
  console.log(r);
} catch(e) { console.log(e.stdout || e.stderr); }

console.log('\n=== Checking channel data encoding ===');
try {
  const r = execSync(`mysql -uroot -p123456 ry --default-character-set=utf8mb4 -e "SELECT channel_id, HEX(channel_name) as hex_name, channel_name FROM channel LIMIT 5;"`, {encoding: 'utf8'});
  console.log(r);
} catch(e) { console.log(e.stdout || e.stderr); }
