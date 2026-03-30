package com.paisi.yufei.yuyu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.paisi.yufei.yuyu.utils.SPUtils
import com.paisi.yufei.yuyu.utils.ToastUtils

/**
 * 启动页 Activity
 * 实现2秒延迟跳转，根据登录状态判断跳转目标
 */
class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        // 安装启动屏
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 延迟2秒后执行跳转逻辑
        handler.postDelayed({
            navigateToTarget()
        }, 2000)
    }

    /**
     * 根据用户登录状态导航到不同页面
     */
    private fun navigateToTarget() {
        try {
            val isLoggedIn = SPUtils.isLoggedIn()
            val targetIntent = if (isLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(targetIntent)
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
            ToastUtils.showShort(this, "应用启动失败，请重试")
            finish()
        }
    }

    override fun onBackPressed() {
        // 禁用返回键
        return
    }
}