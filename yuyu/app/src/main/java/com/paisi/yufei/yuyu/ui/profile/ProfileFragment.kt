package com.paisi.yufei.yuyu.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.viewModels
import androidx.lifecycle.lifecycleScope
import com.paisi.yufei.yuyu.LoginActivity
import com.paisi.yufei.yuyu.R
import com.paisi.yufei.yuyu.databinding.FragmentProfileBinding
import com.paisi.yufei.yuyu.model.User
import com.paisi.yufei.yuyu.utils.ClickUtils
import com.paisi.yufei.yuyu.utils.SPUtils
import com.paisi.yufei.yuyu.utils.ToastUtils
import com.paisi.yufei.yuyu.viewmodel.UserViewModel
import kotlinx.coroutines.launch

/**
 * 个人中心 Fragment
 * 展示用户信息、功能入口和退出登录
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        // 我的行程点击事件
        binding.btnMyTrips.onDebounceClick {
            // TODO: 跳转到我的行程页面
            ToastUtils.showShort(requireContext(), "我的行程")
        }

        // 设置点击事件
        binding.btnSettings.onDebounceClick {
            // TODO: 跳转到设置页面
            ToastUtils.showShort(requireContext(), "设置")
        }

        // 退出登录点击事件
        binding.btnLogout.onDebounceClick {
            showLogoutConfirmation()
        }

        // 切换账号点击事件
        binding.btnSwitchAccount.onDebounceClick {
            requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    /**
     * 观察用户信息数据变化
     */
    private fun observeData() {
        userViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            updateUserInfo(user)
        }
    }

    /**
     * 更新用户信息UI
     */
    private fun updateUserInfo(user: User) {
        binding.tvNickname.text = user.nickname
        // 根据是否为游客模式更新类型标签
        if (user.isGuest) {
            binding.tvUserType.text = getString(R.string.guest_mode)
        } else {
            binding.tvUserType.text = getString(R.string.registered_user)
        }
    }

    /**
     * 显示退出登录确认对话框
     */
    private fun showLogoutConfirmation() {
        lifecycleScope.launch {
            val confirmed = android.app.AlertDialog.Builder(requireContext())
                .setTitle(R.string.btn_logout)
                .setMessage(getString(R.string.confirm_logout))
                .setPositiveButton(android.R.string.ok) { _, _ -> true }
                .setNegativeButton(android.R.string.cancel) { _, _ -> false }
                .show()
                .isShowing

            if (confirmed) {
                SPUtils.clearAll()
                requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}