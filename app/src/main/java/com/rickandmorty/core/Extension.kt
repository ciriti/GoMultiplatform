package com.rickandmorty.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by Carmelo Iriti
 */

fun AppCompatActivity.init(fragment: Fragment, @IdRes resId: Int) {
    supportFragmentManager
        .beginTransaction()
        .add(resId, fragment, fragment::class.java.name)
        .commit()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun RecyclerView.configureLayoutManager() {
    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
}