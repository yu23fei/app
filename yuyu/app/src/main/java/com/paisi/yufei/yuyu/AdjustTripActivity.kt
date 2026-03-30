package com.paisi.yufei.yuyu

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.paisi.yufei.yuyu.databinding.ActivityAdjustTripBinding
import com.paisi.yufei.yuyu.model.Trip
import com.paisi.yufei.yuyu.utils.ClickUtils
import com.paisi.yufei.yuyu.utils.ToastUtils
import com.paisi.yufei.yuyu.utils.onDebounceClick
import com.paisi.yufei.yuyu.viewmodel.TripViewModel

/**
 * 全行程调整 Activity
 * 提供快捷需求选择和自定义输入，生成调整方案
 */
class AdjustTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdjustTripBinding
    private val tripViewModel: TripViewModel by viewModels()

    private lateinit var originalTrip: Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdjustTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 获取传递的原始行程
        originalTrip = intent.getParcelableExtra("original_trip") ?: run {
            ToastUtils.showShort(this, "原始行程数据异常")
            finish()
            return
        }

        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        // 快捷需求点击事件
        binding.chipLessMoney.setOnClickListener {
            binding.etDemand.setText(getString(R.string.chip_less_money))
        }
        binding.chipMoreFood.setOnClickListener {
            binding.etDemand.setText(getString(R.string.chip_more_food))
        }
        binding.chipLessTiring.setOnClickListener {
            binding.etDemand.setText(getString(R.string.chip_less_tiring))
        }
        binding.chipNightLife.setOnClickListener {
            binding.etDemand.setText(getString(R.string.chip_night_life))
        }

        // 生成调整方案按钮点击
        binding.btnGenerateAdjust.onDebounceClick {
            generateAdjustment()
        }
    }

    /**
     * 生成调整方案
     */
    private fun generateAdjustment() {
        val demand = binding.etDemand.text?.toString()?.trim() ?: ""

        if (demand.isEmpty()) {
            ToastUtils.showShort(this, "请输入调整需求")
            return
        }

        // 调用ViewModel生成调整方案
        tripViewModel.adjustTrip(originalTrip, demand)
        ToastUtils.showShort(this, R.string.toast_loading.toString())
    }

    /**
     * 观察ViewModel数据变化
     */
    private fun observeViewModel() {
        tripViewModel.adjustTripResult.observe(this) { pair ->
            if (pair != null) {
                // 跳转到对比页面
                val intent = Intent(this, AdjustCompareActivity::class.java)
                intent.putExtra("original_trip", pair.first)
                intent.putExtra("adjusted_trip", pair.second)
                startActivity(intent)
                finish()
            }
        }
    }
}