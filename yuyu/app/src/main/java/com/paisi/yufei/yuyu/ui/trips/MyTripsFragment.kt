package com.paisi.yufei.yuyu.ui.trips

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.viewModels
import androidx.lifecycle.lifecycleScope
import com.paisi.yufei.yuyu.R
import com.paisi.yufei.yuyu.TripDetailActivity
import com.paisi.yufei.yuyu.databinding.FragmentMyTripsBinding
import com.paisi.yufei.yuyu.model.Trip
import com.paisi.yufei.yuyu.utils.ClickUtils
import com.paisi.yufei.yuyu.utils.ToastUtils
import com.paisi.yufei.yuyu.viewmodel.TripViewModel
import kotlinx.coroutines.launch

/**
 * 我的行程 Fragment
 * 展示用户的历史行程列表，支持查看详情和删除
 */
class MyTripsFragment : Fragment() {

    private var _binding: FragmentMyTripsBinding? = null
    private val binding get() = _binding!!

    private val tripViewModel: TripViewModel by viewModels()
    private lateinit var adapter: TripAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyTripsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        // 初始化适配器
        adapter = TripAdapter(mutableListOf()) { trip ->
            // 跳转到行程详情
            val intent = Intent(requireContext(), TripDetailActivity::class.java)
            intent.putExtra("trip", trip)
            startActivity(intent)
        } { trip ->
            // 删除行程
            showDeleteConfirmation(trip)
        }
        binding.rvTrips.adapter = adapter
    }

    /**
     * 观察我的行程列表数据变化
     */
    private fun observeData() {
        tripViewModel.myTripsList.observe(viewLifecycleOwner) { trips ->
            if (trips.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
+                binding.rvTrips.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvTrips.visibility = View.VISIBLE
                adapter.updateData(trips)
            }
        }
    }

    /**
     * 显示删除确认对话框
     */
    private fun showDeleteConfirmation(trip: Trip) {
        lifecycleScope.launch {
            val confirmed = android.app.AlertDialog.Builder(requireContext())
                .setTitle(R.string.btn_delete_trip)
                .setMessage(getString(R.string.confirm_delete_trip))
                .setPositiveButton(android.R.string.ok) { _, _ -> true }
                .setNegativeButton(android.R.string.cancel) { _, _ -> false }
                .show()
                .isShowing

            if (confirmed) {
                val deleted = tripViewModel.deleteTrip(trip.id)
                if (deleted) {
                    ToastUtils.showShort(requireContext(), R.string.toast_operation_success)
                } else {
                    ToastUtils.showShort(requireContext(), R.string.toast_operation_failed)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}