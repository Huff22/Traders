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
import com.example.databinding.ActivitySigninBinding
import com.example.utils.FeedbackUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private val viewModel: SigninViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
        observeViewModel()
    }

    private fun setupUI() {
        val fullText = "Don't have an account? SIGN UP"
        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf("SIGN UP")
        if (startIndex != -1) {
            val color = ContextCompat.getColor(this, R.color.secondary_theme)
            spannableString.setSpan(
                ForegroundColorSpan(color),
                startIndex,
                fullText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.tvRegisterFooter.text = spannableString
    }

    private fun setupListeners() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.signInUser(email, password)
        }

        binding.tvRegisterFooter.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
        
        binding.btnGoogle.setOnClickListener {
            FeedbackUtils.showInfo(binding.root, "Google Sign In Clicked")
        }
        
        binding.btnFacebook.setOnClickListener {
            FeedbackUtils.showInfo(binding.root, "Facebook Sign In Clicked")
        }
        
        binding.btnTwitter.setOnClickListener {
            FeedbackUtils.showInfo(binding.root, "Twitter Sign In Clicked")
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.signinState.collectLatest { state ->
                when (state) {
                    is SigninState.Idle -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSignIn.isEnabled = true
                    }
                    is SigninState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnSignIn.isEnabled = false
                    }
                    is SigninState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSignIn.isEnabled = true
                        FeedbackUtils.showSuccess(binding.root, "Signed in successfully!")
                        startActivity(Intent(this@SigninActivity, VerifyNeighborhoodActivity::class.java))
                        finish()
                    }
                    is SigninState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSignIn.isEnabled = true
                        FeedbackUtils.showError(binding.root, state.message)
                    }
                }
            }
        }
    }
}
