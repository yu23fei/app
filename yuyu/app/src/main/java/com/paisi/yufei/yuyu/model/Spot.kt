package com.paisi.yufei.yuyu.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 景点模型
 * 包含景点的详细信息
 */
@Parcelize
data class Spot(
    val id: String, // 景点ID
    val name: String, // 名称
    val city: String, // 所在城市
    val address: String, // 详细地址
    val latitude: Float, // 纬度
    val longitude: Float, // 经度
    val description: String, // 描述
    val ticketPrice: Double, // 门票价格
    val duration: Int, // 建议游玩时长（分钟）
    val type: String, // 类型（如：地标、美食、历史文化等）
    val tips: String // 大学生专属避坑提示

) : Parcelable