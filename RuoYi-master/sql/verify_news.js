const { execSync } = require('child_process');

console.log('=== news_category ===');
try {
  const r = execSync(`mysql -uroot -p123456 ry --default-character-set=utf8mb4 -e "SELECT category_id, category_name, HEX(category_name), CHAR_LENGTH(category_name) FROM news_category;"`, {encoding: 'utf8'});
  console.log(r);
} catch(e) { console.log(e.stdout || e.stderr); }

console.log('=== news_article ===');
try {
  const r = execSync(`mysql -uroot -p123456 ry --default-character-set=utf8mb4 -e "SELECT article_id, title, HEX(title), CHAR_LENGTH(title) FROM news_article;"`, {encoding: 'utf8'});
  console.log(r);
} catch(e) { console.log(e.stdout || e.stderr); }
