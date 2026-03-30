package com.paisi.yufei.yuyu.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.viewModels
import androidx.navigation.fragment.findNavController
import com.paisi.yufei.yuyu.R
import com.paisi.yufei.yuyu.databinding.FragmentHomeBinding
import com.paisi.yufei.yuyu.model.TripRequest
import com.paisi.yufei.yuyu.utils.ClickUtils
import com.paisi.yufei.yuyu.utils.ToastUtils
import com.paisi.yufei.yuyu.viewmodel.TripViewModel
import java.util.*

/**
 * 首页 Fragment
 * 行程生成的核心入口，包含完整的表单和生成逻辑
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val tripViewModel: TripViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        // 快捷填充按钮点击事件
        binding.btnFillChongqing.onDebounceClick {
            binding.etDestination.setText("重庆")
        }
        binding.btnFillChengdu.onDebounceClick {
            binding.etDestination.setText("成都")
        }
        binding.btnFillXian.onDebounceClick {
            binding.etDestination.setText("西安")
        }

        // 生成行程按钮点击
        binding.btnGenerateTrip.onDebounceClick {
            generateTrip()
        }
    }

    /**
     * 生成行程
     */
    private fun generateTrip() {
        val destination = binding.etDestination.text?.toString()?.trim()
        val daysText = binding.etDays.text?.toString()?.trim()
        val budgetText = binding.etBudget.text?.toString()?.trim()

        // 表单校验
        if (destination.isNullOrEmpty()) {
            ToastUtils.showShort(requireContext(), "请输入目的地")
            return
        }
        if (daysText.isNullOrEmpty()) {
            ToastUtils.showShort(requireContext(), "请输入出行天数")
            return
        }
        if (budgetText.isNullOrEmpty()) {
            ToastUtils.showShort(requireContext(), "请输入人均预算")
            return
        }

        val days = try {
            daysText.toInt()
        } catch (e: NumberFormatException) {
            ToastUtils.showShort(requireContext(), "出行天数必须为数字")
            return
        }

        val budget = try {
            budgetText.toFloat()
        } catch (e: NumberFormatException) {
            ToastUtils.showShort(requireContext(), "人均预算必须为数字")
            return
        }

        if (days <= 0) {
            ToastUtils.showShort(requireContext(), "出行天数必须大于0")
            return
        }

        if (budget <= 0) {
            ToastUtils.showShort(requireContext(), "人均预算必须大于0")
            return
        }

        // 获取选中的偏好
        val selectedPreferences = mutableListOf<String>()
        binding.chipGroupPreferences.checkedChipIds.forEach { chipId ->
            when (chipId) {
                R.id.chip_foodie -> selectedPreferences.add(getString(R.string.pref_foodie))
                R.id.chip_photo -> selectedPreferences.add(getString(R.string.pref_photo))
                R.id.chip_history -> selectedPreferences.add(getString(R.string.pref_history))
                R.id.chip_nature -> selectedPreferences.add(getString(R.string.pref_nature))
                R.id.chip_shopping -> selectedPreferences.add(getString(R.string.pref_shopping))
                R.id.chip_relax -> selectedPreferences.add(getString(R.string.pref_relax))
                R.id.chip_nightlife -> selectedPreferences.add(getString(R.string.pref_nightlife))
            }
        }

        // 创建请求对象并生成行程
        val request = TripRequest(
            destination = destination,
            days = days,
            budget = budget,
            earlyBirdMode = binding.switchEarlyBird.isChecked,
            startDate = Date(System.currentTimeMillis()),
            preferences = selectedPreferences
        )

        tripViewModel.generateTrip(request)
        ToastUtils.showShort(requireContext(), R.string.toast_loading)
    }

    /**
     * 观察ViewModel数据变化
     */
    private fun observeViewModel() {
        tripViewModel.generatedTrip.observe(viewLifecycleOwner) { trip ->
            if (trip != null) {
                // 跳转到行程详情页
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToTripDetailActivity(trip)
                )
                ToastUtils.showShort(requireContext(), R.string.toast_trip_generated)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}