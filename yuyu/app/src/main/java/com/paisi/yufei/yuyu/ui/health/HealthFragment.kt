package com.paisi.yufei.yuyu.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paisi.yufei.yuyu.databinding.FragmentHealthBinding
import com.paisi.yufei.yuyu.utils.ClickUtils.onDebounceClick
import com.paisi.yufei.yuyu.utils.ToastUtils

class HealthFragment : Fragment() {

    private var _binding: FragmentHealthBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HealthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
        viewModel.loadHealthData()
    }

    private fun setupViews() {
        binding.btnRefresh.onDebounceClick {
            viewModel.loadHealthData()
            ToastUtils.showShort(requireContext(), "刷新健康数据...")
        }

        binding.cardPedometer.onDebounceClick {
            ToastUtils.showShort(requireContext(), "计步详情")
        }

        binding.cardWater.onDebounceClick {
            ToastUtils.showShort(requireContext(), "饮水记录")
        }

        binding.cardSleep.onDebounceClick {
            ToastUtils.showShort(requireContext(), "睡眠分析")
        }

        binding.cardExercise.onDebounceClick {
            ToastUtils.showShort(requireContext(), "运动打卡")
        }

        binding.btnCheckIn.onDebounceClick {
            viewModel.checkIn()
            ToastUtils.showShort(requireContext(), "打卡成功！")
        }

        binding.btnMapNavigate.onDebounceClick {
            ToastUtils.showShort(requireContext(), "打开百度地图导航...")
        }
    }

    private fun observeViewModel() {
        viewModel.steps.observe(viewLifecycleOwner) { steps ->
            binding.tvStepsCount.text = steps.toString()
            binding.progressSteps.progress = (steps * 100 / viewModel.dailyStepGoal).coerceAtMost(100)
        }

        viewModel.waterIntake.observe(viewLifecycleOwner) { water ->
            binding.tvWaterCount.text = "${water}ml"
            binding.progressWater.progress = (water * 100 / viewModel.dailyWaterGoal).coerceAtMost(100)
        }

        viewModel.sleepHours.observe(viewLifecycleOwner) { hours ->
            binding.tvSleepCount.text = "${hours}h"
            binding.progressSleep.progress = (hours * 100 / viewModel.dailySleepGoal).coerceAtMost(100)
        }

        viewModel.exerciseMinutes.observe(viewLifecycleOwner) { minutes ->
            binding.tvExerciseCount.text = "${minutes}min"
            binding.progressExercise.progress = (minutes * 100 / viewModel.dailyExerciseGoal).coerceAtMost(100)
        }

        viewModel.healthScore.observe(viewLifecycleOwner) { score ->
            binding.tvHealthScore.text = score.toString()
        }

        viewModel.airQuality.observe(viewLifecycleOwner) { quality ->
            binding.tvAirQuality.text = quality
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
