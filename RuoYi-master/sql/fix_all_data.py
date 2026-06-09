# -*- coding: utf-8 -*-
import subprocess
import sys

sql = """SET NAMES utf8mb4;

DELETE FROM news_category;
INSERT INTO news_category VALUES(1, '科技新闻', 'technology', 1, '0', '0', 'admin', SYSDATE(), '', NULL, '科技类新闻');
INSERT INTO news_category VALUES(2, '时事新闻', 'news', 2, '0', '0', 'admin', SYSDATE(), '', NULL, '时事类新闻');
INSERT INTO news_category VALUES(3, '娱乐新闻', 'entertainment', 3, '0', '0', 'admin', SYSDATE(), '', NULL, '娱乐类新闻');
INSERT INTO news_category VALUES(4, '体育新闻', 'sports', 4, '0', '0', 'admin', SYSDATE(), '', NULL, '体育类新闻');
INSERT INTO news_category VALUES(5, '财经新闻', 'finance', 5, '0', '0', 'admin', SYSDATE(), '', NULL, '财经类新闻');

DELETE FROM news_article;
INSERT INTO news_article VALUES(1, '人工智能技术新突破', 1, '', '人工智能在自然语言处理方面取得重大进展', '<p>近日，人工智能技术在自然语言处理领域取得重大突破...</p>', 'text', '', '', '', '', NULL, '1', 1000, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(2, '全球气候变化峰会召开', 2, '', '各国领导人齐聚一堂，共商气候问题', '<p>全球气候变化峰会今日召开，各国领导人共同探讨应对气候变化的策略...</p>', 'text', '', '', '', '', NULL, '1', 856, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(3, '新一季热门综艺节目上线', 3, '', '全新综艺节目即将开播，明星阵容强大', '<p>新一季热门综艺节目即将上线，汇集了众多明星嘉宾...</p>', 'text', '', '', '', '', NULL, '0', 0, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(4, '世界杯预选赛精彩回顾', 4, '', '各国球队为晋级决赛圈奋力拼搏', '<p>世界杯预选赛激战正酣，多场比赛精彩纷呈...</p>', 'text', '', '', '', '', NULL, '1', 2340, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(5, '股市行情分析报告', 5, '', '专业分析师解读最新股市动态', '<p>本周股市波动较大，分析师为您详细解读...</p>', 'text', '', '', '', '', NULL, '1', 567, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');

DELETE FROM activity;
INSERT INTO activity VALUES(1, '每日签到活动', 'daily-checkin', '', '每日签到获取积分', '2026-06-09 00:00:00', '2026-12-31 23:59:59', 'checkin', 100, 0.00, 50, 1, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO activity VALUES(2, '新人任务活动', 'newbie-task', '', '完成新手任务获得奖励', '2026-06-15 00:00:00', '2026-07-15 23:59:59', 'task', 200, 5.00, 30, 0, '0', '0', 'admin', NOW(), '', NULL, '');

DELETE FROM channel;
INSERT INTO channel VALUES(1, '头条新闻', 'headline', '', '最新资讯第一时间送达', 1280, 1, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO channel VALUES(2, '科技频道', 'tech', '', '探索科技前沿', 856, 2, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO channel VALUES(3, '财经频道', 'finance', '', '深度财经分析', 623, 3, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO channel VALUES(4, '体育频道', 'sports', '', '精彩体育赛事', 945, 4, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO channel VALUES(5, '娱乐频道', 'entertainment', '', '明星娱乐资讯', 1567, 5, '0', '0', 'admin', NOW(), '', NULL, '');

DELETE FROM author;
INSERT INTO author VALUES(1, '科技观察者', '/profile/avatar1.jpg', '资深科技媒体人，专注AI和芯片领域', 12580, 156, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO author VALUES(2, '财经分析师老王', '/profile/avatar2.jpg', '10年证券从业经验，擅长宏观分析', 8920, 230, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO author VALUES(3, '体育评论员小李', '/profile/avatar3.jpg', '前职业运动员，现从事体育解说', 15670, 98, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO author VALUES(4, '娱乐八卦君', '/profile/avatar4.jpg', '娱乐圈第一线，带你了解明星动态', 28940, 320, '0', '0', 'admin', NOW(), '', NULL, '');

DELETE FROM topic;
INSERT INTO topic VALUES(1, '人工智能', 'ai', '/topic/ai.jpg', '人工智能最新进展与讨论', 2560, 180, 1, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO topic VALUES(2, '气候变化', 'climate', '/topic/climate.jpg', '全球气候问题讨论', 1280, 95, 2, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO topic VALUES(3, '世界杯', 'worldcup', '/topic/worldcup.jpg', '世界杯相关话题', 3400, 520, 3, '0', '0', 'admin', NOW(), '', NULL, '');
INSERT INTO topic VALUES(4, '股市行情', 'stock', '/topic/stock.jpg', '股市分析与投资策略', 1890, 210, 4, '0', '0', 'admin', NOW(), '', NULL, '');

SELECT 'Done!' as result;
"""

# Write SQL to temp file with UTF-8 BOM
with open('f:/Git Hub Project/news app/RuoYi-master/sql/_fix_data.sql', 'w', encoding='utf-8-sig') as f:
    f.write(sql)

# Execute using mysql with explicit utf8mb4
result = subprocess.run(
    ['mysql', '-uroot', '-p123456', 'ry', '--default-character-set=utf8mb4'],
    input=sql.encode('utf-8'),
    capture_output=True,
    text=False
)
print('stdout:', result.stdout.decode('utf-8', errors='replace'))
if result.stderr:
    print('stderr:', result.stderr.decode('utf-8', errors='replace'))
print('returncode:', result.returncode)
