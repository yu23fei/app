package com.paisi.yufei.yuyu.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paisi.yufei.yuyu.R
import com.paisi.yufei.yuyu.databinding.DialogReplaceSpotBinding
import com.paisi.yufei.yuyu.model.Spot
import com.paisi.yufei.yuyu.ui.trip.SpotAdapter
import com.paisi.yufei.yuyu.utils.ClickUtils
import com.paisi.yufei.yuyu.utils.ToastUtils
import kotlinx.coroutines.launch

/**
 * 景点替换弹窗
 * 继承自BottomSheetDialog，提供推荐景点列表和替换功能
 */
class ReplaceSpotDialog : BottomSheetDialogFragment() {

    private var _binding: DialogReplaceSpotBinding? = null
    private val binding get() = _binding!!

    private lateinit var originalSpot: Spot
    private var selectedSpot: Spot? = null
    private lateinit var adapter: SpotAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogReplaceSpotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        loadSpots("recommend") // 默认加载智能推荐
    }

    /**
     * 创建实例并传递原景点
     */
    companion object {
        fun newInstance(originalSpot: Spot): ReplaceSpotDialog {
            val dialog = ReplaceSpotDialog()
            val args = Bundle()
            args.putParcelable("original_spot", originalSpot)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            originalSpot = it.getParcelable("original_spot") ?: return
        }
    }

    private fun setupViews() {
        // 初始化适配器
        adapter = SpotAdapter(mutableListOf()) { spot ->
            selectedSpot = spot
            // 更新所有item的选中状态
            for (i in 0 until adapter.itemCount) {
                val viewHolder = binding.rvSpots.findViewHolderForAdapterPosition(i) as? SpotAdapter.ViewHolder
                viewHolder?.bindIsSelected(spot == selectedSpot)
            }
        }

        // 筛选条件点击事件
        binding.chipRecommend.setOnClickListener {
            loadSpots("recommend")
        }
        binding.chipBudget.setOnClickListener {
            loadSpots("budget")
        }
        binding.chipPopular.setOnClickListener {
            loadSpots("popular")
        }

        // 确认替换按钮点击
        binding.btnConfirmReplace.onDebounceClick {
            if (selectedSpot != null) {
                // TODO: 通知Activity或Fragment进行替换操作
                ToastUtils.showShort(requireContext(), "已选择替换景点：${selectedSpot!!.name}")
                dismiss()
            } else {
                ToastUtils.showShort(requireContext(), "请先选择一个景点")
            }
        }
    }

    /**
     * 加载推荐的替换景点
     */
    private fun loadSpots(filterType: String) {
        lifecycleScope.launch {
            try {
                // 这里调用MockDataUtils获取数据
                val spots = MockDataUtils.getReplaceSpots(originalSpot, filterType)
                adapter.updateData(spots.toMutableList())
            } catch (e: Exception) {
                e.printStackTrace()
                ToastUtils.showShort(requireContext(), "加载推荐景点失败")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}