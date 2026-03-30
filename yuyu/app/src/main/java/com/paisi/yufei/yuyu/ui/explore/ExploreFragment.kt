package com.paisi.yufei.yuyu.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paisi.yufei.yuyu.R
import com.paisi.yufei.yuyu.databinding.FragmentExploreBinding

/**
 * 探索 Fragment
 * 展示应用的探索功能，目前为占位
 */
class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: 实现探索功能
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}