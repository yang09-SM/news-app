-- ----------------------------
-- 新闻管理模块 - 数据库脚本
-- ----------------------------

-- ----------------------------
-- 1、新闻分类表
-- ----------------------------
DROP TABLE IF EXISTS news_category;
CREATE TABLE news_category (
  category_id     BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  category_name   VARCHAR(50)     NOT NULL COMMENT '分类名称',
  category_code   VARCHAR(50)     NOT NULL COMMENT '分类编码',
  order_num       INT(4)          DEFAULT 0 COMMENT '排序',
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0正常 1停用）',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (category_id),
  UNIQUE KEY uk_category_code (category_code)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '新闻分类表';

-- ----------------------------
-- 2、新闻内容表
-- ----------------------------
DROP TABLE IF EXISTS news_article;
CREATE TABLE news_article (
  article_id      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '新闻ID',
  title           VARCHAR(200)    NOT NULL COMMENT '新闻标题',
  category_id     BIGINT(20)      NOT NULL COMMENT '分类ID',
  cover_image     VARCHAR(500)    DEFAULT '' COMMENT '封面图片',
  summary         VARCHAR(500)    DEFAULT '' COMMENT '新闻摘要',
  content         TEXT COMMENT '新闻内容',
  news_type       VARCHAR(20)     DEFAULT 'text' COMMENT '新闻类型（text文本 video视频 audio音频）',
  video_url       VARCHAR(500)    DEFAULT '' COMMENT '视频地址',
  audio_url       VARCHAR(500)    DEFAULT '' COMMENT '音频地址',
  duration        VARCHAR(50)     DEFAULT '' COMMENT '时长',
  source          VARCHAR(100)    DEFAULT '' COMMENT '新闻来源',
  author_id       BIGINT(20)      DEFAULT NULL COMMENT '作者ID',
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0草稿 1已发布 2已下线）',
  view_count      BIGINT(20)      DEFAULT 0 COMMENT '浏览次数',
  like_count      BIGINT(20)      DEFAULT 0 COMMENT '点赞数',
  comment_count   BIGINT(20)      DEFAULT 0 COMMENT '评论数',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (article_id),
  KEY idx_category_id (category_id),
  KEY idx_status (status),
  KEY idx_author_id (author_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '新闻内容表';

-- ----------------------------
-- 3、用户信息扩展表
-- ----------------------------
DROP TABLE IF EXISTS user_profile;
CREATE TABLE user_profile (
  profile_id      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '扩展ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  nickname        VARCHAR(50)     DEFAULT '' COMMENT '用户昵称',
  avatar          VARCHAR(500)    DEFAULT '' COMMENT '头像地址',
  bio             VARCHAR(500)    DEFAULT '' COMMENT '个人简介',
  points          INT(11)         DEFAULT 0 COMMENT '积分',
  cash_balance    DECIMAL(10,2)   DEFAULT 0.00 COMMENT '现金余额',
  last_checkin    DATE            DEFAULT NULL COMMENT '最后签到日期',
  checkin_days    INT(11)         DEFAULT 0 COMMENT '连续签到天数',
  following_count INT(11)         DEFAULT 0 COMMENT '关注数',
  followers_count INT(11)         DEFAULT 0 COMMENT '粉丝数',
  friends_count   INT(11)         DEFAULT 0 COMMENT '好友数',
  likes_count     INT(11)         DEFAULT 0 COMMENT '获赞数',
  level           INT(11)         DEFAULT 1 COMMENT '用户等级',
  vip_level       INT(11)         DEFAULT 0 COMMENT 'VIP等级',
  vip_expire_time DATETIME        DEFAULT NULL COMMENT 'VIP过期时间',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (profile_id),
  UNIQUE KEY uk_user_id (user_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '用户信息扩展表';

-- ----------------------------
-- 4、用户浏览历史表
-- ----------------------------
DROP TABLE IF EXISTS user_browsing_history;
CREATE TABLE user_browsing_history (
  history_id      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '历史ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  article_id      BIGINT(20)      NOT NULL COMMENT '新闻ID',
  article_title   VARCHAR(200)    DEFAULT '' COMMENT '新闻标题',
  article_cover   VARCHAR(500)    DEFAULT '' COMMENT '新闻封面',
  browse_time     DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '浏览时间',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (history_id),
  KEY idx_user_id (user_id),
  KEY idx_article_id (article_id),
  KEY idx_browse_time (browse_time)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '用户浏览历史表';

-- ----------------------------
-- 5、用户收藏表
-- ----------------------------
DROP TABLE IF EXISTS user_favorite;
CREATE TABLE user_favorite (
  favorite_id     BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  article_id      BIGINT(20)      NOT NULL COMMENT '新闻ID',
  article_title   VARCHAR(200)    DEFAULT '' COMMENT '新闻标题',
  article_cover   VARCHAR(500)    DEFAULT '' COMMENT '新闻封面',
  favorite_time   DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (favorite_id),
  UNIQUE KEY uk_user_article (user_id, article_id),
  KEY idx_favorite_time (favorite_time)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '用户收藏表';

-- ----------------------------
-- 6、用户评论表
-- ----------------------------
DROP TABLE IF EXISTS user_comment;
CREATE TABLE user_comment (
  comment_id      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  article_id      BIGINT(20)      NOT NULL COMMENT '新闻ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  user_name       VARCHAR(50)     DEFAULT '' COMMENT '用户名',
  user_avatar     VARCHAR(500)    DEFAULT '' COMMENT '用户头像',
  content         TEXT            NOT NULL COMMENT '评论内容',
  parent_id       BIGINT(20)      DEFAULT NULL COMMENT '父评论ID（null表示一级评论）',
  reply_to_user_id BIGINT(20)     DEFAULT NULL COMMENT '回复的用户ID',
  reply_to_user_name VARCHAR(50)  DEFAULT '' COMMENT '回复的用户名',
  like_count      INT(11)         DEFAULT 0 COMMENT '点赞数',
  reply_count     INT(11)         DEFAULT 0 COMMENT '回复数',
  is_top          CHAR(1)         DEFAULT '0' COMMENT '是否置顶（0否 1是）',
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0正常 1隐藏）',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (comment_id),
  KEY idx_article_id (article_id),
  KEY idx_user_id (user_id),
  KEY idx_parent_id (parent_id),
  KEY idx_create_time (create_time)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '用户评论表';

-- ----------------------------
-- 7、用户关注关系表
-- ----------------------------
DROP TABLE IF EXISTS user_follow;
CREATE TABLE user_follow (
  follow_id       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '关注ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '关注者ID',
  follow_user_id  BIGINT(20)      NOT NULL COMMENT '被关注用户ID',
  follow_time     DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (follow_id),
  UNIQUE KEY uk_user_follow (user_id, follow_user_id),
  KEY idx_follow_user_id (follow_user_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '用户关注关系表';

-- ----------------------------
-- 8、用户兴趣标签表
-- ----------------------------
DROP TABLE IF EXISTS user_interest;
CREATE TABLE user_interest (
  interest_id     BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '兴趣ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  tag_name        VARCHAR(50)     NOT NULL COMMENT '标签名称',
  weight          INT(11)         DEFAULT 1 COMMENT '权重',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (interest_id),
  KEY idx_user_id (user_id),
  KEY idx_tag_name (tag_name)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '用户兴趣标签表';

-- ----------------------------
-- 9、用户消息表
-- ----------------------------
DROP TABLE IF EXISTS user_message;
CREATE TABLE user_message (
  message_id      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '接收用户ID',
  sender_id       BIGINT(20)      DEFAULT NULL COMMENT '发送者ID（系统消息为null）',
  message_type    VARCHAR(20)     NOT NULL COMMENT '消息类型（system系统 like点赞 comment评论 reply回复 follow关注）',
  title           VARCHAR(200)    DEFAULT '' COMMENT '消息标题',
  content         TEXT            DEFAULT NULL COMMENT '消息内容',
  related_id      BIGINT(20)      DEFAULT NULL COMMENT '关联ID（新闻ID、评论ID等）',
  related_type    VARCHAR(20)     DEFAULT '' COMMENT '关联类型',
  is_read         CHAR(1)         DEFAULT '0' COMMENT '是否已读（0否 1是）',
  read_time       DATETIME        DEFAULT NULL COMMENT '阅读时间',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (message_id),
  KEY idx_user_id (user_id),
  KEY idx_is_read (is_read),
  KEY idx_message_type (message_type),
  KEY idx_create_time (create_time)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '用户消息表';

-- ----------------------------
-- 10、活动表
-- ----------------------------
DROP TABLE IF EXISTS activity;
CREATE TABLE activity (
  activity_id     BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  activity_name   VARCHAR(200)    NOT NULL COMMENT '活动名称',
  activity_code   VARCHAR(50)     NOT NULL COMMENT '活动编码',
  cover_image     VARCHAR(500)    DEFAULT '' COMMENT '活动封面',
  description     TEXT            DEFAULT NULL COMMENT '活动描述',
  start_time      DATETIME        NOT NULL COMMENT '开始时间',
  end_time        DATETIME        NOT NULL COMMENT '结束时间',
  activity_type   VARCHAR(20)     DEFAULT 'normal' COMMENT '活动类型',
  points_reward   INT(11)         DEFAULT 0 COMMENT '积分奖励',
  cash_reward     DECIMAL(10,2)   DEFAULT 0.00 COMMENT '现金奖励',
  max_participants INT(11)        DEFAULT NULL COMMENT '最大参与人数',
  participant_count INT(11)       DEFAULT 0 COMMENT '当前参与人数',
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0未开始 1进行中 2已结束 3已下线）',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (activity_id),
  UNIQUE KEY uk_activity_code (activity_code),
  KEY idx_status (status),
  KEY idx_start_time (start_time),
  KEY idx_end_time (end_time)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '活动表';

-- ----------------------------
-- 11、用户成就表
-- ----------------------------
DROP TABLE IF EXISTS user_achievement;
CREATE TABLE user_achievement (
  achievement_id  BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '成就ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  achievement_name VARCHAR(100)   NOT NULL COMMENT '成就名称',
  achievement_code VARCHAR(50)    NOT NULL COMMENT '成就编码',
  description     VARCHAR(500)    DEFAULT '' COMMENT '成就描述',
  icon            VARCHAR(500)    DEFAULT '' COMMENT '成就图标',
  points_reward   INT(11)         DEFAULT 0 COMMENT '成就积分',
  is_unlocked     CHAR(1)         DEFAULT '0' COMMENT '是否解锁（0否 1是）',
  unlock_time     DATETIME        DEFAULT NULL COMMENT '解锁时间',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (achievement_id),
  KEY idx_user_id (user_id),
  KEY idx_is_unlocked (is_unlocked),
  KEY idx_achievement_code (achievement_code)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '用户成就表';

-- ----------------------------
-- 12、积分记录表
-- ----------------------------
DROP TABLE IF EXISTS points_record;
CREATE TABLE points_record (
  record_id       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  points_change   INT(11)         NOT NULL COMMENT '积分变化（正数增加，负数减少）',
  points_balance  INT(11)         NOT NULL COMMENT '变动后积分余额',
  record_type     VARCHAR(20)     NOT NULL COMMENT '记录类型（checkin签到 browse浏览 comment评论 share分享 exchange兑换 other其他）',
  description     VARCHAR(500)    DEFAULT '' COMMENT '描述',
  related_id      BIGINT(20)      DEFAULT NULL COMMENT '关联ID',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (record_id),
  KEY idx_user_id (user_id),
  KEY idx_record_type (record_type),
  KEY idx_create_time (create_time)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '积分记录表';

-- ----------------------------
-- 13、兑换记录表
-- ----------------------------
DROP TABLE IF EXISTS exchange_record;
CREATE TABLE exchange_record (
  exchange_id     BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '兑换ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  product_id      BIGINT(20)      NOT NULL COMMENT '商品ID',
  product_name    VARCHAR(200)    DEFAULT '' COMMENT '商品名称',
  product_image   VARCHAR(500)    DEFAULT '' COMMENT '商品图片',
  points_cost     INT(11)         NOT NULL COMMENT '消耗积分',
  exchange_time   DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '兑换时间',
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0待处理 1已处理 2已发货 3已完成 4已取消）',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (exchange_id),
  KEY idx_user_id (user_id),
  KEY idx_status (status),
  KEY idx_exchange_time (exchange_time)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '兑换记录表';

-- ----------------------------
-- 14、创作内容表
-- ----------------------------
DROP TABLE IF EXISTS user_creation;
CREATE TABLE user_creation (
  creation_id     BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '创作ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  title           VARCHAR(200)    NOT NULL COMMENT '标题',
  cover_image     VARCHAR(500)    DEFAULT '' COMMENT '封面图片',
  content         TEXT            NOT NULL COMMENT '内容',
  creation_type   VARCHAR(20)     DEFAULT 'article' COMMENT '创作类型（article文章 video视频）',
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0草稿 1待审核 2已发布 3已驳回 4已下线）',
  view_count      BIGINT(20)      DEFAULT 0 COMMENT '浏览数',
  like_count      BIGINT(20)      DEFAULT 0 COMMENT '点赞数',
  comment_count   BIGINT(20)      DEFAULT 0 COMMENT '评论数',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (creation_id),
  KEY idx_user_id (user_id),
  KEY idx_status (status),
  KEY idx_create_time (create_time)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '创作内容表';

-- ----------------------------
-- 15、举报表
-- ----------------------------
DROP TABLE IF EXISTS report;
CREATE TABLE report (
  report_id       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '举报ID',
  reporter_id     BIGINT(20)      NOT NULL COMMENT '举报人ID',
  report_type     VARCHAR(20)     NOT NULL COMMENT '举报类型（article新闻 comment评论 creation创作 user用户）',
  target_id       BIGINT(20)      NOT NULL COMMENT '被举报对象ID',
  reason          VARCHAR(500)    NOT NULL COMMENT '举报原因',
  description     TEXT            DEFAULT NULL COMMENT '详细描述',
  images          TEXT            DEFAULT NULL COMMENT '举报图片（JSON数组）',
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0待处理 1处理中 2已处理 3已驳回）',
  handle_result   VARCHAR(500)    DEFAULT '' COMMENT '处理结果',
  handle_user_id  BIGINT(20)      DEFAULT NULL COMMENT '处理人ID',
  handle_time     DATETIME        DEFAULT NULL COMMENT '处理时间',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (report_id),
  KEY idx_reporter_id (reporter_id),
  KEY idx_report_type (report_type),
  KEY idx_target_id (target_id),
  KEY idx_status (status)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '举报表';

-- ----------------------------
-- 16、离线新闻表
-- ----------------------------
DROP TABLE IF EXISTS offline_news;
CREATE TABLE offline_news (
  offline_id      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '离线ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  article_id      BIGINT(20)      NOT NULL COMMENT '新闻ID',
  article_title   VARCHAR(200)    DEFAULT '' COMMENT '新闻标题',
  article_cover   VARCHAR(500)    DEFAULT '' COMMENT '新闻封面',
  article_content TEXT            DEFAULT NULL COMMENT '新闻内容（离线缓存）',
  download_time   DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '下载时间',
  file_size       BIGINT(20)      DEFAULT 0 COMMENT '文件大小（字节）',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (offline_id),
  UNIQUE KEY uk_user_article (user_id, article_id),
  KEY idx_download_time (download_time)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '离线新闻表';

-- ----------------------------
-- 17、频道表
-- ----------------------------
DROP TABLE IF EXISTS channel;
CREATE TABLE channel (
  channel_id      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '频道ID',
  channel_name    VARCHAR(50)     NOT NULL COMMENT '频道名称',
  channel_code    VARCHAR(50)     NOT NULL COMMENT '频道编码',
  icon            VARCHAR(500)    DEFAULT '' COMMENT '频道图标',
  description     VARCHAR(500)    DEFAULT '' COMMENT '频道描述',
  news_count      BIGINT(20)      DEFAULT 0 COMMENT '新闻数量',
  order_num       INT(4)          DEFAULT 0 COMMENT '排序',
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0正常 1停用）',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (channel_id),
  UNIQUE KEY uk_channel_code (channel_code)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '频道表';

-- ----------------------------
-- 18、频道订阅表
-- ----------------------------
DROP TABLE IF EXISTS channel_subscription;
CREATE TABLE channel_subscription (
  subscription_id BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '订阅ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  channel_id      BIGINT(20)      NOT NULL COMMENT '频道ID',
  subscribe_time  DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '订阅时间',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (subscription_id),
  UNIQUE KEY uk_user_channel (user_id, channel_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '频道订阅表';

-- ----------------------------
-- 19、作者表
-- ----------------------------
DROP TABLE IF EXISTS author;
CREATE TABLE author (
  author_id       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '作者ID',
  author_name     VARCHAR(50)     NOT NULL COMMENT '作者名称',
  avatar          VARCHAR(500)    DEFAULT '' COMMENT '作者头像',
  bio             VARCHAR(500)    DEFAULT '' COMMENT '作者简介',
  follower_count  BIGINT(20)      DEFAULT 0 COMMENT '粉丝数',
  article_count   BIGINT(20)      DEFAULT 0 COMMENT '文章数',
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0正常 1停用）',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (author_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '作者表';

-- ----------------------------
-- 20、作者关注表
-- ----------------------------
DROP TABLE IF EXISTS author_follow;
CREATE TABLE author_follow (
  follow_id       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '关注ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  author_id       BIGINT(20)      NOT NULL COMMENT '作者ID',
  follow_time     DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (follow_id),
  UNIQUE KEY uk_user_author (user_id, author_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '作者关注表';

-- ----------------------------
-- 21、主题表
-- ----------------------------
DROP TABLE IF EXISTS topic;
CREATE TABLE topic (
  topic_id        BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '主题ID',
  topic_name      VARCHAR(100)    NOT NULL COMMENT '主题名称',
  topic_code      VARCHAR(50)     NOT NULL COMMENT '主题编码',
  cover_image     VARCHAR(500)    DEFAULT '' COMMENT '主题封面',
  description     VARCHAR(500)    DEFAULT '' COMMENT '主题描述',
  news_count      BIGINT(20)      DEFAULT 0 COMMENT '新闻数量',
  discussion_count BIGINT(20)     DEFAULT 0 COMMENT '讨论数量',
  order_num       INT(4)          DEFAULT 0 COMMENT '排序',
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0正常 1停用）',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (topic_id),
  UNIQUE KEY uk_topic_code (topic_code)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '主题表';

-- ----------------------------
-- 22、用户不感兴趣新闻表
-- ----------------------------
DROP TABLE IF EXISTS user_disliked_news;
CREATE TABLE user_disliked_news (
  dislike_id      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT 'ID',
  user_id         BIGINT(20)      NOT NULL COMMENT '用户ID',
  article_id      BIGINT(20)      NOT NULL COMMENT '新闻ID',
  dislike_time    DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '不感兴趣时间',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (dislike_id),
  UNIQUE KEY uk_user_article (user_id, article_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '用户不感兴趣新闻表';

-- ----------------------------
-- 初始化新闻分类数据
-- ----------------------------
INSERT INTO news_category VALUES(1, '科技新闻', 'technology', 1, '0', '0', 'admin', SYSDATE(), '', NULL, '科技类新闻');
INSERT INTO news_category VALUES(2, '时事新闻', 'news', 2, '0', '0', 'admin', SYSDATE(), '', NULL, '时事类新闻');
INSERT INTO news_category VALUES(3, '娱乐新闻', 'entertainment', 3, '0', '0', 'admin', SYSDATE(), '', NULL, '娱乐类新闻');
INSERT INTO news_category VALUES(4, '体育新闻', 'sports', 4, '0', '0', 'admin', SYSDATE(), '', NULL, '体育类新闻');
INSERT INTO news_category VALUES(5, '财经新闻', 'finance', 5, '0', '0', 'admin', SYSDATE(), '', NULL, '财经类新闻');

-- ----------------------------
-- 初始化新闻内容数据
-- ----------------------------
INSERT INTO news_article VALUES(1, '人工智能技术新突破', 1, '', '人工智能在自然语言处理方面取得重大进展', '<p>近日，人工智能技术在自然语言处理领域取得重大突破...</p>', 'text', '', '', '', '', NULL, '1', 1000, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(2, '全球气候变化峰会召开', 2, '', '各国领导人齐聚一堂，共商气候问题', '<p>全球气候变化峰会今日召开，各国领导人共同探讨应对气候变化的策略...</p>', 'text', '', '', '', '', NULL, '1', 856, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(3, '新一季热门综艺节目上线', 3, '', '全新综艺节目即将开播，明星阵容强大', '<p>新一季热门综艺节目即将上线，汇集了众多明星嘉宾...</p>', 'text', '', '', '', '', NULL, '0', 0, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(4, '世界杯预选赛精彩回顾', 4, '', '各国球队为晋级决赛圈奋力拼搏', '<p>世界杯预选赛激战正酣，多场比赛精彩纷呈...</p>', 'text', '', '', '', '', NULL, '1', 2340, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES(5, '股市行情分析报告', 5, '', '专业分析师解读最新股市动态', '<p>本周股市波动较大，分析师为您详细解读...</p>', 'text', '', '', '', '', NULL, '1', 567, 0, 0, '0', 'admin', SYSDATE(), '', NULL, '');

-- ----------------------------
-- 初始化频道数据
-- ----------------------------
INSERT INTO channel VALUES(1, '头条新闻', 'headline', '', '最新资讯第一时间送达', 1280, 1, '0', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO channel VALUES(2, '科技频道', 'tech', '', '探索科技前沿', 856, 2, '0', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO channel VALUES(3, '财经频道', 'finance', '', '深度财经分析', 623, 3, '0', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO channel VALUES(4, '体育频道', 'sports', '', '精彩体育赛事', 945, 4, '0', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO channel VALUES(5, '娱乐频道', 'entertainment', '', '明星娱乐资讯', 1567, 5, '0', '0', 'admin', SYSDATE(), '', NULL, '');

-- ----------------------------
-- 初始化作者数据
-- ----------------------------
INSERT INTO author VALUES(1, '张记者', '', '资深财经记者，专注行业分析', 1258, 456, '0', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO author VALUES(2, '李编辑', '', '科技领域专家', 892, 234, '0', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO author VALUES(3, '王评论员', '', '体育评论员', 2345, 678, '0', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO author VALUES(4, '赵作家', '', '文化专栏作家', 567, 123, '0', '0', 'admin', SYSDATE(), '', NULL, '');

-- ----------------------------
-- 初始化主题数据
-- ----------------------------
INSERT INTO topic VALUES(1, '科技前沿', 'tech-frontier', '', '探索最新科技动态', 128, 56, 1, '0', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO topic VALUES(2, '财经资讯', 'finance-news', '', '深度解读财经新闻', 96, 42, 2, '0', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO topic VALUES(3, '体育世界', 'sports-world', '', '精彩赛事直播', 256, 89, 3, '0', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO topic VALUES(4, '娱乐八卦', 'entertainment-gossip', '', '明星资讯速递', 312, 124, 4, '0', '0', 'admin', SYSDATE(), '', NULL, '');

-- ----------------------------
-- 初始化用户信息扩展数据（示例）
-- ----------------------------
INSERT INTO user_profile VALUES(1, 1, '若依', '', '热爱生活，热爱新闻', 1000, 50.00, CURDATE(), 7, 10, 5, 3, 20, 2, 1, NULL, '0', 'admin', SYSDATE(), '', NULL, '');

-- ----------------------------
-- 初始化活动数据
-- ----------------------------
INSERT INTO activity VALUES(1, '每日签到活动', 'daily-checkin', '', '每日签到领积分', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 30 DAY), 'daily', 10, 0.00, NULL, 0, '1', '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO activity VALUES(2, '新手任务', 'newbie-task', '', '完成新手任务获丰厚奖励', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'task', 100, 5.00, NULL, 0, '1', '0', 'admin', SYSDATE(), '', NULL, '');

-- ----------------------------
-- 初始化成就数据（示例）
-- ----------------------------
INSERT INTO user_achievement VALUES(1, 1, '初次登录', 'first-login', '第一次登录应用', '', 10, '1', SYSDATE(), '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO user_achievement VALUES(2, 1, '连续签到7天', 'checkin-7', '连续签到7天', '', 50, '0', NULL, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO user_achievement VALUES(3, 1, '阅读达人', 'read-100', '阅读100篇新闻', '', 100, '0', NULL, '0', 'admin', SYSDATE(), '', NULL, '');

-- ----------------------------
-- 新闻管理菜单初始化
-- ----------------------------
-- 一级菜单
INSERT INTO sys_menu VALUES(2000, '新闻管理', '0', '5', '#', '', 'M', '0', '1', '', 'fa fa-newspaper-o', 'admin', SYSDATE(), '', NULL, '新闻管理目录');

-- 二级菜单 - 新闻分类
INSERT INTO sys_menu VALUES(2001, '新闻分类', '2000', '1', '/news/category', '', 'C', '0', '1', 'news:category:view', 'fa fa-sitemap', 'admin', SYSDATE(), '', NULL, '新闻分类菜单');
-- 新闻分类按钮
INSERT INTO sys_menu VALUES(2100, '分类查询', '2001', '1', '#', '', 'F', '0', '1', 'news:category:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2101, '分类新增', '2001', '2', '#', '', 'F', '0', '1', 'news:category:add', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2102, '分类修改', '2001', '3', '#', '', 'F', '0', '1', 'news:category:edit', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2103, '分类删除', '2001', '4', '#', '', 'F', '0', '1', 'news:category:remove', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2104, '分类导出', '2001', '5', '#', '', 'F', '0', '1', 'news:category:export', '#', 'admin', SYSDATE(), '', NULL, '');

-- 二级菜单 - 新闻内容
INSERT INTO sys_menu VALUES(2002, '新闻内容', '2000', '2', '/news/article', '', 'C', '0', '1', 'news:article:view', 'fa fa-file-text-o', 'admin', SYSDATE(), '', NULL, '新闻内容菜单');
-- 新闻内容按钮
INSERT INTO sys_menu VALUES(2200, '新闻查询', '2002', '1', '#', '', 'F', '0', '1', 'news:article:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2201, '新闻新增', '2002', '2', '#', '', 'F', '0', '1', 'news:article:add', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2202, '新闻修改', '2002', '3', '#', '', 'F', '0', '1', 'news:article:edit', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2203, '新闻删除', '2002', '4', '#', '', 'F', '0', '1', 'news:article:remove', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2204, '新闻导出', '2002', '5', '#', '', 'F', '0', '1', 'news:article:export', '#', 'admin', SYSDATE(), '', NULL, '');

-- 二级菜单 - 用户管理
INSERT INTO sys_menu VALUES(2003, '用户管理', '2000', '3', '/news/user', '', 'C', '0', '1', 'news:user:view', 'fa fa-users', 'admin', SYSDATE(), '', NULL, '用户管理菜单');
-- 用户管理按钮
INSERT INTO sys_menu VALUES(2300, '用户查询', '2003', '1', '#', '', 'F', '0', '1', 'news:user:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2301, '用户查看', '2003', '2', '#', '', 'F', '0', '1', 'news:user:detail', '#', 'admin', SYSDATE(), '', NULL, '');

-- 二级菜单 - 评论管理
INSERT INTO sys_menu VALUES(2004, '评论管理', '2000', '4', '/news/comment', '', 'C', '0', '1', 'news:comment:view', 'fa fa-comments', 'admin', SYSDATE(), '', NULL, '评论管理菜单');
-- 评论管理按钮
INSERT INTO sys_menu VALUES(2400, '评论查询', '2004', '1', '#', '', 'F', '0', '1', 'news:comment:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2401, '评论审核', '2004', '2', '#', '', 'F', '0', '1', 'news:comment:audit', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2402, '评论删除', '2004', '3', '#', '', 'F', '0', '1', 'news:comment:remove', '#', 'admin', SYSDATE(), '', NULL, '');

-- 二级菜单 - 频道管理
INSERT INTO sys_menu VALUES(2005, '频道管理', '2000', '5', '/news/channel', '', 'C', '0', '1', 'news:channel:view', 'fa fa-tv', 'admin', SYSDATE(), '', NULL, '频道管理菜单');
-- 频道管理按钮
INSERT INTO sys_menu VALUES(2500, '频道查询', '2005', '1', '#', '', 'F', '0', '1', 'news:channel:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2501, '频道新增', '2005', '2', '#', '', 'F', '0', '1', 'news:channel:add', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2502, '频道修改', '2005', '3', '#', '', 'F', '0', '1', 'news:channel:edit', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2503, '频道删除', '2005', '4', '#', '', 'F', '0', '1', 'news:channel:remove', '#', 'admin', SYSDATE(), '', NULL, '');

-- 二级菜单 - 活动管理
INSERT INTO sys_menu VALUES(2006, '活动管理', '2000', '6', '/news/activity', '', 'C', '0', '1', 'news:activity:view', 'fa fa-calendar', 'admin', SYSDATE(), '', NULL, '活动管理菜单');
-- 活动管理按钮
INSERT INTO sys_menu VALUES(2600, '活动查询', '2006', '1', '#', '', 'F', '0', '1', 'news:activity:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2601, '活动新增', '2006', '2', '#', '', 'F', '0', '1', 'news:activity:add', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2602, '活动修改', '2006', '3', '#', '', 'F', '0', '1', 'news:activity:edit', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2603, '活动删除', '2006', '4', '#', '', 'F', '0', '1', 'news:activity:remove', '#', 'admin', SYSDATE(), '', NULL, '');

-- 二级菜单 - 举报管理
INSERT INTO sys_menu VALUES(2007, '举报管理', '2000', '7', '/news/report', '', 'C', '0', '1', 'news:report:view', 'fa fa-flag', 'admin', SYSDATE(), '', NULL, '举报管理菜单');
-- 举报管理按钮
INSERT INTO sys_menu VALUES(2700, '举报查询', '2007', '1', '#', '', 'F', '0', '1', 'news:report:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES(2701, '举报处理', '2007', '2', '#', '', 'F', '0', '1', 'news:report:handle', '#', 'admin', SYSDATE(), '', NULL, '');
