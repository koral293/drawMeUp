package com.example.drawmeup.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.drawmeup.R

class PostNav (val id: Int) : Destination() {
    override fun navigate(controller: NavController) {
        controller.navigate(
            R.id.action_navigation_home_to_postFragment2,
            bundleOf("type" to PostType.View(id))
        )
    }
}