// 项目配置文件 - settings.gradle.kts
// 配置模块、仓库和构建选项

pluginManagement {
    repositories {
        // 优先使用阿里云镜像
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/public") } // public 包含 central
        // 保留官方源作为兜底（可选，阿里云不稳定时启用）
        // google()
        // mavenCentral()
        // gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 优先使用阿里云镜像
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/public") } // public 包含 central
        // 高德地图 SDK 需保留 jitpack（阿里云无此镜像）
        maven { url = uri("https://jitpack.io") }
        // 保留官方源作为兜底（可选）
        // google()
        // mavenCentral()
    }
}

rootProject.name = "yuyu"
include(":app")