package com.paisi.yufei.yuyu.viewmodel

import androidx.lifecycle.*
import com.paisi.yufei.yuyu.model.User
import com.paisi.yufei.yuyu.utils.SPUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * 用户信息 ViewModel
 * 处理所有与用户相关的业务逻辑
 */
class UserViewModel : ViewModel() {

    //region === 私有 MutableLiveData ===

    private val _isLoggedIn = MutableLiveData<Boolean>()
    private val _currentUser = MutableLiveData<User?>()

    //endregion

    //region === 公开的 LiveData ===


    /**
     * 登录状态
     */
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    /**
     * 当前用户信息
     */
    val currentUser: LiveData<User?> = _currentUser

    //endregion

    init {
        loadLoginState()
        loadCurrentUser()
    }

    //region === 登录注册相关 ===

    /**
     * 游客登录
     */
    fun loginAsGuest() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val guestUser = User(
                    id = "guest_${UUID.randomUUID()}",
                    phone = "",
                    nickname = "游客${System.currentTimeMillis() % 10000}",
                    isGuest = true
                )
                SPUtils.saveUserInfo(guestUser)
                SPUtils.setLoggedIn(true)
                SPUtils.setGuestMode(true)

                // 更新LiveData
                _isLoggedIn.postValue(true)
                _currentUser.postValue(guestUser)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 手机号登录
     * @param phone 手机号
     * @param code 验证码（默认123456）
     */
    fun loginWithPhone(phone: String, code: String): Boolean {
        return if (phone.isNotEmpty() && android.util.Patterns.PHONE.matcher(phone).matches() && code == "123456") {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val user = User(
                        id = phone,
                        phone = phone,
                        nickname = "用户${phone.takeLast(4)}",
                        isGuest = false
                    )
                    SPUtils.saveUserInfo(user)
                    SPUtils.setLoggedIn(true)
                    SPUtils.setGuestMode(false)

                    // 更新LiveData
                    _isLoggedIn.postValue(true)
                    _currentUser.postValue(user)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            true
        } else {
            false
        }
    }
    //endregion

    //region === 退出登录相关 ===

    /**
     * 退出登录
     */
    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                SPUtils.clearUserInfo()
                SPUtils.clearLoginState()

                // 更新LiveData
                _isLoggedIn.postValue(false)
                _currentUser.postValue(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    //endregion

    //region === 数据加载相关 ===

    /**
     * 加载登录状态
     */
    private fun loadLoginState() {
        _isLoggedIn.value = SPUtils.isLoggedIn()
    }

    /**
     * 加载当前用户信息
     */
    private fun loadCurrentUser() {
        _currentUser.value = SPUtils.getUserInfo()
    }
    //endregion
}