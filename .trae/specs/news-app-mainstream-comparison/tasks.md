# Tasks - 新闻APP迭代升级任务清单

## Phase 1: 基础设施加固（P0优先级） ✅ COMPLETED

- [x] Task 1.1: **引入Redis缓存层** ✅
  - [x] 1.1.1 添加Spring Data Redis依赖到pom.xml（ruoyi-common模块）
  - [x] 1.1.2 创建RedisConfig配置类（连接池、序列化、Key前缀）
  - [x] 1.1.3 实现RedisCacheService工具类（set/get/del/expire通用方法）
  - [x] 1.1.4 为NewsArticleServiceImpl添加热门新闻列表缓存（TTL 5分钟）
  - [x] 1.1.5 为新闻详情接口添加缓存（TTL 10分钟，发布时主动删除缓存key）
  - [x] 1.1.6 为RecommendationServiceImpl添加推荐结果缓存（TTL 30分钟，按userId隔离）
  - [x] 1.1.7 为基础数据（分类/频道/作者）添加缓存（TTL 1小时）
  - [x] 1.1.8 实现缓存击穿保护（Redis分布式锁 + 互斥锁模式）

- [x] Task 1.2: **引入Elasticsearch全文搜索** ✅
  - [x] 1.2.1 添加spring-boot-starter-data-elasticsearch依赖
  - [x] 1.2.2 创建ElasticsearchConfig配置类（连接ES集群）
  - [x] 1.2.3 创建NewsArticleDocument文档类（映射ES索引结构）
  - [x] 1.2.4 创建NewsSearchService搜索服务（分词检索、高亮、聚合）
  - [x] 1.2.5 创建NewsIndexService索引同步服务（发布/编辑时同步ES）
  - [x] 1.2.6 改造NewsApiController的搜索接口（从MySQL LIKE切换到ES）
  - [x] 1.2.7 新增搜索联想API（GET /api/news/search/suggest?keyword=xxx）
  - [x] 1.2.8 新增搜索热词榜API（GET /api/news/search/hot-words）
  - [x] 1.2.9 后台管理页：搜索热词管理页面

- [x] Task 1.3: **接入阿里云OSS云存储** ✅
  - [x] 1.3.1 添加aliyun-oss-spring-boot-starter依赖
  - [x] 1.3.2 创建OssService服务类（上传/下载/删除/生成签名URL）
  - [x] 1.3.3 创建OssConfig配置类（Bucket/Endpoint/AccessKey）
  - [x] 1.3.4 改造文件上传Controller（支持OSS上传 + 本地fallback）
  - [x] 1.3.5 图片上传自动压缩和缩略图生成
  - [x] 1.3.6 配置CDN域名映射（返回可访问的URL）

- [x] Task 1.4: **集成Push消息推送** ✅
  - [x] 1.4.1 添加极光推送SDK依赖（或个推SDK）
  - [x] 1.4.2 创建JPushService推送服务类（别名/标签/全部推送）
  - [x] 1.4.3 创建PushConfig配置类（AppKey/MasterSecret）
  - [x] 1.4.4 创建PushMessage实体类（标题/内容/类型/附加数据）
  - [x] 1.4.5 在UserMessageService中集成推送（新消息时触发Push）
  - [x] 1.4.6 在NewsArticleService中集成推送（发布重要新闻时触发Push）
  - [x] 1.4.7 创建PushRecordController + HTML模板（推送记录管理后台）
  - [x] 1.4.8 数据库新增push_record表 + Mapper

- [x] Task 1.5: **短视频播放器实现** ✅
  - [x] 1.5.1 前端引入video.js播放器库（或DPlayer）
  - [x] 1.5.2 创建视频播放组件HTML模板（播放控制栏/清晰度切换/全屏）
  - [x] 1.5.3 改造新闻详情页（检测newsType=video时渲染播放器）
  - [x] 1.5.4 实现HLS/m3u8流媒体格式支持
  - [x] 1.5.5 实现播放速度调节(0.5x/1x/1.5x/2x)
  - [x] 1.5.6 列表页视频自动连续播放逻辑

