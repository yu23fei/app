package com.paisi.yufei.yuyu.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 用户模型
 * 包含用户的基本信息
 */
@Parcelize
data class User(
    val id: String, // 用户ID
    val phone: String, // 手机号
    val nickname: String, // 昵称
    val avatar: String = "", // 头像URL
    val isGuest: Boolean = false // 是否为游客模式
) : Parcelable