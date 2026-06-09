const { execSync } = require('child_process');

console.log('=== Checking data in key tables ===');
const checks = [
  'SELECT COUNT(*) as cnt FROM news_category',
  'SELECT COUNT(*) as cnt FROM news_article',
  'SELECT COUNT(*) as cnt FROM channel',
  'SELECT COUNT(*) as cnt FROM author',
  'SELECT COUNT(*) as cnt FROM activity',
  'SELECT COUNT(*) as cnt FROM topic',
  'SELECT COUNT(*) as cnt FROM report',
  'SELECT COUNT(*) as cnt FROM user_comment',
  'SELECT COUNT(*) as cnt FROM user_profile',
];

for (const sql of checks) {
  try {
    const r = execSync(`mysql -uroot -p123456 ry -e "${sql}"`, { encoding: 'utf8' });
    console.log(r.trim());
  } catch(e) {
    console.log('ERR:', sql);
  }
}