## Phase 2: 核心能力增强（P1优先级）

- [ ] Task 2.1: **推荐算法升级**
  - [ ] 2.1.1 引入时间衰减因子（近期7天浏览权重×3，30天内×1，更早×0.3）
  - [ ] 2.1.2 增加多样性控制（同分类文章不超过40%，强制插入其他分类）
  - [ ] 2.1.3 增加新鲜度加权（24h内发布的文章额外+20%权重）
  - [ ] 2.1.4 推荐结果缓存优化（相同用户5分钟内返回缓存结果）
  - [ ] 2.1.5 冷启动引导流程（注册后弹出兴趣标签选择界面）
  - [ ] 2.1.6 推荐效果埋点（记录曝光/点击，用于后续算法调优）

- [ ] Task 2.2: **广告管理系统**
  - [ ] 2.2.1 创建Advertisement实体类 + ad_position/ad_material/ad_campaign表SQL
  - [ ] 2.2.2 创建AdvertisementMapper.xml（CRUD + 投放状态筛选）
  - [ ] 2.2.3 创建IAdvertisementService + Impl
  - [ ] 2.2.4 创建AdController（后台管理CRUD）
  - [ ] 2.2.5 广告位管理HTML模板（列表/新增/编辑）
  - [ ] 2.2.6 广告素材管理HTML模板
  - [ ] 2.2.7 广告投放计划管理HTML模板（时间/频次/定向）
  - [ ] 2.2.8 API端点：获取当前可用广告（GET /api/ad/{position}）
  - [ ] 2.2.9 曝光/点击上报API（POST /api/ad/track）
  - [ ] 2.2.10 菜单SQL：广告管理相关菜单（广告位/素材/投放计划/数据统计）

- [ ] Task 2.3: **内容审核自动化**
  - [ ] 2.3.1 创建SensitiveWordFilter工具类（DFA算法敏感词过滤）
  - [ ] 2.3.2 创建sensitive_word表 + SensitiveWordMapper（动态词库管理）
  - [ ] 2.3.3 敏感词管理后台（导入/导出/增删查）
  - [ ] 2.3.4 在评论发布接口集成敏感词过滤（拦截并提示）
  - [ ] 2.3.5 在新闻发布接口集成敏感词检测（标记为待审核）
  - [ ] 2.3.6 可选：创建AiContentAuditService（对接阿里云/腾讯云内容安全API）

- [ ] Task 2.4: **搜索体验升级**（基于Task 1.2 ES基础）
  - [ ] 2.4.1 搜索联想前端组件（jQuery Autocomplete + 下拉列表UI）
  - [ ] 2.4.2 搜索历史存储（localStorage + 服务端备份）
  - [ ] 2.4.3 搜索热词榜前端展示（Tag Cloud样式）
  - [ ] 2.4.4 多维筛选组件（分类下拉/时间范围/来源选择）
  - [ ] 2.4.5 搜索结果高亮CSS样式（关键字标红/加粗）

- [ ] Task 2.5: **评论系统增强**
  - [ ] 2.5.1 评论框集成Emoji表情选择器（EmojiOne/Unicode Emoji）
  - [ ] 2.5.2 图片评论功能（bootstrap-fileinput多图上传，最多9张）
  - [ ] 2.5.3 @提及用户功能（输入@触发用户搜索弹窗）
  - [ ] 2.5.4 点赞表情反应（👍👎😮😂等6种表情）
  - [ ] 2.5.5 user_comment表新增字段（images/reaction_type/is_long折叠）
  - [ ] 2.5.6 CommentReaction实体 + Mapper（表情反应记录）

