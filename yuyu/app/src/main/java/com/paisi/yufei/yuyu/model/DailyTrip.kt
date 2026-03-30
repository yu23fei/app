package com.paisi.yufei.yuyu.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * 单日行程模型
 * 包含某一天的完整行程安排
 */
@Parcelize
data class DailyTrip(
    val day: Int, // 第几天
    val date: Date, // 日期
    val spots: List<Spot>, // 景点列表
    val dailyCost: Float = 0f, // 当日总花费
    val commuteDistance: Float = 0f // 通勤距离，单位km
) : Parcelable