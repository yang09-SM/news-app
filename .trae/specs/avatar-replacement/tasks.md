
# 用户头像替换功能 - 实施计划

## [ ] 任务1: 更新PrefManager添加头像存储
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 在PrefManager中添加头像路径的存储和获取方法
  - 使用内部存储保存头像图片文件
  - 包含saveAvatarPath()和getAvatarPath()方法
- **Acceptance Criteria Addressed**: AC-3
- **Test Requirements**:
  - `programmatic` TR-1.1: 验证头像路径可以正确保存和读取
- **Notes**: 头像保存到应用私有目录，避免权限问题

## [ ] 任务2: 添加必要权限到AndroidManifest
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 添加相机权限(CAMERA)
  - 添加存储权限(READ_MEDIA_IMAGES, READ_EXTERNAL_STORAGE等)
  - 添加FileProvider配置
- **Acceptance Criteria Addressed**: AC-2, AC-3
- **Test Requirements**:
  - `programmatic` TR-2.1: 验证权限声明正确
- **Notes**: 注意Android 13+的权限变化

## [ ] 任务3: 修改个人中心布局添加头像点击
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 修改fragment_profile.xml，使用ImageView替代当前的TextView头像
  - 为头像区域添加点击效果（水波纹）
  - 添加更换头像的提示图标或文字
- **Acceptance Criteria Addressed**: AC-1
- **Test Requirements**:
  - `human-judgement` TR-3.1: 头像区域有视觉反馈
- **Notes**: 保持与现有设计风格一致

## [ ] 任务4: 实现ProfileFragment中的头像替换逻辑
- **Priority**: P0
- **Depends On**: 任务1, 任务2, 任务3
- **Description**: 
  - 实现头像点击事件处理
  - 弹出选择对话框（拍照/相册）
  - 实现ActivityResultContracts处理图片选择
  - 实现图片压缩和保存
  - 加载并显示头像
- **Acceptance Criteria Addressed**: AC-1, AC-2, AC-3, AC-4
- **Test Requirements**:
  - `programmatic` TR-4.1: 验证可以从相册选择并显示
  - `programmatic` TR-4.2: 验证可以拍照并显示
  - `human-judgement` TR-4.3: 用户体验流畅
- **Notes**: 使用registerForActivityResult处理新的Activity Result API
