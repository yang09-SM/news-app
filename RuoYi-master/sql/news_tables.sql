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
  status          CHAR(1)         DEFAULT '0' COMMENT '状态（0草稿 1已发布 2已下线）',
  view_count      BIGINT(20)      DEFAULT 0 COMMENT '浏览次数',
  del_flag        CHAR(1)         DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time     DATETIME COMMENT '创建时间',
  update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time     DATETIME COMMENT '更新时间',
  remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (article_id),
  KEY idx_category_id (category_id),
  KEY idx_status (status)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT = '新闻内容表';

-- ----------------------------
-- 3、初始化新闻分类数据
-- ----------------------------
INSERT INTO news_category VALUES (1, '科技新闻', 'technology', 1, '0', '0', 'admin', SYSDATE(), '', NULL, '科技类新闻');
INSERT INTO news_category VALUES (2, '时事新闻', 'news', 2, '0', '0', 'admin', SYSDATE(), '', NULL, '时事类新闻');
INSERT INTO news_category VALUES (3, '娱乐新闻', 'entertainment', 3, '0', '0', 'admin', SYSDATE(), '', NULL, '娱乐类新闻');

-- ----------------------------
-- 4、初始化新闻内容数据
-- ----------------------------
INSERT INTO news_article VALUES (1, '人工智能技术新突破', 1, '', '人工智能在自然语言处理方面取得重大进展', '<p>近日，人工智能技术在自然语言处理领域取得重大突破...</p>', '1', 1000, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES (2, '全球气候变化峰会召开', 2, '', '各国领导人齐聚一堂，共商气候问题', '<p>全球气候变化峰会今日召开，各国领导人共同探讨应对气候变化的策略...</p>', '1', 856, '0', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO news_article VALUES (3, '新一季热门综艺节目上线', 3, '', '全新综艺节目即将开播，明星阵容强大', '<p>新一季热门综艺节目即将上线，汇集了众多明星嘉宾...</p>', '0', 0, '0', 'admin', SYSDATE(), '', NULL, '');

-- ----------------------------
-- 5、新闻管理菜单初始化
-- ----------------------------
-- 一级菜单
INSERT INTO sys_menu VALUES (2000, '新闻管理', '0', '5', '#', '', 'M', '0', '1', '', 'fa fa-newspaper-o', 'admin', SYSDATE(), '', NULL, '新闻管理目录');

-- 二级菜单 - 新闻分类
INSERT INTO sys_menu VALUES (2001, '新闻分类', '2000', '1', '/news/category', '', 'C', '0', '1', 'news:category:view', 'fa fa-sitemap', 'admin', SYSDATE(), '', NULL, '新闻分类菜单');
-- 新闻分类按钮
INSERT INTO sys_menu VALUES (2100, '分类查询', '2001', '1', '#', '', 'F', '0', '1', 'news:category:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES (2101, '分类新增', '2001', '2', '#', '', 'F', '0', '1', 'news:category:add', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES (2102, '分类修改', '2001', '3', '#', '', 'F', '0', '1', 'news:category:edit', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES (2103, '分类删除', '2001', '4', '#', '', 'F', '0', '1', 'news:category:remove', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES (2104, '分类导出', '2001', '5', '#', '', 'F', '0', '1', 'news:category:export', '#', 'admin', SYSDATE(), '', NULL, '');

-- 二级菜单 - 新闻内容
INSERT INTO sys_menu VALUES (2002, '新闻内容', '2000', '2', '/news/article', '', 'C', '0', '1', 'news:article:view', 'fa fa-file-text-o', 'admin', SYSDATE(), '', NULL, '新闻内容菜单');
-- 新闻内容按钮
INSERT INTO sys_menu VALUES (2200, '新闻查询', '2002', '1', '#', '', 'F', '0', '1', 'news:article:list', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES (2201, '新闻新增', '2002', '2', '#', '', 'F', '0', '1', 'news:article:add', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES (2202, '新闻修改', '2002', '3', '#', '', 'F', '0', '1', 'news:article:edit', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES (2203, '新闻删除', '2002', '4', '#', '', 'F', '0', '1', 'news:article:remove', '#', 'admin', SYSDATE(), '', NULL, '');
INSERT INTO sys_menu VALUES (2204, '新闻导出', '2002', '5', '#', '', 'F', '0', '1', 'news:article:export', '#', 'admin', SYSDATE(), '', NULL, '');
