package com.example.drawmeup

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test

class UserTest {

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
        val loginButton = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/welcomeSignInButton"))
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
        val profile = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/navigation_profile"))
        val logOutButton = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/logoutButton"))
        val confirm = device.findObject(UiSelector().resourceId("android:id/button1"))
        profile.click()
        logOutButton.click()
        confirm.click()
        assert(device.hasObject(By.pkg(packageName).text("Witaj w Dolinie")))
    }

    @Test
    fun sendMessage() {
        val conversations = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/navigation_conversations"))
        val recipient = device.findObject(UiSelector().text("Test2"))
        val message = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/sendMessageEditText"))
        val send = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/sendMessageButton"))
        conversations.click()
        recipient.click()
        message.setText("UI test")
        send.click()
        assert(device.hasObject(By.pkg(packageName).text("UI test")))
    }

}