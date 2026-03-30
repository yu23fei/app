package com.paisi.yufei.yuyu.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Gson 工具类
 * 使用单例模式管理Gson实例，提供对象与JSON的转换扩展函数
 */
object GsonUtils {

    private val gson = Gson()

    /**
     * 将对象转换为JSON字符串
     */
    fun <T> toJson(obj: T): String? {
        return try {
            gson.toJson(obj)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 将JSON字符串转换为指定类型的对象
     */
    inline fun <reified T> fromJson(json: String?): T? {
        return try {
            if (json.isNullOrEmpty()) null else gson.fromJson(json, T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 将JSON字符串转换为指定类型的集合
     */
    inline fun <reified T> fromJsonList(json: String?): List<T>? {
        return try {
            if (json.isNullOrEmpty()) null else {
                val type = object : TypeToken<List<T>>() {}.type
                gson.fromJson(json, type)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

/**
 * Any 扩展函数：转换为JSON字符串
 */
fun Any?.toJson(): String? {
    return this?.let { GsonUtils.toJson(it) }
}

/**
 * String 扩展函数：转换为指定类型的对象
 */
inline fun <reified T> String?.fromJson(): T? {
    return this?.let { GsonUtils.fromJson<T>(it) }
}

/**
 * String 扩展函数：转换为指定类型的集合
 */
inline fun <reified T> String?.fromJsonList(): List<T>? {
    return this?.let { GsonUtils.fromJsonList<T>(it) }
}