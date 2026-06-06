# 新闻 App 五大功能增强 Spec

## Why

当前新闻 App 与主流竞品（今日头条、腾讯新闻、网易新闻）相比，存在 **收藏功能未闭环、无下拉刷新、无社交分享、无深色模式、无推送通知** 等核心功能缺失，严重影响用户体验和产品竞争力。本规格将按投入产出比从高到低依次补齐这 5 项功能。

## What Changes

### 功能 1：收藏功能闭环
- 新增 `FavoriteItem` 数据模型（含 newsId/title/pic/category/url/favoriteTime）
- `PrefManager` 新增收藏数据的完整 CRUD 方法（save/get/add/remove/isFavored/clearFavorites）
- `NewsDetailActivity` Toolbar 添加收藏/取消收藏 MenuItem（图标随状态切换）
- `FavoritesFragment` 绑定 `NewsAdapter` 展示收藏列表，支持点击跳转详情、长按取消收藏、空状态处理
- `activity_news_detail.xml` 无需改动（使用 Toolbar Menu）

### 功能 2：下拉刷新
- 为以下 7 个新闻列表 Fragment 的布局 XML 包裹 `SwipeRefreshLayout`：
  - `fragment_home_news.xml`（HomeNewsFragment）
  - `fragment_headline.xml`（HeadlineFragment）
  - `fragment_tech.xml`（TechFragment）
  - `fragment_sports.xml`（SportsFragment）
  - `fragment_entertainment.xml`（EntertainmentFragment）
  - `fragment_finance.xml`（FinanceFragment）
  - `fragment_life.xml`（LifeFragment）
- 各 Fragment 的 Kotlin 代码中设置 `OnRefreshListener` 触发 `clearAndLoadNews()`
- 配置 SwipeRefreshLayout 颜色方案（primary_color）

### 功能 3：社交分享
- `NewsDetailActivity` Toolbar 添加分享 MenuItem
- 使用 Android 原生 `ShareCompat.Intent.Builder` 构建分享面板
- 分享内容包含：新闻标题 + URL 摘要
- 选择器标题设为"分享新闻到"

### 功能 4：深色模式
- 当前主题已继承 `Theme.Material3.DayNight.NoActionBar`，具备 DayNight 基础设施
- 新增 `values-night/colors.xml` 定义深色配色方案
- `ProfileFragment` 添加深色/浅色模式切换入口（设置项）
- 使用 `AppCompatDelegate.setDefaultNightMode()` 切换模式
- 模式选择持久化到 SharedPreferences
- 关键页面适配深色背景：Activity/Home/NewsDetail/Favorites 等

### 功能 5：推送通知
- AndroidManifest.xml 添加 `POST_NOTIFICATIONS` 权限（API 33+）
- 创建 `NotificationHelper` 工具类：创建 NotificationChannel、构建并发送通知
- `NewsDetailActivity` 添加"推送提醒"菜单项（允许用户对该频道开启推送）
- 推送偏好持久化到 PrefManager
- 实现本地定时推送模拟（使用 AlarmManager + BroadcastReceiver）
- 推送分级：突发新闻（高优先级）vs 普通推荐（默认优先级）

## Impact

- Affected specs: 无依赖已有 spec
- Affected code:
  - **新增文件**: `FavoriteItem.kt`, `NotificationHelper.kt`, `PushReceiver.kt`, `values-night/colors.xml`
  - **修改文件**: `PrefManager.kt`, `NewsDetailActivity.kt`, `FavoritesFragment.kt`, `HomeNewsFragment.kt`, `HeadlineFragment.kt`, `TechFragment.kt`, `SportsFragment.kt`, `EntertainmentFragment.kt`, `FinanceFragment.kt`, `LifeFragment.kt`, `ProfileFragment.kt`, `AndroidManifest.xml`, 7 个 fragment layout XML, `res/menu/news_detail_menu.xml`(新增), `themes.xml`

## ADDED Requirements

### REQ-FAV-1: 收藏数据模型
系统 SHALL 提供 `FavoriteItem` 数据类，包含 id/newsId/title/pic/category/url/favoriteTime 字段。

#### Scenario: 收藏一篇文章
- **WHEN** 用户在新闻详情页点击收藏按钮
- **THEN** 文章信息保存至 PrefManager，收藏图标变为已收藏状态，Toast 提示"已收藏"

#### Scenario: 取消收藏
- **WHEN** 用户在新闻详情页再次点击已收藏的按钮
- **THEN** 该文章从收藏列表移除，图标恢复未收藏状态，Toast 提示"已取消收藏"

#### Scenario: 收藏列表展示
- **WHEN** 已登录用户进入收藏页面
- **THEN** 显示所有已收藏文章的 RecyclerView 列表；空列表时显示"暂无收藏内容"；点击跳转新闻详情

### REQ-SWIPE-1: 下拉刷新
系统 SHALL 为所有新闻列表 Fragment 提供 SwipeRefreshLayout 下拉刷新能力。

#### Scenario: 下拉刷新触发
- **WHEN** 用户在任意新闻列表页下拉
- **THEN** 列表清空并重新加载最新数据，刷新动画结束后自动消失

### REQ-SHARE-1: 社交分享
系统 SHALL 在新闻详情页提供分享功能。

#### Scenario: 分享文章
- **WHEN** 用户在新闻详情页点击分享按钮
- **THEN** 弹出系统分享面板，包含文章标题和链接

### REQ-DARK-1: 深色模式
系统 SHALL 支持深色/浅色/跟随系统三种模式切换。

#### Scenario: 切换深色模式
- **WHEN** 用户在个人中心切换深色模式
- **THEN** 全局 UI 切换为深色配色，模式选择被持久化

#### Scenario: 跟随系统
- **WHEN** 用户选择"跟随系统"
- **THEN** App 跟随系统深色/浅色设置自动切换

### REQ-PUSH-1: 推送通知
系统 SHALL 提供基础推送通知能力。

#### Scenario: 发送推送通知
- **WHEN** 后端触发或定时任务到达
- **THEN** 系统显示通知栏消息，点击可打开对应新闻详情

#### Scenario: 推送权限
- **WHEN** API 33+ 设备首次需要发送通知
- **THEN** 系统请求 POST_NOTIFICATIONS 权限

## MODIFIED Requirements

### REQ-PREFMANAGER: PrefManager 扩展
PrefManager SHALL 新增以下方法：
- `saveFavorites(favorites: List<FavoriteItem>)`
- `getFavorites(): List<FavoriteItem>`
- `addFavorite(item: FavoriteItem)`
- `removeFavorite(newsId: String)`
- `isFavorited(newsId: String): Boolean`
- `clearFavorites()`
- `getNightMode(): Int` / `saveNightMode(mode: Int)`

## REMOVED REQUIREMENTS

无。
