# 热推图片显示修复 Spec

## Why
热推（热门推荐）页面的新闻图片无法正常显示，全部显示为紫色占位符图标。经过代码分析发现根本原因是：**热推数据从未被正确初始化**，导致 `pic` 字段为空或无效。

## What Changes
- **问题根因分析**：
  1. `PrefManager.initializeMockData()` 方法只初始化了 topics/channels/authors，**没有初始化热推数据**
  2. `HotPushFragment` 仅从本地 SharedPreferences 读取数据，但没有数据源
  3. 服务器 `server.js` 未提供热推相关 API
  4. 当 `hotPushItem.pic` 为空时，Glide 显示 placeholder_news 占位符

- **修复方案**：
  1. 在 `initializeMockData()` 中添加热推模拟数据的初始化
  2. 为每条热推数据提供有效的图片 URL（使用 unsplash 等公共图库）
  3. 确保图片 URL 可访问且格式正确

## Impact
- Affected specs: 无
- Affected code:
  - [PrefManager.kt](app/src/main/java/com/example/myapplication/PrefManager.kt) - 添加热推数据初始化
  - [HotPushAdapter.kt](app/src/main/java/com/example/myapplication/HotPushAdapter.kt) - 图片加载逻辑验证

## ADDED Requirements

### Requirement: 热推数据初始化
系统 SHALL 在首次启动时自动初始化热推模拟数据，包含有效的图片 URL。

#### Scenario: 成功初始化热推数据
- **WHEN** 应用首次启动或数据为空时
- **THEN** 系统自动创建 8-10 条热推新闻数据
- **AND** 每条数据包含有效可访问的图片 URL
- **AND** 图片能通过 Glide 正常加载显示

### Requirement: 图片显示验证
系统 SHALL 能够正确加载并显示热推新闻的配图。

#### Scenario: 图片正常显示
- **WHEN** 用户进入热推页面
- **THEN** 每条新闻卡片显示真实的配图而非占位符
- **AND** 图片使用圆角裁剪和居中缩放模式

## MODIFIED Requirements

### Requirement: Mock 数据初始化
修改 `initializeMockData()` 方法，在现有初始化逻辑后添加热推数据的创建和保存。

```kotlin
// 新增热推数据初始化
val hotPushes = listOf(
    HotPushItem(
        id = "hp_1",
        title = "重磅新闻：2024年科技发展趋势报告发布",
        content = "今日发布的2024年科技发展趋势报告显示，人工智能、云计算和5G技术...",
        pic = "https://images.unsplash.com/photo-1677442136019-21780ecad995?w=400&h=300&fit=crop",
        pushTime = System.currentTimeMillis() - 3600000,
        isTop = true,
        views = 16500,
        likes = 892,
        comments = 456,
        newsUrl = "https://example.com/news/1"
    ),
    // ... 更多热推数据
)
saveHotPushes(hotPushes)
```

## REMOVED Requirements
无