- [ ] Task 2.6: **数据埋点系统**
  - [ ] 2.6.1 创建TrackingEvent实体类（event_id/user_id/event_type/event_data/timestamp）
  - [ ] 2.6.2 创建tracking_event表（分区表设计，按月分区）
  - [ ] 2.6.3 创建TrackingCollectorController（接收埋点数据POST /api/track/event）
  - [ ] 2.6.4 定义事件规范（page_view/article_click/video_play/share/favorite/like/comment）
  - [ ] 2.6.5 前端埋点SDK（轻量JS SDK，自动采集PV/点击/停留时长）
  - [ ] 2.6.6 基础统计查询API（DAU/MAU、文章Top10、留存率）

- [ ] Task 2.7: **风控反作弊**
  - [ ] 2.7.1 创建@RateLimit注解 + RateLimitInterceptor（基于Redis令牌桶）
  - [ ] 2.7.2 核心接口限流配置（登录5次/分钟、点赞50次/分钟、评论20次/分钟）
  - [ ] 2.7.3 IP黑名单机制（Redis Set存储，自动过期）
  - [ ] 2.7.4 设备指纹采集（基础版：User-Agent + IP + 屏幕分辨率hash）
  - [ ] 2.7.5 异常行为检测（短时间内大量点赞/关注/评论 → 标记可疑账号）

## Phase 3: 功能扩展（P2优先级）

- [ ] Task 3.1: **AI智能助手模块**
  - [ ] 3.1.1 创建AiAssistantController（GET /api/ai/summary, POST /api/ai/chat）
  - [ ] 3.1.2 对接AI大模型API（DeepSeek/通义千问/文心一言，通过HTTP调用）
  - [ ] 3.1.3 新闻摘要生成（传入文章内容 → 返回200字摘要）
  - [ ] 3.1.4 智能问答（用户提问 → AI结合知识库回答）
  - [ ] 3.1.5 后台AI配置页面（API Key管理、模型选择、调用限额）
  - [ ] 3.1.6 AI调用记录表 + 统计（token消耗/调用次数/成本）

- [ ] Task 3.2: **问答社区模块**
  - [ ] 3.2.1 创建Question实体 + question表（title/content/tags/view_count/answer_count/status）
  - [ ] 3.2.2 创建Answer实体 + answer表（question_id/user_id/content/like_count/is_accepted）
  - [ ] 3.2.3 QuestionMapper.xml + AnswerMapper.xml
  - [ ] 3.2.4 IQuestionService + IAnswerService + Impl
  - [ ] 3.2.5 QuestionApiController（提问/回答/采纳/搜索/热门）
  - [ ] 3.2.6 后台QuestionController（审核/置顶/删除管理）
  - [ ] 3.2.7 问答列表/详情/提问HTML模板
  - [ ] 3.2.8 菜单SQL：问答社区菜单

- [ ] Task 3.3: **直播模块基础版**
  - [ ] 3.3.1 创建LiveRoom实体 + live_room表（title/cover/stream_url/status/viewer_count/start_time）
  - [ ] 3.3.2 LiveRoomMapper.xml + ILiveRoomService + Impl
  - [ ] 3.3.3 LiveRoomApiController（直播列表/详情/进入房间）
  - [ ] 3.3.4 后台LiveRoomController（创建/编辑/下线直播）
  - [ ] 3.3.5 直播列表HTML模板（封面卡片式布局）
  - [ ] 3.3.6 集成视频播放器（支持m3u8/flv直播流）
  - [ ] 3.3.7 菜单SQL：直播管理菜单

- [ ] Task 3.4: **朋友圈动态(微头条)**
  - [ ] 3.4.1 创建Moment实体 + moment表（user_id/content/images/video_url/like_count/comment_count/location）
  - [ ] 3.4.2 MomentMapper.xml + IMomentService + Impl
  - [ ] 3.4.3 MomentApiController（发布动态/Feed流/点赞/评论/删除）
  - [ ] 3.4.4 Feed流算法（关注用户动态 + 热门动态混合）
  - [ ] 3.4.5 后台MomentController（内容审核/删除管理）
  - [ ] 3.4.6 动态发布/Feed流HTML模板
  - [ ] 3.4.7 菜单SQL：动态管理菜单

