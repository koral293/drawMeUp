package com.example.drawmeup

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test
import java.lang.AssertionError

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
        val profile = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/navigation_profile"))
        val logOutButton = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/logoutButton"))
        val confirm = device.findObject(UiSelector().resourceId("android:id/button1"))
        profile.click()
        logOutButton.click()
        confirm.click()
        assert(device.hasObject(By.pkg(packageName).text("Are you ready to DrawMeUp?")))
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

    @Test
    fun createConversation_and_send_message() {
        val conversations = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/navigation_conversations"))
        val create = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/newConversationButton"))
        val addRecipient = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/edit_text"))
        val confirm = device.findObject(UiSelector().resourceId("android:id/button1"))
        val recipient = device.findObject(UiSelector().text("conversationTest"))
        val message = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/sendMessageEditText"))
        val send = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/sendMessageButton"))
        conversations.click()
        create.click()
        addRecipient.setText("conversationTest")
        confirm.click()
        recipient.click()
        message.setText("UI test")
        send.click()
        assert(device.hasObject(By.pkg(packageName).text("UI test")))
    }

    @Test
    fun createConversation_user_not_found() {
        val conversations = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/navigation_conversations"))
        val create = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/newConversationButton"))
        val addRecipient = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/edit_text"))
        val confirm = device.findObject(UiSelector().resourceId("android:id/button1"))
        val recipient = device.findObject(UiSelector().text("conversationTest2"))
        conversations.click()
        create.click()
        addRecipient.setText("conversationTest2")
        confirm.click()
        if (recipient.exists()) {
            throw AssertionError("Recipient found")
        }
    }

    @Test
    fun addPost() {
        val addPost = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/addPostButton"))
        val title = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/nameEditText"))
        val description = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/descriptionEditText"))
        val tags = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/tagsEditText"))
        val add = device.findObject(UiSelector().resourceId("com.example.drawmeup:id/addButton"))
        addPost.click()
        title.setText("UI test")
        description.setText("UI test")
        tags.setText("UI test")
        add.click()
    }

}