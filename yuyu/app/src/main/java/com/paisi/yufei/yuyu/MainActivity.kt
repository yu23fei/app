package com.paisi.yufei.yuyu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.paisi.yufei.yuyu.databinding.ActivityMainBinding
import com.paisi.yufei.yuyu.utils.ToastUtils // 确保你有这个工具类，或者换成系统的 Toast

/**
 * 主页面 Activity
 * 作为底部导航的宿主，管理四个Tab的Fragment切换
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    // 双击返回键退出应用的计时变量
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        setupNavigation()
        setupStatusBar()
        setupOnBackPressed() // 初始化新的返回键逻辑
    }

    /**
     * 设置底部导航与NavController的关联
     */
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)
    }

    /**
     * 设置状态栏沉浸式
     */
    private fun setupStatusBar() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.bottomNav.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }
    }

    /**
     * 【修复】使用新的 API 处理双击返回键退出
     */
    private fun setupOnBackPressed() {
        onBackPressedDispatcher.addCallback(this) {
            if (navController.currentDestination?.id == R.id.homeFragment) {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    finish() // 退出应用
                } else {
                    ToastUtils.showShort(this@MainActivity, "再按一次退出应用")
                    backPressedTime = System.currentTimeMillis()
                }
            } else {
                // 如果不在首页，则回退到上一个 Fragment
                navController.navigateUp()
            }
        }
    }
}