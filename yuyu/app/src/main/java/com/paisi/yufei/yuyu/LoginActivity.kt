package com.paisi.yufei.yuyu

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.paisi.yufei.yuyu.databinding.ActivityLoginBinding
import com.paisi.yufei.yuyu.utils.ClickUtils
import com.paisi.yufei.yuyu.utils.ToastUtils
import com.paisi.yufei.yuyu.viewmodel.UserViewModel
import com.paisi.yufei.yuyu.utils.*

/**
 * 登录注册 Activity
 * 包含手机号登录、验证码倒计时、游客模式入口
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels()

    // 倒计时器
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        // 手机号输入监听
        binding.etPhone.doOnTextChanged { text, _, _, _ ->
            binding.tilPhone.error = null
        }

        // 验证码输入监听
        binding.etCode.doOnTextChanged { text, _, _, _ ->
            binding.tilCode.error = null
        }

        // 获取验证码按钮点击
        binding.btnGetCode.onDebounceClick {
            if (validatePhoneNumber()) {
                startCountDown()
                ToastUtils.showShort(this, "验证码已发送")
            }
        }

        // 登录按钮点击
        binding.btnLogin.onDebounceClick {
            performLogin()
        }

        // 游客模式点击
        binding.tvGuestMode.onDebounceClick {
            userViewModel.loginAsGuest()
        }

        // 协议点击事件（示例）
        binding.tvTerms.setOnClickListener {
            ToastUtils.showShort(this, "已阅读用户协议与隐私政策")
        }
    }

    /**
     * 验证手机号格式
     */
    private fun validatePhoneNumber(): Boolean {
        val phone = binding.etPhone.text.toString().trim()
        return if (phone.isEmpty()) {
            binding.tilPhone.error = getString(R.string.toast_invalid_phone)
            false
        } else if (!android.util.Patterns.PHONE.matcher(phone).matches()) {
            binding.tilPhone.error = "手机号格式不正确"
            false
        } else {
            true
        }
    }

    /**
     * 开始验证码倒计时
     */
    private fun startCountDown() {
        binding.btnGetCode.isEnabled = false
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.btnGetCode.text = "${millisUntilFinished / 1000}秒后重发"
            }

            override fun onFinish() {
                binding.btnGetCode.text = getString(R.string.btn_get_code)
                binding.btnGetCode.isEnabled = true
            }
        }.start()
    }

    /**
     * 执行登录逻辑
     */
    private fun performLogin() {
        val phone = binding.etPhone.text.toString().trim()
        val code = binding.etCode.text.toString().trim()

        if (!validatePhoneNumber()) return
        if (code.isEmpty()) {
            binding.tilCode.error = "请输入验证码"
            return
        }

        if (userViewModel.loginWithPhone(phone, code)) {
            ToastUtils.showShort(this, R.string.toast_login_success.toString())
            navigateToMain()
        } else {
            ToastUtils.showShort(this, R.string.toast_invalid_code.toString())
        }
    }

    /**
     * 观察ViewModel状态变化
     */
    private fun observeViewModel() {
        userViewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                navigateToMain()
            }
        }
    }

    /**
     * 跳转到主页面
     */
    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}