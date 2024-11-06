package com.example.drawmeup.navigation

import java.io.Serializable

sealed class PostType : Serializable {
    data object New: PostType() {
        private fun readResolve(): Any = New
    }

    data class View(val id: Int): PostType()
}