package hccn.adastragrp.com.wezualizer.base.bindingutil

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("android:background")
    fun setBackgroundRes(view: View, resId: Int) {
        view.setBackgroundColor(resId)}