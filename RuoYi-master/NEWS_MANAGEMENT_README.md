
# 新闻管理系统 - 部署说明

## 项目概述

本项目是在若依(RuoYi)框架基础上扩展的新闻管理系统，包含用户权限管理和新闻内容管理功能。

## 技术栈

- **后端**: Spring Boot + MyBatis + Shiro
- **前端**: Thymeleaf + Bootstrap + jQuery
- **数据库**: MySQL

## 功能特性

### 1. 用户权限管理
- 用户管理（复用若依现有功能）
- 角色管理（复用若依现有功能）
- 菜单权限管理

### 2. 新闻分类管理
- 分类增删改查
- 分类排序
- 分类启用/禁用

### 3. 新闻内容管理
- 新闻增删改查
- 富文本编辑器支持
- 图片上传支持
- 新闻状态管理（草稿/已发布/已下线）
- 新闻分类关联

### 4. API 开放接口
- 分类列表查询
- 新闻列表查询（支持分页、分类筛选）
- 新闻详情查询

## 部署步骤

### 1. 数据库初始化

1. 确保已创建若依数据库
2. 执行 SQL 脚本：
   ```
   f:\Git Hub Project\news app\RuoYi-master\sql\news_tables.sql
   ```
   该脚本会创建：
   - `news_category` 表（新闻分类）
   - `news_article` 表（新闻文章）
   - 相关菜单和权限数据

### 2. 后端部署

1. 将项目导入 IDE（如 IntelliJ IDEA）
2. 配置数据库连接（修改 `application.yml`）
3. 启动 RuoYi 主程序

### 3. 访问系统

1. 启动成功后访问：`http://localhost:80`
2. 使用若依管理员账号登录
3. 在左侧菜单可看到"新闻管理"菜单
4. 包含"新闻分类"和"新闻内容"两个子菜单

## 文件结构

### 后端代码文件清单

```
RuoYi-master/
├── ruoyi-system/
│   ├── src/main/java/com/ruoyi/system/
│   │   ├── domain/
│   │   │   ├── NewsCategory.java      # 新闻分类实体
│   │   │   └── NewsArticle.java       # 新闻文章实体
│   │   ├── mapper/
│   │   │   ├── NewsCategoryMapper.java
│   │   │   └── NewsArticleMapper.java
│   │   └── service/
│   │       ├── INewsCategoryService.java
│   │       ├── INewsArticleService.java
│   │       └── impl/
│   │           ├── NewsCategoryServiceImpl.java
│   │           └── NewsArticleServiceImpl.java
│   └── src/main/resources/mapper/system/
│       ├── NewsCategoryMapper.xml
│       └── NewsArticleMapper.xml
│
└── ruoyi-admin/
    ├── src/main/java/com/ruoyi/web/controller/
    │   ├── system/
    │   │   ├── NewsCategoryController.java    # 新闻分类管理控制器
    │   │   └── NewsArticleController.java     # 新闻文章管理控制器
    │   └── api/
    │       ├── CategoryApiController.java     # 分类开放接口控制器
    │       └── NewsApiController.java        # 新闻开放接口控制器
    │
    └── src/main/resources/templates/system/news/
        ├── category/
        │   ├── category.html              # 分类列表页
        │   ├── add.html                 # 新增分类页
        │   └── edit.html                 # 编辑分类页
        └── article/
            ├── article.html             # 新闻列表页
            ├── add.html            # 新增新闻页
            ├── edit.html           # 编辑新闻页
            └── detail.html         # 新闻详情页
```

## API 接口文档

### 公开接口（无需登录）

#### 1. 获取分类列表

```
GET /api/category/list
```

**响应示例：**
```json
{
  "code": 200,
  "data": [
    {
      "categoryId": 1,
      "categoryName": "科技新闻",
      "categoryCode": "tech"
    }
  ]
}
```

#### 2. 获取新闻列表

```
GET /api/news/list?categoryId=1&amp;pageNum=1&amp;pageSize=10
```

**参数：**
- `categoryId` (可选): 分类 ID
- `pageNum` (可选): 页码，默认 1
- `pageSize` (可选): 每页数量，默认 10

#### 3. 获取新闻详情

```
GET /api/news/{id}
```

**说明：访问此接口会自动增加新闻浏览量

## 权限说明

| 权限标识 | 说明
|---------|------|
| `news:category:view` | 查看分类 |
| `news:category:list` | 查询分类列表 |
| `news:category:add` | 新增分类 |
| `news:category:edit` | 编辑分类 |
| `news:category:remove` | 删除分类 |
| `news:article:view` | 查看新闻 |
| `news:article:list` | 查询新闻列表 |
| `news:article:add` | 新增新闻 |
| `news:article:edit` | 编辑新闻 |
| `news:article:remove` | 删除新闻 |

## 注意事项

1. **新闻状态说明：
   - `0`: 草稿
   - `1`: 已发布（API 仅返回此状态的新闻）
   - `2`: 已下线

2. **图片上传：
   - 使用若依框架自带的上传功能
   - 上传路径配置在 `application.yml` 中

3. **富文本编辑器：
   - 使用 Summernote 编辑器
   - 支持图片粘贴和上传

## 版本信息

- 若依框架版本：4.8.3
- Java 版本：17+
- Spring Boot 版本：4.0.3

