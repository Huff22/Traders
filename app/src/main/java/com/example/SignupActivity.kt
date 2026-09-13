package com.example

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.databinding.ActivitySignupBinding
import com.example.utils.FeedbackUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
        observeViewModel()
    }

    private fun setupUI() {
        val fullText = "Have an account? SIGN IN"
        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf("SIGN IN")
        if (startIndex != -1) {
            val color = ContextCompat.getColor(this, R.color.secondary_theme)
            spannableString.setSpan(
                ForegroundColorSpan(color),
                startIndex,
                fullText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.tvLoginFooter.text = spannableString
    }

    private fun setupListeners() {
        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            viewModel.signUpUser(email, password, confirmPassword, name)
        }

        binding.tvLoginFooter.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
            finish()
        }
        
        binding.btnGoogle.setOnClickListener {
            FeedbackUtils.showInfo(binding.root, "Google Sign Up Clicked")
        }
        
        binding.btnFacebook.setOnClickListener {
            FeedbackUtils.showInfo(binding.root, "Facebook Sign Up Clicked")
        }
        
        binding.btnTwitter.setOnClickListener {
            FeedbackUtils.showInfo(binding.root, "Twitter Sign Up Clicked")
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.signupState.collectLatest { state ->
                when (state) {
                    is SignupState.Idle -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSignUp.isEnabled = true
                    }
                    is SignupState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnSignUp.isEnabled = false
                    }
                    is SignupState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSignUp.isEnabled = true
                        FeedbackUtils.showSuccess(binding.root, "Account created successfully!")
                        startActivity(Intent(this@SignupActivity, VerifyNeighborhoodActivity::class.java))
                        finish()
                    }
                    is SignupState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSignUp.isEnabled = true
                        FeedbackUtils.showError(binding.root, state.message)
                    }
                }
            }
        }
    }
}
