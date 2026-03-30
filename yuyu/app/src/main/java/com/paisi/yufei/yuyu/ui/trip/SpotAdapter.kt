package com.paisi.yufei.yuyu.ui.trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paisi.yufei.yuyu.databinding.ItemSpotBinding
import com.paisi.yufei.yuyu.model.Spot

/**
 * 景点列表适配器
 * 用于展示行程中的单个景点，包含名称、地址、类型和操作按钮
 */
class SpotAdapter(
    private var spots: MutableList<Spot>,
    private val onNavigateClick: (Spot) -> Unit,
    private val onReplaceClick: (Spot) -> Unit
) : RecyclerView.Adapter<SpotAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSpotBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
        holder.binding.tvName.text = spot.name
        holder.binding.tvAddress.text = spot.address
        holder.binding.tvType.text = spot.type
        holder.binding.tvDuration.text = holder.itemView.context.getString(R.string.duration_format, spot.duration)

        // 导航按钮点击事件
        holder.binding.btnNavigate.setOnClickListener {
            onNavigateClick(spot)
        }

        // 替换按钮点击事件
        holder.binding.btnReplaceSpot.setOnClickListener {
            onReplaceClick(spot)
        }
    }

    override fun getItemCount(): Int = spots.size

    /**
     * 更新数据
     */
    fun updateData(newSpots: MutableList<Spot>) {
        this.spots.clear()
        this.spots.addAll(newSpots)
        notifyDataSetChanged()
    }
}