package com.example.utils

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.R
import com.google.android.material.snackbar.Snackbar

object FeedbackUtils {

    fun showSuccess(view: View, message: String) {
        showCustomSnackbar(view, message, R.color.secondary_theme)
    }

    fun showError(view: View, message: String) {
        showCustomSnackbar(view, message, android.R.color.holo_red_dark)
    }

    fun showInfo(view: View, message: String) {
        showCustomSnackbar(view, message, android.R.color.darker_gray)
    }

    private fun showCustomSnackbar(view: View, message: String, colorResId: Int) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        
        // Style the background
        snackbarView.setBackgroundColor(ContextCompat.getColor(view.context, colorResId))
        snackbarView.background = ContextCompat.getDrawable(view.context, R.drawable.bg_snackbar)
        
        // Style the text
        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
        textView.textSize = 16f
        textView.maxLines = 3
        
        // Make it float
        val params = snackbarView.layoutParams as? FrameLayout.LayoutParams
        if (params != null) {
            params.setMargins(32, 32, 32, 32)
            snackbarView.layoutParams = params
        }
        
        snackbar.show()
    }
}
