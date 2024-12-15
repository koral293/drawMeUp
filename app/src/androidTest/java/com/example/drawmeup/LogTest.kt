package com.example.drawmeup

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogTest {

    private lateinit var device: UiDevice
    private val timeout = 5000L
    private val packageName = "com.example.drawmeup"

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeout)
    }

    @Test
    fun logIn() {
        val loginButton = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/signInButton"))
        val emailField = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/emailEditText"))
        val passwordField = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/passwordEditText"))
        val singInButton = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/signInButton"))
        loginButton.click()
        emailField.setText("test@test")
        passwordField.setText("test")
        singInButton.click()
        device.wait(Until.hasObject(By.pkg(packageName).text("Profile")), timeout)
    }

    @Test
    fun logOut() {
        val logOutButton = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/logoutButton"))
        logOutButton.click()
        device.wait(Until.hasObject(By.pkg(packageName).text("Sign in")), timeout)
    }
}