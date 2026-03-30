package com.paisi.yufei.yuyu

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.paisi.yufei.yuyu.databinding.ActivityTripDetailBinding
import com.paisi.yufei.yuyu.model.Trip
import com.paisi.yufei.yuyu.ui.trip.DailyTripFragment
import com.paisi.yufei.yuyu.utils.ClickUtils
import com.paisi.yufei.yuyu.utils.ToastUtils
import com.paisi.yufei.yuyu.viewmodel.TripViewModel
import kotlinx.parcelize.Parcelize
import kotlinx.coroutines.launch

/**
 * 行程详情 Activity
 * 展示完整的行程安排，包含ViewPager2+TabLayout展示每日行程
 */
class TripDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTripDetailBinding
    private val tripViewModel: TripViewModel by viewModels()

    private lateinit var trip: Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 获取传递的行程数据
        trip = intent.getParcelableExtra("trip") ?: run {
            ToastUtils.showShort(this, "行程数据异常")
            finish()
            return
        }

        setupToolbar()
        setupViewPager()
        setupFab()
    }

    /**
     * 设置工具栏
     */
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    /**
     * 设置ViewPager2和TabLayout
     */
    private fun setupViewPager() {
        val adapter = object : androidx.viewpager2.adapter.FragmentStateAdapter(this) {
            override fun getItemCount(): Int = trip.dailyTrips.size

            override fun createFragment(position: Int): Fragment {
                return DailyTripFragment.newInstance(trip.dailyTrips[position])
            }
        }
        binding.viewPager.adapter = adapter

        // 将TabLayout与ViewPager2关联
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(R.string.day_format, position + 1)
        }.attach()
    }

    /**
     * 设置悬浮按钮
     */
    private fun setupFab() {
        binding.fabAdjustTrip.onDebounceClick {
            val intent = Intent(this, AdjustTripActivity::class.java)
            intent.putExtra("original_trip", trip)
            startActivity(intent)
        }
    }
}