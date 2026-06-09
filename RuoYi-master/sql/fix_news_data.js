const { execSync } = require('child_process');

// Fix news_category and news_article with Unicode escapes
const sql = `
SET NAMES utf8mb4;

DELETE FROM news_category;
INSERT INTO news_category VALUES(1, '\u79d1\u6280\u65b0\u95fb', 'technology', 1, '0', '0', 'admin', SYSDATE(), '', NULL, '\u79d1\u6280\u7c7b\u65b0\u95fb');
INSERT INTO news_category VALUES(2, '\u65f6\u4e8b\u65b0\u95fb', 'news', 2, '0', '0', 'admin', SYSDATE(), '', NULL, '\u65f6\u4e8b\u7c7b\u65b0\u95fb');
INSERT INTO news_category VALUES(3, '\u5a31\u4e50\u65b0\u95fb', 'entertainment', 3, '0', '0', 'admin', SYSDATE(), '', NULL, '\u5a31\u4e50\u7c7b\u65b0\u95fb');
INSERT INTO news_category VALUES(4, '\u4f53\u80b2\u65b0\u95fb', 'sports', 4, '0', '0', 'admin', SYSDATE(), '', NULL, '\u4f53\u80b2\u7c7b\u65b0\u95fb');
INSERT INTO news_category VALUES(5, '\u8d22\u7ecf\u65b0\u95fb', 'finance', 5, '0', '0', 'admin', SYSDATE(), '', NULL, '\u8d22\u7ecf\u7c7b\u65b0\u95fb');

DELETE FROM news_article;
INSERT INTO news_article VALUES(1, '\u4eba\u5de5\u667a\u80fd\u6280\u672f\u65b0\u7a81\u7834', 1, '', '\u4eba\u5de5\u667a\u80fd\u5728\u81ea\u7136\u8bed\u8a00\u5904\u7406\u65b9\u9762\u53d6\u5f97\u91cd\u5927\u8fdb\u5c55', '<p>\u8fd1\u65e5\uff0c\u4eba\u5de5\u667a\u80fd\u6280\u672f\u5728\u81ea\u7136\u8bed\u8a00\u5904\u7406\u9886\u57df\u53d6\u5f97\u91cd\u5927\u7a81\u7834...</p>', 'text', '', '', '', '', NULL, '1', 1000, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(2, '\u5168\u7403\u6c14\u5019\u53d8\u5316\u5cf0\u4f1a\u53ec\u5f00', 2, '', '\u5404\u56fd\u9886\u5bfc\u4eba\u805a\u96c6\u4e00\u5802\uff0c\u5171\u5546\u6c14\u5019\u95ee\u9898', '<p>\u5168\u7403\u6c14\u5019\u53d8\u5316\u5cf0\u4f1a\u4eca\u65e5\u53ec\u5f00\uff0c\u5404\u56fd\u9886\u5bfc\u4eba\u5171\u540c\u63a2\u8ba8\u5e94\u5bf9\u6c14\u5019\u53d8\u5316\u7684\u7b56\u7565...</p>', 'text', '', '', '', '', NULL, '1', 856, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(3, '\u65b0\u4e00\u5b63\u70ed\u95e8\u7efc\u827a\u8282\u76ee\u4e0a\u7ebf', 3, '', '\u5168\u65b0\u7efc\u827a\u8282\u76ee\u5373\u5c06\u5f00\u64ad\uff0c\u660e\u661f\u9635\u5bb9\u5f3a\u5927', '<p>\u65b0\u4e00\u5b63\u70ed\u95e8\u7efc\u827a\u8282\u76ee\u5373\u5c06\u4e0a\u7ebf\uff0c\u96c6\u5406\u4e86\u4f17\u591a\u660e\u661f\u5bb8\u5bbe...</p>', 'text', '', '', '', '', NULL, '0', 0, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(4, '\u4e16\u754c\u676f\u9884\u9009\u8d5b\u7cbe\u5f69\u56de\u987e', 4, '', '\u5404\u56fd\u7403\u961f\u4e3a\u664b\u7ea7\u51b3\u8d5b\u5708\u594b\u529b\u62fc\u640f', '<p>\u4e16\u754c\u676f\u9884\u9009\u8d5b\u6fc0\u6218\u6b63\u9163\uff0c\u591a\u573a\u6bd4\u8d5b\u7cbe\u5f69\u7eb7\u544a...</p>', 'text', '', '', '', '', NULL, '1', 2340, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(5, '\u80a1\u5e02\u884c\u60c5\u5206\u6790\u62a5\u544a', 5, '', '\u4e13\u4e1a\u5206\u6790\u5e08\u89e3\u8bfb\u6700\u65b0\u80a1\u5e02\u52a8\u6001', '<p>\u672c\u5468\u80a1\u5e02\u6ce2\u52a8\u8f83\u5927\uff0c\u5206\u6790\u5e08\u4e3a\u60a8\u8be6\u7ec6\u89e3\u8bfb...</p>', 'text', '', '', '', '', NULL, '1', 567, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');

SELECT 'Done!' as result;
`;

try {
  const r = execSync('mysql -uroot -p123456 ry --default-character-set=utf8mb4', { input: sql, encoding: 'utf8' });
  console.log(r);
} catch(e) {
  console.log('Error:', e.stderr || e.message);
}