- [ ] Task 3.5: **数据分析仪表盘**
  - [ ] 3.5.1 创建DashboardController（GET /system/dashboard/data）
  - [ ] 3.5.2 核心指标统计接口（总用户数/今日新增/日活/文章数/总浏览量）
  - [ ] 3.5.3 趋势数据接口（近30天用户增长/浏览量趋势/发布量趋势）
  - [ ] 3.5.4 内容排行接口（TOP10热门文章/TOP10活跃作者/TOP10热门分类）
  - [ ] 3.5.5 用户画像分布（性别/年龄/地区/兴趣标签分布饼图数据）
  - [ ] 3.5.6 Dashboard HTML模板（ECharts图表：折线图/柱状图/饼图/仪表盘）
  - [ ] 3.5.7 菜单SQL：数据看板菜单

## Phase 4: 架构升级（中长期规划，标记为Future）

- [ ] Task 4.1: **前后端分离改造** (Future)
  - Vue3 + Vite + TypeScript前端项目初始化
  - Axios封装 + JWT Token拦截器
  - Vue Router路由配置
  - Pinia状态管理
  - Element Plus UI组件库集成
  - 页面逐个迁移（登录 → 首页 → 新闻列表 → 详情 → 个人中心）

- [ ] Task 4.2: **微服务拆分** (Future)
  - Spring Cloud Gateway网关
  - ruoyi-user-service（用户/认证/画像）
  - ruoyi-content-service（新闻/分类/评论/搜索）
  - ruoyi-recommend-service（推荐算法）
  - ruoyi-activity-service（活动/积分/兑换）
  - Nacos注册中心 + 配置中心

- [ ] Task 4.3: **消息队列引入** (Future)
  - RabbitMQ/RocketMQ集成
  - 异步事件：发布通知、搜索索引同步、统计数据更新
  - 延迟消息：定时任务替代方案

- [ ] Task 4.4: **数据库优化** (Future)
  - MySQL主从读写分离（Sharding-JDBC）
  - 核心大表分表策略设计
  - 慢查询监控与优化

- [ ] Task 4.5: **容器化部署** (Future)
  - Dockerfile编写（每个模块）
  - docker-compose.yml编排
  - Nginx反向代理配置
  - 基础镜像优化（JRE精简）

- [ ] Task 4.6: **监控体系** (Future)
  - Spring Boot Actuator健康检查
  - Prometheus metrics暴露
  - Grafana Dashboard配置
  - ELK日志收集（Filebeat → Logstash → Elasticsearch → Kibana）
  - 告警规则配置（钉钉/企业微信通知）

# Task Dependencies

## Phase 1 内部依赖
- [Task 1.2] 的 1.2.6-1.2.8 依赖 [Task 1.2] 的 1.2.1-1.2.5（ES基础设施）
- [Task 1.4] 的 1.4.5-1.4.6 依赖 [Task 1.4] 的 1.4.1-1.4.3（推送SDK基础）

## Phase 2 内部依赖
- [Task 2.4] 全部依赖 [Task 1.2]（ES搜索引擎必须先就绪）
- [Task 2.5] 的 2.5.5-2.5.6 依赖数据库表变更
- [Task 2.6] 的 2.6.5 前端SDK可独立开发

## Phase 3 内部依赖
- [Task 3.1] 的 3.1.2 需要外部AI API Key（需提前申请）
- [Task 3.3] 的 3.3.6 依赖 [Task 1.5]（播放器组件复用）

## 跨Phase依赖
- Phase 2 全部依赖 Phase 1 完成（Redis/ES/OSS/Push是P2功能的基础设施）
- Phase 3 依赖 Phase 1 + Phase 2（需要缓存/搜索/审核能力支撑）
- Phase 4 是独立架构升级，可与Phase 1-3并行规划但建议在功能稳定后再启动
