package com.example.coworking.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.coworking.R
import com.google.android.material.snackbar.Snackbar

object GlobalSnackBar {

    @SuppressLint("RestrictedApi")
    fun showSnackBar(view: View, message: String, isSuccess: Boolean) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val snackBarLayoutBinding = inflater.inflate(R.layout.snackbar_layout, null)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT);
        val snackBarLayout = snackbar.view as Snackbar.SnackbarLayout
        snackBarLayout.addView(snackBarLayoutBinding)

        snackBarLayout.findViewById<ConstraintLayout>(R.id.clSnackBar).setBackgroundResource(
            if (isSuccess) R.drawable.success_snackbar_background else R.drawable.error_snackbar_background
        )
        snackBarLayout.findViewById<TextView>(R.id.tvTitle).text =
            if (isSuccess) "Success" else "Error"
        snackBarLayout.findViewById<TextView>(R.id.tvSubTitle).text = message
        snackbar.show()
    }
}