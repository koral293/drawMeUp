package com.example.drawmeup

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.AssertionError

class GuestTest {

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
    }

    @After
    fun tearDown() {
        val profile =
            device.findObject(UiSelector().resourceId("com.example.drawmeup:id/navigation_profile"))
        if (!profile.exists()) {
            return
        }
        val logOutButton =
            device.findObject(UiSelector().resourceId("com.example.drawmeup:id/logoutButton"))
        val confirm = device.findObject(UiSelector().resourceId("android:id/button1"))
        profile.click()
        logOutButton.click()
        confirm.click()
    }

    @Test
    fun signUp() {
        val signUpButton = device.findObject(
            UiSelector().resourceId("com.example.drawmeup:id/signUpButton")
        )
        val emailField = device.findObject(
            UiSelector().resourceId("com.example.drawmeup:id/emailEditText")
        )
        val passwordField = device.findObject(
            UiSelector().resourceId("com.example.drawmeup:id/passwordEditText")
        )
        val nickname = device.findObject(
            UiSelector().resourceId("com.example.drawmeup:id/nicknameEditText")
        )
        val singUpButton = device.findObject(
            UiSelector().resourceId("com.example.drawmeup:id/signUpButton")
        )
        signUpButton.click()
        nickname.setText("UItest")
        emailField.setText("testUI@testUI")
        passwordField.setText("test1234")
        singUpButton.click()
        device.wait(
            Until.hasObject(
                By.pkg(packageName).text("Profile")
            ), timeout
        )
    }

    @Test
    fun signUpEmailExists() {
        val signUpButton =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/signUpButton")
            )
        val emailField =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/emailEditText")
            )
        val passwordField =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/passwordEditText")
            )
        val nickname =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/nicknameEditText")
            )
        val singUpButton =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/signUpButton")
            )
        val profile =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/navigation_profile")
            )
        signUpButton.click()
        nickname.setText("test2")
        emailField.setText("test@test")
        passwordField.setText("test1234")
        singUpButton.click()
        if (profile.exists()) {
            throw AssertionError("Profile exists")
        }
    }

    @Test
    fun signUpNicknameExists() {
        val signUpButton =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/signUpButton"))
        val emailField =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/emailEditText"))
        val passwordField =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/passwordEditText"))
        val nickname =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/nicknameEditText"))
        val singUpButton =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/signUpButton"))
        val profile =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/navigation_profile"))
        signUpButton.click()
        nickname.setText("Test")
        emailField.setText("test@test")
        passwordField.setText("test1234")
        singUpButton.click()
        if (profile.exists()) {
            throw AssertionError("Profile exists")
        }
    }

    @Test
    fun signUpIncorrectPassword() {
        val signUpButton =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/signUpButton"))
        val emailField =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/emailEditText"))
        val passwordField =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/passwordEditText"))
        val nickname =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/nicknameEditText"))
        val singUpButton =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/signUpButton"))
        val profile =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/navigation_profile"))
        signUpButton.click()
        nickname.setText("Test")
        emailField.setText("test@test")
        passwordField.setText("1234")
        singUpButton.click()
        if (profile.exists()) {
            throw AssertionError("Profile exists")
        }
    }

    @Test
    fun signInIncorrectEmail() {
        val signInButton =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/signInButton"))
        val emailField =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/emailEditText"))
        val passwordField =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/passwordEditText"))
        val profile =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/navigation_profile"))
        signInButton.click()
        emailField.setText("test")
        passwordField.setText("test1234")
        signInButton.click()
        if (profile.exists()) {
            throw AssertionError("Profile exists")
        }
    }

    @Test
    fun signInIncorrectPassword() {
        val signInButton =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/signInButton"))
        val emailField =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/emailEditText"))
        val passwordField =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/passwordEditText"))
        val profile =
            device.findObject(
                UiSelector().resourceId("com.example.drawmeup:id/navigation_profile"))
        signInButton.click()
        emailField.setText("test@test")
        passwordField.setText("test1234")
        signInButton.click()
        if (profile.exists()) {
            throw AssertionError("Profile exists")
        }
    }
}