# Tasks

- [ ] Task 1: 在 PrefManager.initializeMockData() 中添加热推数据初始化
  - [ ] 1.1 创建 8-10 条模拟热推数据，包含真实可访问的图片 URL（使用 Unsplash）
  - [ ] 1.2 每条数据包含完整的字段：id, title, content, pic, pushTime, isTop, views, likes, comments, newsUrl
  - [ ] 1.3 调用 saveHotPushes() 保存数据到 SharedPreferences
  - [ ] 1.4 确保图片 URL 使用 https 协议且格式正确

- [ ] Task 2: 验证热推页面图片显示
  - [ ] 2.1 确认 HotPushAdapter 的 Glide 加载逻辑正确
  - [ ] 2.2 验证占位符和错误图配置合理
  - [ ] 2.3 测试不同网络状态下的图片加载

# Task Dependencies
- Task 2 depends on Task 1
