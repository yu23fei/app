package com.paisi.yufei.yuyu.utils

import android.os.SystemClock
import android.view.View
import java.util.concurrent.ConcurrentHashMap

/**
 * 点击防抖工具类
 * 使用Kotlin扩展函数实现View的防抖点击
 */
object ClickUtils {

    private const val DEFAULT_CLICK_INTERVAL = 1000L // 默认防抖时间1秒
    private val lastClickTimes = ConcurrentHashMap<View, Long>()

    /**
     * 设置防抖点击监听器
     * @param interval 防抖间隔，单位毫秒
     * @param action 点击回调
     */
    fun View.setDebouncedOnClickListener(interval: Long = DEFAULT_CLICK_INTERVAL, action: (View) -> Unit) {
        this.setOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()
            val previousTime = lastClickTimes[this] ?: 0L
            if (currentTime - previousTime > interval) {
                lastClickTimes[this] = currentTime
                action(it)
            }
        }
    }
}

/**
 * View 扩展函数：设置防抖点击监听器
 */
fun View.onDebounceClick(interval: Long = 1000L, action: (View) -> Unit) {
    ClickUtils.setDebouncedOnClickListener(this, interval, action)
}