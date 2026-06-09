# 新闻应用功能迭代 - The Implementation Plan (Decomposed and Prioritized Task List)

## [x] Task 1: 完善订阅/话题/关注系统UI和功能
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 完善订阅页面UI，显示可订阅的频道列表、可关注的作者列表、热门话题列表
  - 实现订阅/取消订阅频道的功能
  - 实现关注/取消关注作者的功能
  - 实现话题聚合页面，显示该话题下的相关新闻
  - 集成到主界面的底部导航或首页标签
- **Acceptance Criteria Addressed**: AC-1
- **Test Requirements**:
  - `programmatic` TR-1.1: 用户可以看到可订阅的频道列表，状态显示正确（已订阅/未订阅）
  - `programmatic` TR-1.2: 用户点击订阅/取消订阅后状态立即更新，数据持久化存储
  - `human-judgement` TR-1.3: 话题聚合页面能正确显示该话题下的新闻，UI美观
- **Notes**: 可复用现有的PrefManager中已有的订阅相关数据结构

## [x] Task 2: 将个性化推荐流集成到首页"推荐"标签
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 修改HomeNewsFragment的"推荐"标签逻辑
  - 当用户选择"推荐"标签时，调用RecommendationEngine获取个性化推荐内容
  - 其他标签保持原有的分类新闻逻辑
  - 确保推荐内容的下拉刷新和加载更多正常工作
- **Acceptance Criteria Addressed**: AC-2
- **Test Requirements**:
  - `programmatic` TR-2.1: 点击首页"推荐"标签时，显示基于用户浏览历史的推荐内容
  - `programmatic` TR-2.2: 推荐内容支持下拉刷新和无限加载
  - `human-judgement` TR-2.3: 推荐内容与用户兴趣匹配度合理
- **Notes**: 已有RecommendationEngine和RecommendationFragment，主要是集成到首页

## [ ] Task 3: 视频/音频新闻支持
- **Priority**: P1
- **Depends On**: None
- **Description**: 
  - 在NewsItem中添加媒体类型字段（图文/视频/音频）
  - 添加视频播放器组件
  - 在新闻列表和详情页中正确区分和显示不同媒体类型
  - 实现视频的播放控制（播放/暂停/进度条/全屏）
- **Acceptance Criteria Addressed**: AC-3
- **Test Requirements**:
  - `human-judgement` TR-3.1: 视频新闻在列表中有视频标识（如播放按钮）
  - `human-judgement` TR-3.2: 点击视频可以正常播放，播放器控件完整可用
  - `programmatic` TR-3.3: 音频新闻能正确播放，支持后台播放
- **Notes**: 可以使用Android原生VideoView或ExoPlayer，初期使用原生方案降低复杂度

## [ ] Task 4: 本地新闻/位置服务
- **Priority**: P1
- **Depends On**: None
- **Description**: 
  - 添加位置权限请求和处理逻辑
  - 实现获取用户地理位置功能
  - 在首页或分类页添加"本地"标签
  - 根据用户位置筛选或获取本地新闻
- **Acceptance Criteria Addressed**: AC-4
- **Test Requirements**:
  - `human-judgement` TR-4.1: 用户首次进入本地新闻页面时会请求位置权限
  - `programmatic` TR-4.2: 成功获取位置后能正确显示本地新闻
  - `human-judgement` TR-4.3: 用户可以手动切换城市或关闭本地定位
- **Notes**: 初期可以根据城市名参数调用现有API，后期考虑真正的本地新闻源

## [ ] Task 5: 离线阅读功能
- **Priority**: P1
- **Depends On**: None
- **Description**: 
  - 添加新闻下载功能（在新闻详情页和新闻列表添加下载按钮）
  - 使用本地存储（Room数据库或文件系统）保存已下载的新闻
  - 添加"离线阅读"页面，显示已下载的新闻列表
  - 离线状态下可正常查看已下载新闻
- **Acceptance Criteria Addressed**: AC-5
- **Test Requirements**:
  - `programmatic` TR-5.1: 用户可以下载新闻，下载进度有提示
  - `programmatic` TR-5.2: 断网后能正常查看已下载的新闻
  - `human-judgement` TR-5.3: 已下载新闻在"离线阅读"页面清晰展示
