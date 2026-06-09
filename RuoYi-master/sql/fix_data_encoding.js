const { execSync } = require('child_process');

// Use Unicode escapes to guarantee correct encoding regardless of terminal/file encoding
// 每日签到活动 = \u6bcf\u65e5\u7b7e\u5230\u6d3b\u52a8
// 新人任务活动 = \u65b0\u4eba\u4efb\u52a1\u6d3b\u52a8
// 头条新闻 = \u5934\u6761\u65b0\u95fb
// 科技频道 = \u79d1\u6280\u9891\u9053
// 财经频道 = \u8d22\u7ecf\u9891\u9053
// 体育频道 = \u4f53\u80b2\u9891\u9053
// 娱乐频道 = \u5a31\u4e50\u9891\u9053
// 科技观察者 = \u79d1\u6280\u89c2\u5bdf\u8005
// etc.

const sql = `
SET NAMES utf8mb4;

DELETE FROM activity;
INSERT INTO activity VALUES(1, '\u6bcf\u65e5\u7b7e\u5230\u6d3b\u52a8', 'daily-checkin', '', '\u6bcf\u65e5\u7b7e\u5230\u83b7\u53d6\u79ef\u5206', '2026-06-09 00:00:00', '2026-12-31 23:59:59', 'checkin', 100, 0.00, 50, 1, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO activity VALUES(2, '\u65b0\u4eba\u4efb\u52a1\u6d3b\u52a8', 'newbie-task', '', '\u5b8c\u6210\u65b0\u624b\u4efb\u52a1\u83b7\u5f97\u5956\u52b1', '2026-06-15 00:00:00', '2026-07-15 23:59:59', 'task', 200, 5.00, 30, 0, '0', '0', 'admin', NOW(), '', NULL, '');

DELETE FROM channel;
INSERT INTO channel VALUES(1, '\u5934\u6761\u65b0\u95fb', 'headline', '', '\u6700\u65b0\u8d44\u8baf\u7b2c\u4e00\u65f6\u95f4\u9001\u8fbe', 1280, 1, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO channel VALUES(2, '\u79d1\u6280\u9891\u9053', 'tech', '', '\u63a2\u7d22\u79d1\u6280\u524d\u6cbf', 856, 2, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO channel VALUES(3, '\u8d22\u7ecf\u9891\u9053', 'finance', '', '\u6df1\u5ea6\u8d22\u7ecf\u5206\u6790', 623, 3, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO channel VALUES(4, '\u4f53\u80b2\u9891\u9053', 'sports', '', '\u7cbe\u5f69\u4f53\u80b2\u8d5b\u4e8b', 945, 4, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO channel VALUES(5, '\u5a31\u4e50\u9891\u9053', 'entertainment', '', '\u660e\u661f\u5a31\u4e50\u8d44\u8baf', 1567, 5, '0', '0', 'admin', NOW(), '', NULL, '');

DELETE FROM author;
INSERT INTO author VALUES(1, '\u79d1\u6280\u89c2\u5bdf\u8005', '/profile/avatar1.jpg', '\u8d44\u6df1\u79d1\u6280\u5a92\u4eba\uff0c\u4e13\u6ce8AI\u548c\u82af\u7247\u9886\u57df', 12580, 156, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO author VALUES(2, '\u8d22\u7ecf\u5206\u6790\u5e08\u8001\u73c0', '/profile/avatar2.jpg', '10\u5e74\u8bc1\u5238\u4ece\u4e1a\u7ecf\u9a8c\uff0c\u64c5\u957f\u5b8f\u89c2\u5206\u6790', 8920, 230, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO author VALUES(3, '\u4f53\u80b2\u8bc4\u8bba\u5458\u5c0f\u674e', '/profile/avatar3.jpg', '\u524d\u804c\u4e1a\u8fd0\u52a8\u5458\uff0c\u73b0\u4ece\u4e8b\u4f53\u80b2\u89e3\u8bf4', 15670, 98, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO author VALUES(4, '\u5a31\u4e50\u516b\u5366\u541b', '/profile/avatar4.jpg', '\u5a31\u4e50\u5708\u7b2c\u4e00\u7ebf\uff0c\u5e26\u4f60\u4e86\u89e3\u660e\u661f\u52a8\u6001', 28940, 320, '0', '0', 'admin', NOW(), '', NULL, '');

DELETE FROM topic;
INSERT INTO topic VALUES(1, '\u4eba\u5de5\u667a\u80fd', 'ai', '/topic/ai.jpg', '\u4eba\u5de5\u667a\u80fd\u6700\u65b0\u8fdb\u5c55\u4e0e\u8ba8\u8bba', 2560, 180, 1, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO topic VALUES(2, '\u6c14\u5019\u53d8\u5316', 'climate', '/topic/climate.jpg', '\u5168\u7403\u6c14\u5019\u95ee\u9898\u8ba8\u8bba', 1280, 95, 2, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO topic VALUES(3, '\u4e16\u754c\u676f', 'worldcup', '/topic/worldcup.jpg', '\u4e16\u754c\u676f\u76f8\u5173\u8bdd\u9898', 3400, 520, 3, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO topic VALUES(4, '\u80a1\u5e02\u884c\u60c5', 'stock', '/topic/stock.jpg', '\u80a1\u5e02\u5206\u6790\u4e0e\u6295\u8d44\u7b56\u7565', 1890, 210, 4, '0', '0', 'admin', NOW(), '', NULL, '');

SELECT 'Done!' as result;
`;

try {
  const r = execSync('mysql -uroot -p123456 ry --default-character-set=utf8mb4', { input: sql, encoding: 'utf8' });
  console.log(r);
} catch(e) {
  console.log('Error:', e.stderr || e.message);
}
