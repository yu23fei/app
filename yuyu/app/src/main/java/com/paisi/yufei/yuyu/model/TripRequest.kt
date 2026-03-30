package com.paisi.yufei.yuyu.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * 行程生成请求模型
 * 包含用户输入的所有行程参数
 */
@Parcelize
data class TripRequest(
    val destination: String, // 目的地
    val days: Int, // 出行天数
    val budget: Float, // 人均预算
    val earlyBirdMode: Boolean, // 是否开启早八模式
    val startDate: Date, // 出发日期
    val preferences: List<String> = emptyList() // 出行偏好
) : Parcelable