- **Notes**: 可以使用Room数据库管理下载的新闻元数据，WebView缓存或HTML文件保存内容

## [ ] Task 6: 内容举报/审核功能
- **Priority**: P1
- **Depends On**: None
- **Description**: 
  - 在新闻详情页、评论区添加入口举报按钮
  - 实现举报弹窗/页面，支持选择举报原因
  - 提交举报后有状态反馈
  - 实现基础的敏感词过滤（评论发布时检查）
- **Acceptance Criteria Addressed**: FR-6
- **Test Requirements**:
  - `human-judgement` TR-6.1: 用户可以方便地找到举报入口
  - `programmatic` TR-6.2: 举报原因选择完整，提交成功有反馈
  - `programmatic` TR-6.3: 发布评论时基础敏感词能被检测和提示
- **Notes**: 初期在本地处理，后期可与后端对接

## [ ] Task 7: 用户个人资料完善
- **Priority**: P1
- **Depends On**: None
- **Description**: 
  - 完善个人资料编辑页面
  - 支持上传/更换头像（从相册选择或拍照）
  - 支持编辑昵称、个人简介
  - 添加实名认证入口（初期可以只显示，不实现真实验证）
- **Acceptance Criteria Addressed**: FR-7
- **Test Requirements**:
  - `human-judgement` TR-7.1: 用户可以进入个人资料编辑页面
  - `programmatic` TR-7.2: 头像、昵称等修改可以保存和生效
  - `human-judgement` TR-7.3: 编辑页面UI友好，操作流畅
- **Notes**: 头像存储可以使用本地文件或Glide缓存

## [ ] Task 8: 直播功能（预留架构）
- **Priority**: P2
- **Depends On**: None
- **Description**: 
  - 在首页或分类页添加"直播"入口
  - 实现直播列表页面UI（显示正在直播和即将直播）
  - 为直播播放器预留架构（不实现真实直播流）
- **Acceptance Criteria Addressed**: FR-8
- **Test Requirements**:
  - `human-judgement` TR-8.1: 直播入口和列表页面布局完整
  - `human-judgement` TR-8.2: 直播卡片设计美观，有状态标识
- **Notes**: 先做UI和架构预留，真实直播功能需要后端配合

## [ ] Task 9: 广告系统（预留架构）
- **Priority**: P2
- **Depends On**: None
- **Description**: 
  - 在新闻列表中预留广告位置
  - 实现简单的模拟广告展示
  - 支持Banner广告和信息流广告的UI布局
- **Acceptance Criteria Addressed**: FR-9
- **Test Requirements**:
  - `human-judgement` TR-9.1: 广告位置和布局预留正确
  - `human-judgement` TR-9.2: 模拟广告展示自然不突兀
- **Notes**: 初期只做UI和架构，真实广告需要对接广告平台

## [ ] Task 10: AI摘要/音频播报（预留架构）
- **Priority**: P2
- **Depends On**: None
- **Description**: 
  - 在新闻详情页添加"AI摘要"和"听新闻"入口
  - 预留相关UI和交互
  - 显示模拟的AI摘要内容
- **Acceptance Criteria Addressed**: FR-11
- **Test Requirements**:
  - `human-judgement` TR-10.1: AI摘要和听新闻入口设计合理
  - `human-judgement` TR-10.2: 模拟功能流程完整
- **Notes**: 先做UI和交互流程，真实AI/音频功能需要对接第三方服务

## [ ] Task 11: 整体性能优化和体验提升
- **Priority**: P2
- **Depends On**: Task 1-10（可并行）
- **Description**: 
  - 优化页面加载速度
  - 优化列表滑动流畅度
  - 优化内存占用和启动速度
  - 修复已知Bug，提升稳定性
- **Acceptance Criteria Addressed**: NFR-1, NFR-2, NFR-3
- **Test Requirements**:
  - `programmatic` TR-11.1: 应用启动时间 <1.5s
  - `programmatic` TR-11.2: 列表滑动无明显卡顿（60fps）
  - `human-judgement` TR-11.3: 整体使用体验流畅，无明显Bug
- **Notes**: 贯穿整个迭代过程，持续优化
