# Tasks

- [x] Task 1: 替换 PrefManager.initializeMockData() 中的所有假图片 URL 为 picsum.photos 真实 URL
  - [x] 1.1 替换浏览历史 3 条记录的 pic URL（pic1→ai-news, pic2→finance, pic3→sports）
  - [x] 1.2 替换热推 5 条记录的 hot URL（hot1→tech-summit, hot2→ai-breakthrough, hot3→stock-market, hot4→world-cup, hot5→healthy-food）
  - [x] 1.3 替换积分商城 3 个商品的 product URL（product1→water-cup, product2→bluetooth-earphone, product3→notebook）
  - [x] 1.4 替换兑换记录 1 条记录的 product URL（与商品1一致）
  - [x] 1.5 替换群聊 2 个群组的 group URL（group1→news-discussion, group2→tech-lovers）
  - [x] 1.6 替换聊天消息 1 条记录的 avatar URL（avatar1→user-zhangsan）

- [x] Task 2: 在 GroupAdapter 中添加群组头像 Glide 加载逻辑
  - [x] 2.1 在 bind() 方法中添加 Glide.with().load(group.avatar) 调用
  - [x] 2.2 设置 placeholder 和 error 占位图

- [x] Task 3: 在 ChatMessageAdapter 中添加用户头像 Glide 加载逻辑
  - [x] 3.1 在 left ViewHolder bind() 中添加 Glide 加载头像
  - [x] 3.2 在 right ViewHolder bind() 中添加 Glide 加载头像
  - [x] 3.3 设置 placeholder 和 error 占位图

# Task Dependencies
- 无依赖关系，Task 1 / Task 2 / Task 3 可并行执行
