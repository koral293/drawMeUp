package com.example.drawmeup.navigation

import java.io.Serializable

sealed class ChatType : Serializable {
    data object New: ChatType() {
        private fun readResolve(): Any = New
    }

    data class View(val id: Int): ChatType()
}