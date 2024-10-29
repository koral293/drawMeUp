package com.example.drawmeup.utils

import android.util.Log

object Logger {

    private fun getClassName() : String {
        val stackTrace = Thread.currentThread().stackTrace
        val className = stackTrace.getOrNull(4)?.className?.substringAfterLast(".")
        val methodName = stackTrace.getOrNull(4)?.methodName
        return "$className $methodName"
    }

    fun debug(message: String) {
        Log.d(getClassName(), message)
    }

}