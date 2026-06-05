
# 趣何资讯 - 访客模式访问功能实现计划

## [ ] Task 1: 修改 AndroidManifest.xml，将 HomeActivity 设为启动 Activity
- **Priority**: high
- **Depends On**: None
- **Description**: 
  - 修改 AndroidManifest.xml，将 HomeActivity 设置为应用启动的主 Activity
  - 移除 LoginActivity 的 LAUNCHER intent-filter
- **Acceptance Criteria Addressed**: AC-1
- **Test Requirements**:
  - programmatic: 启动应用，验证是否直接进入 HomeActivity
- **Notes**: 保持 LoginActivity 可被其他页面启动

## [ ] Task 2: 修改 HomeActivity，移除强制登录检查
- **Priority**: high
- **Depends On**: Task 1
- **Description**: 
  - 移除 HomeActivity 中的强制登录检查逻辑
  - 保留 PrefManager 初始化，用于后续判断登录状态
  - 确保未登录用户也能正常进入主页面
- **Acceptance Criteria Addressed**: AC-1, AC-2
- **Test Requirements**:
  - programmatic: 未登录状态下启动应用，验证是否可以正常进入 HomeActivity
  - programmatic: 验证首页和分类页面是否可以正常访问
- **Notes**: 不要删除 PrefManager 相关代码，后续功能需要使用

## [ ] Task 3: 修改 ProfileFragment，根据登录状态显示不同内容
- **Priority**: high
- **Depends On**: Task 2
- **Description**: 
  - 修改 ProfileFragment 的布局和逻辑
  - 未登录时显示登录/注册按钮和提示文字
  - 已登录时显示用户信息和功能菜单（修改密码、关于、退出登录等）
  - 点击登录按钮跳转到 LoginActivity
- **Acceptance Criteria Addressed**: AC-4, AC-6
- **Test Requirements**:
  - programmatic: 未登录时访问 ProfileFragment，验证是否显示登录界面
  - programmatic: 已登录时访问 ProfileFragment，验证是否显示用户信息和功能菜单
  - programmatic: 点击登录按钮，验证是否跳转到 LoginActivity

## [ ] Task 4: 修改 FavoritesFragment，未登录时提示登录
- **Priority**: high
- **Depends On**: Task 2
- **Description**: 
  - 修改 FavoritesFragment 的布局和逻辑
  - 未登录时显示登录提示和登录按钮
  - 已登录时正常显示收藏列表
  - 点击登录按钮跳转到 LoginActivity
- **Acceptance Criteria Addressed**: AC-3
- **Test Requirements**:
  - programmatic: 未登录时访问 FavoritesFragment，验证是否显示登录提示
  - programmatic: 已登录时访问 FavoritesFragment，验证是否正常显示收藏列表
  - programmatic: 点击登录按钮，验证是否跳转到 LoginActivity

## [ ] Task 5: 修改 MainActivity（如果需要）
- **Priority**: medium
- **Depends On**: None
- **Description**: 
  - 检查 MainActivity 是否还被使用
  - 如果不再使用，可以考虑移除或保留作为备用
- **Acceptance Criteria Addressed**: None
- **Test Requirements**:
  - human-judgment: 确认 MainActivity 的用途和是否需要保留
- **Notes**: 根据实际使用情况决定是否需要修改

## [ ] Task 6: 测试整个应用的功能
- **Priority**: medium
- **Depends On**: Task 1, Task 2, Task 3, Task 4
- **Description**: 
  - 全面测试未登录状态下的功能
  - 全面测试登录状态下的功能
  - 测试登录/登出流程
  - 测试从各个需要登录的页面跳转到登录页的流程
- **Acceptance Criteria Addressed**: AC-1, AC-2, AC-3, AC-4, AC-5, AC-6
- **Test Requirements**:
  - programmatic: 运行应用，验证所有功能正常
  - human-judgment: 手动测试各种场景，确保用户体验良好

