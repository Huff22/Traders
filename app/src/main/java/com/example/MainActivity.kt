package com.example

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        val fullText = "Already have an account? Log In"
        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf("Log In")
        
        // Let's use the secondary theme color for the whole text but let's make it standard if needed.
        // Actually, the XML already styles it green and bold, but we can make "Log In" stand out more if we wanted.
        binding.tvLogin.text = spannableString
    }

    private fun setupListeners() {
        binding.btnGetStarted.setOnClickListener {
            startActivity(android.content.Intent(this, SignupActivity::class.java))
        }

        binding.tvLogin.setOnClickListener {
            startActivity(android.content.Intent(this, SigninActivity::class.java))
        }
    }
}
