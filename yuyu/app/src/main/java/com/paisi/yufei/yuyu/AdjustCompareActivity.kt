package com.paisi.yufei.yuyu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paisi.yufei.yuyu.databinding.ActivityAdjustCompareBinding
import com.paisi.yufei.yuyu.model.Trip
import com.paisi.yufei.yuyu.utils.ToastUtils

/**
 * 调整方案对比 Activity
 * 展示调整前后的行程方案，并提供应用或取消选项
 */
class AdjustCompareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdjustCompareBinding

    private lateinit var originalTrip: Trip
    private lateinit var adjustedTrip: Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdjustCompareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 获取传递的行程数据
        originalTrip = intent.getParcelableExtra("original_trip") ?: run {
            finish()
            return
        }
        adjustedTrip = intent.getParcelableExtra("adjusted_trip") ?: run {
            finish()
            return
        }

        setupViews()
    }

    private fun setupViews() {
        // 计算变化值
        val budgetChange = adjustedTrip.budget - originalTrip.budget
        val daysChange = adjustedTrip.days - originalTrip.days
        val spotChange = adjustedTrip.dailyTrips.sumOf { it.spots.size } - originalTrip.dailyTrips.sumOf { it.spots.size }


        // 更新UI
        binding.tvChangeBudget.text = getString(R.string.change_budget, budgetChange)
        binding.tvChangeDays.text = getString(R.string.change_days, daysChange)
        binding.tvChangeSpots.text = getString(R.string.change_spots, spotChange)

        binding.tvChangeBudgetNew.text = getString(R.string.change_budget, -budgetChange) // 反向显示
        binding.tvChangeDaysNew.text = getString(R.string.change_days, -daysChange)
        binding.tvChangeSpotsNew.text = getString(R.string.change_spots, -spotChange)

        // 按钮点击事件
        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnApply.setOnClickListener {
            // TODO: 将新行程保存到我的行程列表，并返回主页面
            ToastUtils.showShort(this, R.string.toast_operation_success.toString())
            setResult(RESULT_OK)
            finish()
        }
    }
}