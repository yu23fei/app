package com.paisi.yufei.yuyu.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * 完整行程模型
 * 包含整个旅行计划的所有信息
 */
@Parcelize
data class Trip(
    val id: String, // 行程ID
    val destination: String, // 目的地
    val days: Int, // 出行天数
    val budget: Float, // 总预算
    val startDate: Date, // 出发日期
    val dailyTrips: List<DailyTrip>, // 每日行程列表
    val generatedAt: Long = System.currentTimeMillis() // 生成时间戳
) : Parcelable