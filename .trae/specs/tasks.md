# 新闻应用迭代优化 - 实施计划

## Phase 1: 核心架构重构 (P0)

### [x] Task 1: 数据库表结构扩展
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 设计并创建用户行为数据相关表结构（浏览历史、收藏、评论、用户信息扩展等）
  - 参考PrefManager.kt中的数据结构设计数据库表
  - 更新news_tables.sql脚本
- **Acceptance Criteria Addressed**: AC-002
- **Test Requirements**:
  - `programmatic` TR-1.1: 所有表结构创建成功，字段类型和约束正确
  - `programmatic` TR-1.2: 可以插入测试数据并正确查询
- **Notes**: 表设计要符合RuoYi的命名规范和结构

### [x] Task 2: 整合用户认证到RuoYi
- **Priority**: P0
- **Depends On**: Task 1
- **Description**: 
  - 分析Node.js server.js中的认证逻辑
  - 将用户注册、登录、密码修改功能整合到RuoYi的SysUser相关模块
  - 保持密码加密方式兼容（bcrypt）
- **Acceptance Criteria Addressed**: AC-001
- **Test Requirements**:
  - `programmatic` TR-2.1: 用户注册API正常工作
  - `programmatic` TR-2.2: 用户登录API返回正确结果
  - `programmatic` TR-2.3: 密码修改功能正常
- **Notes**: 向后兼容现有Node.js创建的用户数据

### [x] Task 3: 扩展用户管理API
- **Priority**: P0
- **Depends On**: Task 2
- **Description**: 
  - 创建用户信息扩展表的Domain、Mapper、Service、Controller
  - 实现用户画像、积分、关注关系等数据的CRUD API
  - 统一使用/api前缀，保持匿名访问或JWT认证
- **Acceptance Criteria Addressed**: AC-001, AC-003
- **Test Requirements**:
  - `programmatic` TR-3.1: 所有用户数据API可正常调用
  - `programmatic` TR-3.2: API返回JSON格式符合Android端期望

### [x] Task 4: 完善新闻管理API
- **Priority**: P0
- **Depends On**: Task 1
- **Description**: 
  - 扩展NewsApiController，增加更多功能接口
  - 实现新闻浏览记录、收藏、点赞、评论等API
  - 保持与现有新闻表结构兼容
- **Acceptance Criteria Addressed**: AC-003
- **Test Requirements**:
  - `programmatic` TR-4.1: 所有新闻相关API可正常调用
  - `programmatic` TR-4.2: 分页查询、条件筛选等功能正常

### [x] Task 5 & 6: Android端API集成和数据迁移
- **Priority**: P0
- **Depends On**: Task 3, Task 4
- **Description**: 
  - 创建统一的ApiClient类封装网络请求
  - 修改PrefManager，添加数据同步逻辑
  - 将所有本地数据操作替换为API调用
  - 保留本地缓存作为降级方案
- **Acceptance Criteria Addressed**: AC-001, AC-002
- **Test Requirements**:
  - `human-judgment` TR-5.1: Android端所有功能正常工作
  - `programmatic` TR-5.2: 网络请求监控显示所有请求发送到RuoYi

### [ ] Task 6: 数据迁移工具开发
- **Priority**: P0
- **Depends On**: Task 5
- **Description**: 
  - Android端实现数据检测和迁移逻辑
  - 首次登录时检查本地数据，未迁移则上传到云端
  - 实现冲突处理策略（可配置云端优先或合并）
- **Acceptance Criteria Addressed**: AC-002
- **Test Requirements**:
  - `programmatic` TR-6.1: 本地数据可以完整上传到云端
  - `human-judgment` TR-6.2: 多设备登录时数据一致

## Phase 2: 核心体验优化 (P1)

### [ ] Task 7: JWT认证实现
- **Priority**: P1
- **Depends On**: Task 2
- **Description**: 
  - 集成JWT到RuoYi的Shiro权限系统
  - 实现token生成、刷新、验证逻辑
  - Android端添加token管理
- **Acceptance Criteria Addressed**: AC-001
- **Test Requirements**:
  - `programmatic` TR-7.1: JWT token正常生成和验证
  - `programmatic` TR-7.2: token过期后自动刷新

### [ ] Task 8: 用户画像系统
- **Priority**: P1
- **Depends On**: Task 3
- **Description**: 
  - 设计用户兴趣标签体系
  - 实现基于用户行为的标签自动更新
  - 提供用户画像查询和编辑API
- **Acceptance Criteria Addressed**: AC-004
- **Test Requirements**:
  - `programmatic` TR-8.1: 用户浏览新闻后兴趣标签正确更新
  - `human-judgment` TR-8.2: 标签体系合理覆盖内容分类

### [ ] Task 9: 推荐系统实现
- **Priority**: P1
- **Depends On**: Task 8
- **Description**: 
  - 实现协同过滤推荐算法（基于用户或基于物品）
  - 实现热门推荐作为冷启动备用方案
  - 提供推荐API接口
- **Acceptance Criteria Addressed**: AC-004
- **Test Requirements**:
  - `programmatic` TR-9.1: 推荐API在1秒内返回结果
  - `human-judgment` TR-9.2: 推荐结果与用户兴趣相关

### [ ] Task 10: 内容展示优化
- **Priority**: P1
- **Depends On**: Task 5
- **Description**: 
  - 优化NewsDetailActivity，支持富媒体内容渲染
  - 改进图片加载和缓存策略
  - 优化阅读体验（字体、夜间模式等）
- **Acceptance Criteria Addressed**: AC-004
- **Test Requirements**:
  - `human-judgment` TR-10.1: 富媒体内容正常展示
  - `human-judgment` TR-10.2: 阅读体验流畅

### [ ] Task 11: 社交功能完善
- **Priority**: P1
- **Depends On**: Task 3
- **Description**: 
  - 实现关注/粉丝功能API和UI
  - 完善评论和互动功能
  - 添加社交分享功能
- **Acceptance Criteria Addressed**: AC-003
- **Test Requirements**:
  - `human-judgment` TR-11.1: 关注功能正常工作
  - `programmatic` TR-11.2: 社交关系数据正确存储和查询

## Phase 3: 功能增强 (P2)

### [ ] Task 12: 多媒体内容支持
- **Priority**: P2
- **Depends On**: Task 4, Task 10
- **Description**: 
  - 扩展新闻表结构，支持视频/音频字段
  - 后端实现多媒体文件上传和管理
  - Android端实现视频/音频播放器集成
- **Acceptance Criteria Addressed**: AC-003
- **Test Requirements**:
  - `human-judgment` TR-12.1: 视频新闻可以正常播放
  - `human-judgment` TR-12.2: 音频新闻可以正常播放

### [ ] Task 13: 活动系统和积分体系完善
- **Priority**: P2
- **Depends On**: Task 3
- **Description**: 
  - 完善活动管理功能
  - 实现积分获取和消耗规则
  - 完善积分商城功能
- **Acceptance Criteria Addressed**: AC-003
- **Test Requirements**:
  - `human-judgment` TR-13.1: 活动功能正常使用
  - `programmatic` TR-13.2: 积分计算和记录正确

### [ ] Task 14: 性能优化和监控
- **Priority**: P2
- **Depends On**: All P0, P1 tasks
- **Description**: 
  - API性能优化（缓存、索引等）
  - 添加性能监控和日志
  - 数据库查询优化
- **Acceptance Criteria Addressed**: AC-005
- **Test Requirements**:
  - `programmatic` TR-14.1: 性能测试满足NFR-001
  - `programmatic` TR-14.2: 并发测试满足NFR-002
