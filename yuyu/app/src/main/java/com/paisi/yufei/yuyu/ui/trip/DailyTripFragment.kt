package com.paisi.yufei.yuyu.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.paisi.yufei.yuyu.R
import com.paisi.yufei.yuyu.databinding.FragmentDailyTripBinding
import com.paisi.yufei.yuyu.model.DailyTrip
import com.paisi.yufei.yuyu.model.Spot
import com.paisi.yufei.yuyu.ui.trip.SpotAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * 单日行程 Fragment
 * 展示某一天的完整行程安排，按时间段分组
 */
class DailyTripFragment : Fragment() {

    private var _binding: FragmentDailyTripBinding? = null
    private val binding get() = _binding!!

    private lateinit var dailyTrip: DailyTrip

    // 按时间段分组的景点列表
    private val morningSpots = mutableListOf<Spot>()
    private val afternoonSpots = mutableListOf<Spot>()
    private val eveningSpots = mutableListOf<Spot>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        updateUI()
    }

    /**
     * 创建Fragment实例并传递数据
     */
    companion object {
        fun newInstance(dailyTrip: DailyTrip): DailyTripFragment {
            val fragment = DailyTripFragment()
            val args = Bundle()
            args.putParcelable("daily_trip", dailyTrip)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dailyTrip = it.getParcelable("daily_trip") ?: DailyTrip(1, Date(), emptyList())
        }
    }

    private fun setupViews() {
        // 初始化适配器
        binding.rvMorningSpots.adapter = SpotAdapter(morningSpots) { spot ->
            navigateToMap(spot)
        } { spot ->
            showReplaceDialog(spot)
        }

        binding.rvAfternoonSpots.adapter = SpotAdapter(afternoonSpots) { spot ->
            navigateToMap(spot)
        } { spot ->
            showReplaceDialog(spot)
        }

        binding.rvEveningSpots.adapter = SpotAdapter(eveningSpots) { spot ->
            navigateToMap(spot)
        } { spot ->
            showReplaceDialog(spot)
        }

        // 设置布局管理器
        binding.rvMorningSpots.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAfternoonSpots.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEveningSpots.layoutManager = LinearLayoutManager(requireContext())
    }

    /**
     * 更新UI界面
     */
    private fun updateUI() {
        // 设置标题
        binding.tvDayTitle.text = getString(R.string.day_format, dailyTrip.day)


        // 计算总花费和通勤距离
        val totalCost = dailyTrip.dailyCost
        val commuteDistance = dailyTrip.commuteDistance
        binding.tvTotalCost.text = getString(R.string.total_cost, totalCost)
        binding.tvDistance.text = getString(R.string.distance, commuteDistance)

        // 分组景点（简化处理：按索引模3分组）
        morningSpots.clear()
        afternoonSpots.clear()
        eveningSpots.clear()

        dailyTrip.spots.forEachIndexed { index, spot ->
            when (index % 3) {
                0 -> morningSpots.add(spot)
                1 -> afternoonSpots.add(spot)
                2 -> eveningSpots.add(spot)
            }
        }

        // 通知适配器更新
        binding.rvMorningSpots.adapter?.notifyDataSetChanged()
        binding.rvAfternoonSpots.adapter?.notifyDataSetChanged()
        binding.rvEveningSpots.adapter?.notifyDataSetChanged()
    }

    /**
     * 导航到地图
     */
    private fun navigateToMap(spot: Spot) {
        ToastUtils.showShort(requireContext(), "正在导航至${spot.name}")
        // TODO: 集成高德地图SDK
    }

    /**
     * 显示替换景点弹窗
     */
    private fun showReplaceDialog(spot: Spot) {
        // TODO: 实现替换逻辑
        ReplaceSpotDialog.newInstance(spot).show(parentFragmentManager, "replace_dialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}