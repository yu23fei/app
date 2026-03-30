package com.paisi.yufei.yuyu.ui.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class HealthViewModel : ViewModel() {

    private val _steps = MutableLiveData<Int>()
    val steps: LiveData<Int> = _steps

    private val _waterIntake = MutableLiveData<Int>()
    val waterIntake: LiveData<Int> = _waterIntake

    private val _sleepHours = MutableLiveData<Float>()
    val sleepHours: LiveData<Float> = _sleepHours

    private val _exerciseMinutes = MutableLiveData<Int>()
    val exerciseMinutes: LiveData<Int> = _exerciseMinutes

    private val _healthScore = MutableLiveData<Int>()
    val healthScore: LiveData<Int> = _healthScore

    private val _airQuality = MutableLiveData<String>()
    val airQuality: LiveData<String> = _airQuality

    private val _checkInSuccess = MutableLiveData<Boolean>()
    val checkInSuccess: LiveData<Boolean> = _checkInSuccess

    val dailyStepGoal = 10000
    val dailyWaterGoal = 2000
    val dailySleepGoal = 8f
    val dailyExerciseGoal = 60

    fun loadHealthData() {
        _steps.value = Random.nextInt(3000, 12000)
        _waterIntake.value = Random.nextInt(500, 2500)
        _sleepHours.value = Random.nextFloat() * 3 + 6
        _exerciseMinutes.value = Random.nextInt(10, 90)
        _healthScore.value = Random.nextInt(70, 98)
        _airQuality.value = listOf("优", "良", "轻度污染")[Random.nextInt(3)]
    }

    fun checkIn() {
        _checkInSuccess.value = true
    }

    fun updateSteps(newSteps: Int) {
        _steps.value = newSteps
        calculateHealthScore()
    }

    fun updateWaterIntake(newWater: Int) {
        _waterIntake.value = newWater
        calculateHealthScore()
    }

    fun updateSleepHours(newHours: Float) {
        _sleepHours.value = newHours
        calculateHealthScore()
    }

    fun updateExerciseMinutes(newMinutes: Int) {
        _exerciseMinutes.value = newMinutes
        calculateHealthScore()
    }

    private fun calculateHealthScore() {
        val stepScore = ((steps.value ?: 0) * 100 / dailyStepGoal).coerceAtMost(100)
        val waterScore = ((waterIntake.value ?: 0) * 100 / dailyWaterGoal).coerceAtMost(100)
        val sleepScore = ((sleepHours.value ?: 0f) * 100 / dailySleepGoal).coerceAtMost(100).toInt()
        val exerciseScore = ((exerciseMinutes.value ?: 0) * 100 / dailyExerciseGoal).coerceAtMost(100)

        val totalScore = (stepScore + waterScore + sleepScore + exerciseScore) / 4
        _healthScore.value = totalScore
    }
}
