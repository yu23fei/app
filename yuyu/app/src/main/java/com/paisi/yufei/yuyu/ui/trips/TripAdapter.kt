package com.paisi.yufei.yuyu.ui.trips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paisi.yufei.yuyu.databinding.ItemTripBinding
import com.paisi.yufei.yuyu.model.Trip

/**
 * 行程列表适配器
 * 用于展示用户的历史行程，支持查看详情和删除
 */
class TripAdapter(
    private var trips: MutableList<Trip>,
    private val onItemClick: (Trip) -> Unit,
    private val onDeleteClick: (Trip) -> Unit
) : RecyclerView.Adapter<TripAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemTripBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trip = trips[position]
        holder.binding.tvDestination.text = trip.destination
        holder.binding.tvDays.text = holder.itemView.context.getString(R.string.days_format, trip.days)
        holder.binding.tvBudget.text = holder.itemView.context.getString(R.string.budget_format, trip.budget)
        holder.binding.tvStartDate.text = holder.itemView.context.getString(R.string.start_date_format, DateUtils.formatDate(trip.startDate))
        holder.binding.tvGeneratedAt.text = holder.itemView.context.getString(R.string.generated_at_format, DateUtils.formatDateTime(trip.generatedAt))

        // 条目点击事件
        holder.itemView.setOnClickListener {
            onItemClick(trip)
        }

        // 删除按钮点击事件（可扩展）
        // 这里简化处理，长按删除或通过菜单操作
    }

    override fun getItemCount(): Int = trips.size

    /**
     * 更新数据
     */
    fun updateData(newTrips: MutableList<Trip>) {
        this.trips.clear()
        this.trips.addAll(newTrips)
        notifyDataSetChanged()
    }
}