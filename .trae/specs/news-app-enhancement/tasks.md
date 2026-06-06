# Tasks

- [ ] Task 1: 实现收藏功能闭环
  - [ ] 1.1 新建 `FavoriteItem.kt` 数据模型（id/newsId/title/pic/category/url/favoriteTime）
  - [ ] 1.2 在 `PrefManager.kt` 中新增收藏相关 CRUD 方法（saveFavorites/getFavorites/addFavorite/removeFavorite/isFavorited/clearFavorites + KEY_FAVORITES 常量）
  - [ ] 1.3 新建 `res/menu/news_detail_menu.xml`，包含收藏和分享 MenuItem
  - [ ] 1.4 修改 `NewsDetailActivity.kt`：添加收藏按钮逻辑（onCreateOptionsMenu + onOptionsItemSelected），根据 newsId 判断已收藏状态并切换图标，调用 PrefManager 收藏/取消收藏
  - [ ] 1.5 修改 `FavoritesFragment.kt`：从 PrefManager 加载收藏数据，绑定 NewsAdapter 展示列表，处理空状态/非登录态，支持长按取消收藏

- [ ] Task 2: 实现下拉刷新功能
  - [ ] 2.1 修改 7 个 Fragment 布局 XML（fragment_home_news/fragment_headline/fragment_tech/fragment_sports/fragment_entertainment/fragment_finance/fragment_life）：用 SwipeRefreshLayout 包裹 RecyclerView
  - [ ] 2.2 修改 `HomeNewsFragment.kt`：绑定 SwipeRefreshLayout，设置 OnRefreshListener 调用 clearAndLoadNews()，刷新完成后 setColorSchemeColors + isRefreshing=false
  - [ ] 2.3 修改 `HeadlineFragment.kt`：同上逻辑
  - [ ] 2.4 修改 `TechFragment.kt`：同上逻辑
  - [ ] 2.5 修改 `SportsFragment.kt`：同上逻辑
  - [ ] 2.6 修改 `EntertainmentFragment.kt`：同上逻辑
  - [ ] 2.7 修改 `FinanceFragment.kt`：同上逻辑
  - [ ] 2.8 修改 `LifeFragment.kt`：同上逻辑

- [ ] Task 3: 实现社交分享功能
  - [ ] 3.1 修改 `NewsDetailActivity.kt`：在 onOptionsItemSelected 中添加分享 MenuItem 处理逻辑，使用 ShareCompat.Intent.Builder 构建分享 Intent（标题+链接）

- [ ] Task 4: 实现深色模式
  - [ ] 4.1 新建 `res/values-night/colors.xml`：定义深色配色方案（bg_color/fg_color/text_primary/text_secondary/card_background/divider_color 等）
  - [ ] 4.2 在 `PrefManager.kt` 中新增 getNightMode()/saveNightMode() 方法及 KEY_NIGHT_MODE 常量
  - [ ] 4.3 修改 `ProfileFragment.kt`：在设置区域添加"深色模式"切换入口（跟随系统/浅色/深色三个选项），切换时调用 AppCompatDelegate.setDefaultNightMode() 并持久化
  - [ ] 4.4 修改 `HomeActivity.kt` 或 `Application` 类：在 onCreate 时读取持久化的 night mode 设置并应用

- [ ] Task 5: 实现推送通知基础功能
  - [ ] 5.1 修改 `AndroidManifest.xml`：添加 POST_NOTIFICATIONS 权限
  - [ ] 5.2 新建 `NotificationHelper.kt` 工具类：创建 NotificationChannel（频道ID "news_push"，名称 "新闻推送"）、构建 sendNewsNotification(title/content/intent) 方法、请求通知权限方法
  - [ ] 5.3 新建 `PushReceiver.kt`(BroadcastReceiver)：接收 AlarmManager 广播后调用 NotificationHelper 发送通知
  - [ ] 5.4 在 `AndroidManifest.xml` 中注册 PushReceiver
  - [ ] 5.5 在 `NewsDetailActivity.kt` 中添加"推送提醒"菜单项：点击后使用 AlarmManager 设置定时推送（测试用 30 秒后触发）

# Task Dependencies
- [Task 1] 和 [Task 2] 和 [Task 3] 可并行执行（互不依赖）
- [Task 4] 独立可并行
- [Task 5] 独立可并行
- Task 3 的菜单文件 (news_detail_menu.xml) 与 Task 1 共享，建议 Task 1 先创建该文件，Task 3 再往其中添加 share MenuItem → **实际执行中 Task 1 先于 Task 3 创建 menu 文件**
