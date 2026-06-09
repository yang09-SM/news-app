const { execSync } = require('child_process');
const fs = require('fs');

// Read the full SQL file
const fullSql = fs.readFileSync('f:/Git Hub Project/news app/RuoYi-master/sql/news_tables.sql', 'utf8');

// Split: DDL (CREATE TABLE) part and INSERT data part
const ddlEnd = fullSql.indexOf('-- 初始化新闻分类数据');
const ddlSql = fullSql.substring(0, ddlEnd).trim();

console.log('=== Step 1: Creating tables ===');
try {
  const r1 = execSync('mysql -uroot -p123456 ry', { input: ddlSql + '\n', encoding: 'utf8' });
  console.log(r1);
} catch(e) {
  console.log('DDL result:', e.stdout || e.stderr);
}

// Check if tables exist now
console.log('\n=== Step 2: Checking tables ===');
try {
  const r2 = execSync('mysql -uroot -p123456 ry -e "SHOW TABLES;"', { encoding: 'utf8' });
  console.log(r2);
} catch(e) {
  console.log(e.stdout || e.stderr);
}

// Now try INSERT data with relaxed mode
console.log('\n=== Step 3: Inserting initial data ===');
const insertSql = fullSql.substring(ddlEnd).trim();
try {
  const r3 = execSync('mysql -uroot -p123456 ry --init-command="SET SESSION sql_mode=\'\'"', 
    { input: insertSql + '\n', encoding: 'utf8' });
  console.log(r3);
} catch(e) {
  console.log('Insert error:', e.stderr || e.message);
}
