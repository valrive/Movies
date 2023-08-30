package com.udemy.startingpointpersonal.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udemy.startingpointpersonal.R
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


fun String.toDate(): Date? {
    val formatDate = "yyyy/MM/dd'T'HH:mm:ssZZZ"

    var formatter = SimpleDateFormat(formatDate, Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    var date = formatter.parse(this)
    if(date == null){
        formatter = SimpleDateFormat(formatDate, Locale.getDefault())//Constants.DEFAULT_LOCALE
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        date = formatter.parse(this)
    }
    return date
}
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

@BindingAdapter("url")
fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).centerCrop().into(this)
}

inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}

fun <VH : RecyclerView.ViewHolder, T> RecyclerView.Adapter<VH>.basicDiffUtil(
    initialValue: List<T> = emptyList(),
    areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) =
    Delegates.observable(initialValue) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areItemsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areContentsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun getOldListSize() = old.size
            override fun getNewListSize() = new.size
        }).dispatchUpdatesTo(this)
    }

private var IS_PROGRESSBAR_VISIBLE = false
private var progressBar: View? = null

fun Activity.muestraProgressBar() {
    if (IS_PROGRESSBAR_VISIBLE) return

    progressBar = LayoutInflater.from(this).inflate(
        R.layout.loading_view,
        findViewById<View>(android.R.id.content) as ViewGroup,
        false
    )
    (findViewById<View>(android.R.id.content) as ViewGroup).addView(progressBar)
    if (currentFocus != null) currentFocus!!.clearFocus()
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        val bar: ProgressBar = progressBar!!.findViewById(R.id.progressBar)
        val drawableProgress = DrawableCompat.wrap(bar.indeterminateDrawable)
        DrawableCompat.setTint(
            drawableProgress,
            ContextCompat.getColor(this, R.color.colorPrimaryDark)
        )
        bar.indeterminateDrawable = DrawableCompat.unwrap(drawableProgress)
    }
    IS_PROGRESSBAR_VISIBLE = true
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.escondeProgressBar() {
    if (!IS_PROGRESSBAR_VISIBLE) return

    if (progressBar != null && progressBar!!.parent != null) {
        (progressBar!!.parent as ViewGroup).removeView(progressBar)
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
    progressBar = null
    IS_PROGRESSBAR_VISIBLE = false
}

fun <T> LifecycleOwner.launchAndCollect(collectable: StateFlow<T>, onResult: (T) -> Unit) =
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collectable.collect { onResult(it) }
        }
    }
