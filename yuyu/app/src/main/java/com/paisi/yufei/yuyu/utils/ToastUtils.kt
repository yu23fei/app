package com.paisi.yufei.yuyu.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Toast 工具类
 * 使用单例模式实现统一的Toast展示，避免重复弹出
 */
object ToastUtils {

    private var toast: Toast? = null

    /**
     * 显示短时Toast
     */
    fun showShort(context: Context, message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    /**
     * 显示长时Toast
     */
    fun showLong(context: Context, message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast?.show()
    }
}

/**
 * Context 扩展函数：显示Toast
 */
fun Context.toastShort(message: String) {
    ToastUtils.showShort(this, message)
}

fun Context.toastLong(message: String) {
    ToastUtils.showLong(this, message)
}

/**
 * Fragment 扩展函数：显示Toast
 */
fun Fragment.toastShort(message: String) {
    context?.let { ToastUtils.showShort(it, message) }
}

fun Fragment.toastLong(message: String) {
    context?.let { ToastUtils.showLong(it, message) }
}