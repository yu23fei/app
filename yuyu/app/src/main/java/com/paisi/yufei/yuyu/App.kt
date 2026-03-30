package com.paisi.yufei.yuyu

import android.app.Application
import com.paisi.yufei.yuyu.utils.SPUtils

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // 初始化工具类
        SPUtils.init(this)
    }
}
