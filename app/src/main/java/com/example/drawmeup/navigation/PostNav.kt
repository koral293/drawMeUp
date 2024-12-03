package com.example.drawmeup.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.drawmeup.R

class PostNav (val id: Int, val bool: Boolean) : Destination() {
    override fun navigate(controller: NavController) {
        if (bool) {
            controller.navigate(
                R.id.action_navigation_home_to_postFragment2,
                bundleOf("type" to PostType.View(id))
            )
        } else {
            controller.navigate(
                R.id.action_navigation_profile_to_addPostFragment,
                bundleOf("type" to PostType.View(id))
            )
        }
    }
}