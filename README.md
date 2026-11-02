# 行野 AI（yuyu）

一个基于 Kotlin 的 Android 旅游行程管理应用，提供行程规划、每日行程调整、景点管理、健康数据记录与探索推荐等功能。

## 功能特性

- **行程管理**：创建、查看、调整旅行行程，支持按天拆分每日行程
- **行程对比**：对比不同行程方案，灵活替换景点
- **景点管理**：景点信息展示与行程内景点编排
- **健康模块**：步数、睡眠、饮水、运动等健康数据记录与统计
- **探索与推荐**：发现旅行目的地与相关内容
- **个人中心**：用户信息、偏好设置管理

## 技术栈

- **语言**：Kotlin
- **UI 框架**：Android View + Fragment + RecyclerView
- **构建工具**：Gradle（Kotlin DSL + Version Catalog）
- **架构**：MVVM（ViewModel + LiveData）
- **数据**：SharedPreferences 本地持久化（Gson 序列化）
- **最低支持**：Android 8.0（API 26）

## 工程结构

```
yuyu/
├── app/
│   └── src/main/
│       ├── java/com/paisi/yufei/yuyu/
│       │   ├── model/            # 数据模型（Trip、Spot、User 等）
│       │   ├── viewmodel/        # ViewModel 层
│       │   ├── ui/               # 界面（Fragment + Adapter）
│       │   │   ├── trip/         # 行程相关界面
│       │   │   ├── trips/        # 行程列表
│       │   │   ├── explore/      # 探索
│       │   │   ├── health/       # 健康
│       │   │   ├── home/         # 首页
│       │   │   └── profile/      # 个人中心
│       │   ├── dialog/           # 弹窗组件
│       │   ├── utils/            # 工具类
│       │   └── MainActivity.kt   # 主入口
│       └── res/                  # 资源文件
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/                       # Gradle 配置
```

## 构建与运行

### 环境要求

- Android Studio 最新稳定版
- JDK 17+
- Android SDK（compileSdk 35）

### 构建步骤

1. 使用 Android Studio 打开项目根目录 `yuyu/`
2. 等待 Gradle 同步完成
3. 连接设备或启动模拟器，点击 Run 运行

或使用命令行构建：

```bash
cd yuyu
./gradlew assembleDebug
```

生成的 APK 位于 `app/build/outputs/apk/debug/`。

## 代码规范

本项目遵循阿里巴巴 Java 开发规范及 Google Android 开发规范：

- 使用 MVVM 架构分层，View 层不直接操作数据
- 统一使用 ViewModel 管理 UI 状态
- 遵循 Kotlin 官方代码风格与命名规范

## 许可证

本项目采用 [MIT License](LICENSE) 开源。
