package com.mad43.recipestask.utils

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

fun Activity.showToast(message : String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.visibilityGone() {
    this.visibility = View.GONE
}

fun View.visibilityVisible() {
    this.visibility = View.VISIBLE
}

fun ProgressBar.showProgress() {
    this.visibility = View.VISIBLE
}

fun ProgressBar.hideProgress() {
    this.visibility = View.GONE
}

