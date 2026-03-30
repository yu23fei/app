package com.paisi.yufei.yuyu.viewmodel

import androidx.lifecycle.*
import com.paisi.yufei.yuyu.model.Spot
import com.paisi.yufei.yuyu.model.Trip
import com.paisi.yufei.yuyu.model.TripRequest
import com.paisi.yufei.yuyu.utils.MockDataUtils
import com.paisi.yufei.yuyu.utils.SPUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 行程业务 ViewModel
 * 处理所有与行程相关的业务逻辑
 */
class TripViewModel : ViewModel() {

    //region === 私有 MutableLiveData ===

    private val _generatedTrip = MutableLiveData<Trip>()
    private val _replaceSpotResult = MutableLiveData<Pair<Spot, Spot>>()
    private val _adjustTripResult = MutableLiveData<Pair<Trip, Trip>>()
    private val _myTripsList = MutableLiveData<List<Trip>>(emptyList())

    //endregion

    //region === 公开的 LiveData ===

    /**
     * 生成的行程结果
     */
    val generatedTrip: LiveData<Trip> = _generatedTrip

    /**
     * 景点替换结果
     */
    val replaceSpotResult: LiveData<Pair<Spot, Spot>> = _replaceSpotResult

    /**
     * 行程调整结果（原行程与新行程对比）
     */
    val adjustTripResult: LiveData<Pair<Trip, Trip>> = _adjustTripResult

    /**
     * 我的行程列表
     */
    val myTripsList: LiveData<List<Trip>> = _myTripsList

    //endregion

    init {
        loadMyTrips()
    }

    //region === 行程生成相关 ===

    /**
     * 生成行程
     * 在IO协程中调用MockDataUtils模拟AI生成
     */
    fun generateTrip(request: TripRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val trip = MockDataUtils.generateTrip(request)
                _generatedTrip.postValue(trip)
                // 保存到本地
                SPUtils.saveTripToMyTrips(trip)
                // 刷新我的行程列表
                loadMyTrips()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    //endregion

    //region === 景点替换相关 ===

    /**
     * 替换行程中的某个景点
     * @param originalTrip 原始行程
     * @param dayIndex 第几天
     * @param spotIndex 景点索引
     * @param newSpot 新景点
     */
    fun replaceSpotInTrip(originalTrip: Trip, dayIndex: Int, spotIndex: Int, newSpot: Spot) {
        try {
            val dailyTrips = originalTrip.dailyTrips.toMutableList()
            val targetDay = dailyTrips[dayIndex].spots.toMutableList()
            val oldSpot = targetDay[spotIndex]
            targetDay[spotIndex] = newSpot
            dailyTrips[dayIndex] = dailyTrips[dayIndex].copy(spots = targetDay)

            val updatedTrip = originalTrip.copy(dailyTrips = dailyTrips)
            _replaceSpotResult.value = Pair(oldSpot, newSpot)

            // 更新原始行程和我的行程列表
            SPUtils.saveTripToMyTrips(updatedTrip)
            loadMyTrips()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //endregion

    //region === 行程调整相关 ===

    /**
     * 调整行程
     * 在IO协程中调用MockDataUtils模拟AI优化
     */
    fun adjustTrip(originalTrip: Trip, demand: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val adjustedTrip = MockDataUtils.adjustTrip(originalTrip, demand)
                _adjustTripResult.postValue(Pair(originalTrip, adjustedTrip))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    //endregion

    //region === 我的行程管理相关 ===

    /**
     * 加载我的行程列表
     */
    private fun loadMyTrips() {
        try {
            val trips = SPUtils.getMyTrips()
            _myTripsList.postValue(trips)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 删除指定行程
     */
    fun deleteTrip(tripId: String): Boolean {
        return try {
            val deleted = SPUtils.deleteTripFromMyTrips(tripId)
            if (deleted) {
                loadMyTrips() // 刷新列表
            }
            deleted
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    //endregion
}