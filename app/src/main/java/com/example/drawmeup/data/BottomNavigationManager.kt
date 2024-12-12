package com.example.drawmeup.data

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.drawmeup.R
import com.google.android.material.bottomnavigation.BottomNavigationView

object BottomNavigationManager {

    fun hide(activity: FragmentActivity) {
        activity.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.GONE
    }

    fun show(activity: FragmentActivity) {
        activity.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE
    }

}