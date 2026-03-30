package com.paisi.yufei.yuyu.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.paisi.yufei.yuyu.model.Trip
import com.paisi.yufei.yuyu.model.User

/**
 * SharedPreferences 工具类
 * 使用单例模式实现数据持久化，结合Gson处理对象序列化
 */
object SPUtils {

    private const val PREF_NAME = "yuyu_pref"
    private const val KEY_USER_INFO = "user_info"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_GUEST_MODE = "guest_mode"
    private const val KEY_MY_TRIPS = "my_trips_list"

    private lateinit var prefs: SharedPreferences
    private val gson by lazy { Gson() }

    /**
     * 初始化方法，需在Application中调用
     */
    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    //region === 用户信息相关 ===

    /**
     * 保存用户信息
     */
    fun saveUserInfo(user: User) {
        try {
            val json = gson.toJson(user)
            prefs.edit { putString(KEY_USER_INFO, json) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): User? {
        return try {
            val json = prefs.getString(KEY_USER_INFO, null)
            if (json.isNullOrEmpty()) null else gson.fromJson(json, User::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 清除用户信息
     */
    fun clearUserInfo() {
        prefs.edit { remove(KEY_USER_INFO) }
    }
    //endregion

    //region === 登录状态相关 ===

    /**
     * 设置登录状态
     */
    fun setLoggedIn(loggedIn: Boolean) {
        prefs.edit { putBoolean(KEY_IS_LOGGED_IN, loggedIn) }
    }

    /**
     * 获取登录状态
     */
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    /**
     * 设置游客模式
     */
    fun setGuestMode(guest: Boolean) {
        prefs.edit { putBoolean(KEY_GUEST_MODE, guest) }
    }

    /**
     * 是否为游客模式
     */
    fun isGuestMode(): Boolean {
        return prefs.getBoolean(KEY_GUEST_MODE, true)
    }

    /**
     * 清除登录状态
     */
    fun clearLoginState() {
        prefs.edit {
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_GUEST_MODE)
        }
    }
    //endregion

    //region === 我的行程相关 ===

    /**
     * 保存行程到我的行程列表
     */
    fun saveTripToMyTrips(trip: Trip) {
        val trips = getMyTrips().toMutableList()
        // 移除已存在的相同ID行程
        trips.removeAll { it.id == trip.id }
        trips.add(0, trip) // 添加到最前面
        saveMyTripsList(trips)
    }

    /**
     * 获取我的行程列表
     */
    fun getMyTrips(): List<Trip> {
        return try {
            val json = prefs.getString(KEY_MY_TRIPS, null)
            if (json.isNullOrEmpty()) {
                emptyList()
            } else {
                val type = object : TypeToken<List<Trip>>() {}.type
                gson.fromJson(json, type)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * 从我的行程列表中删除指定行程
     */
    fun deleteTripFromMyTrips(tripId: String): Boolean {
        val trips = getMyTrips().toMutableList()
        val removed = trips.removeIf { it.id == tripId }
        if (removed) {
            saveMyTripsList(trips)
        }
        return removed
    }

    /**
     * 保存完整的行程列表
     */
    private fun saveMyTripsList(trips: List<Trip>) {
        try {
            val json = gson.toJson(trips)
            prefs.edit { putString(KEY_MY_TRIPS, json) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //endregion
}