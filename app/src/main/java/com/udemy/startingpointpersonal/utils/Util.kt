package com.udemy.startingpointpersonal.utils

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.snackbar.Snackbar
import com.udemy.startingpointpersonal.R

object Util {

    private var IS_PROGRESSBAR_VISIBLE = false
    private var progressBar: View? = null


    fun muestraProgressBar(a: Activity) {
        if (IS_PROGRESSBAR_VISIBLE) return

        progressBar = LayoutInflater.from(a).inflate(
            R.layout.loading_view,
            a.findViewById<View>(android.R.id.content) as ViewGroup,
            false
        )
        (a.findViewById<View>(android.R.id.content) as ViewGroup).addView(progressBar)
        if (a.currentFocus != null) a.currentFocus!!.clearFocus()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val bar: ProgressBar = progressBar!!.findViewById(R.id.progressBar)
            val drawableProgress = DrawableCompat.wrap(bar.indeterminateDrawable)
            DrawableCompat.setTint(
                drawableProgress,
                ContextCompat.getColor(a, R.color.colorPrimaryDark)
            )
            bar.indeterminateDrawable = DrawableCompat.unwrap(drawableProgress)
        }
        IS_PROGRESSBAR_VISIBLE = true
        a.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun escondeProgressBar(a: Activity) {
        if (!IS_PROGRESSBAR_VISIBLE) return

        if (progressBar != null && progressBar!!.parent != null) {
            (progressBar!!.parent as ViewGroup).removeView(progressBar)
            a.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        progressBar = null
        IS_PROGRESSBAR_VISIBLE = false
    }

}