# Checklist

## 功能 1：收藏功能闭环
- [ ] FavoriteItem.kt 数据模型已创建，字段完整（id/newsId/title/pic/category/url/favoriteTime）
- [ ] PrefManager 已新增 6 个收藏 CRUD 方法 + KEY_FAVORITES 常量
- [ ] news_detail_menu.xml 菜单文件已创建，包含收藏 MenuItem
- [ ] NewsDetailActivity 已实现收藏/取消收藏逻辑：图标状态切换、Toast 提示、PrefManager 持久化
- [ ] FavoritesFragment 已绑定 NewsAdapter 展示收藏列表，空状态正常显示
- [ ] FavoritesFragment 支持长按取消收藏功能
- [ ] 未登录状态下收藏页面正确引导至登录页

## 功能 2：下拉刷新
- [ ] 7 个 Fragment 布局 XML 均已用 SwipeRefreshLayout 包裹 RecyclerView
- [ ] HomeNewsFragment 下拉刷新可触发数据重新加载，刷新动画正常消失
- [ ] HeadlineFragment 下拉刷新功能正常
- [ ] TechFragment 下拉刷新功能正常
- [ ] SportsFragment 下拉刷新功能正常
- [ ] EntertainmentFragment 下拉刷新功能正常
- [ ] FinanceFragment 下拉刷新功能正常
- [ ] LifeFragment 下拉刷新功能正常

## 功能 3：社交分享
- [ ] news_detail_menu.xml 包含分享 MenuItem
- [ ] NewsDetailActivity 点击分享按钮弹出系统分享面板
- [ ] 分享内容包含新闻标题和链接

## 功能 4：深色模式
- [ ] values-night/colors.xml 已创建，深色配色方案完整
- [ ] PrefManager 已新增 getNightMode()/saveNightMode() 方法
- [ ] ProfileFragment 有深色模式切换 UI（跟随系统/浅色/深色三选项）
- [ ] 切换深色模式后全局 UI 正确应用深色配色
- [ ] 模式选择正确持久化，重启 App 后保持用户选择

## 功能 5：推送通知
- [ ] AndroidManifest.xml 已添加 POST_NOTIFICATIONS 权限
- [ ] NotificationHelper.kt 工具类已创建，NotificationChannel 配置正确
- [ ] PushReceiver.kt BroadcastReceiver 已创建并注册到 Manifest
- [ ] NewsDetailActivity 有"推送提醒"菜单项入口
- [ ] 点击推送提醒后能成功发送通知栏消息
- [ ] API 33+ 设备首次使用时正确请求通知权限
