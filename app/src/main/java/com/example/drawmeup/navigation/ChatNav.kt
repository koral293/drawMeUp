package com.example.drawmeup.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.drawmeup.R

class ChatNav (val id: Int) : Destination() {
    override fun navigate(controller: NavController) {
        controller.navigate(
            R.id.action_navigation_conversations_to_chatFragment,
            bundleOf("type" to ChatType.View(id))
        )
    }
}