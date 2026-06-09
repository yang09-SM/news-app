const { execSync } = require('child_process');

console.log('=== Activity columns ===');
try {
  const r = execSync(`mysql -uroot -p123456 ry --default-character-set=utf8mb4 -e "DESCRIBE activity;"`, {encoding: 'utf8'});
  console.log(r);
} catch(e) { console.log(e.stdout || e.stderr); }

console.log('\n=== Channel columns ===');
try {
  const r = execSync(`mysql -uroot -p123456 ry --default-character-set=utf8mb4 -e "DESCRIBE channel;"`, {encoding: 'utf8'});
  console.log(r);
} catch(e) { console.log(e.stdout || e.stderr); }

console.log('\n=== Author columns ===');
try {
  const r = execSync(`mysql -uroot -p123456 ry --default-character-set=utf8mb4 -e "DESCRIBE author;"`, {encoding: 'utf8'});
  console.log(r);
} catch(e) { console.log(e.stdout || e.stderr); }

console.log('\n=== Topic columns ===');
try {
  const r = execSync(`mysql -uroot -p123456 ry --default-character-set=utf8mb4 -e "DESCRIBE topic;"`, {encoding: 'utf8'});
  console.log(r);
} catch(e) { console.log(e.stdout || e.stderr); }
