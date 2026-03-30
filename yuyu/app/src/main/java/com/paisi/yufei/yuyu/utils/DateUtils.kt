package com.paisi.yufei.yuyu.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * 日期时间工具类
 * 使用Kotlin扩展函数实现日期格式化、解析和计算
 */
object DateUtils {

    private val defaultFormat by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }
    private val dateOnlyFormat by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    private val timeOnlyFormat by lazy { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    /**
     * 将时间戳转换为指定格式的字符串
     */
    fun formatTimestamp(timestamp: Long, format: SimpleDateFormat = defaultFormat): String {
        return try {
            format.format(Date(timestamp))
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * 将日期对象转换为指定格式的字符串
     */
    fun formatDate(date: Date, format: SimpleDateFormat = defaultFormat): String {
        return try {
            format.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * 将字符串解析为日期对象
     */
    fun parseDate(dateStr: String, format: SimpleDateFormat = defaultFormat): Date? {
        return try {
            format.parse(dateStr)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 计算两个日期之间的天数差
     */
    fun daysBetween(startDate: Date, endDate: Date): Int {
        val start = startDate.time / (1000 * 60 * 60 * 24)
        val end = endDate.time / (1000 * 60 * 60 * 24)
        return (end - start).toInt()
    }

    /**
     * 获取当前时间戳
     */
    fun getCurrentTimestamp(): Long {
        return System.currentTimeMillis()
    }
}

/**
 * Long 扩展函数：转换为格式化日期字符串
 */
fun Long.toFormattedDate(format: SimpleDateFormat = DateUtils.defaultFormat): String {
    return DateUtils.formatTimestamp(this, format)
}

/**
 * Date 扩展函数：转换为格式化日期字符串
 */
fun Date.toFormattedString(format: SimpleDateFormat = DateUtils.defaultFormat): String {
    return DateUtils.formatDate(this, format)
}

/**
 * String 扩展函数：解析为日期对象
 */
fun String.toDate(format: SimpleDateFormat = DateUtils.defaultFormat): Date? {
    return DateUtils.parseDate(this, format)
}