package com.example.drawmeup

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var device: UiDevice
    private val timeout = 5000L
    private val packageName = "com.example.drawmeup"
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.drawmeup", appContext.packageName)
    }

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Wyczyszczenie wcześniejszych działań
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeout)
    }

    @Test
    fun launchApp_shouldOpenApp() {
        assert(device.hasObject(By.pkg(packageName).depth(0)))
    }

    @Test
    fun pressBackButton_shouldReturnToHomeScreen() {
        device.pressBack()
        device.wait(Until.hasObject(By.pkg("com.android.launcher").depth(0)), timeout)
    }

    @Test
    fun clickSettingsButton_shouldNavigateToSettingsScreen() {
        val settingsButton = device.findObject(UiSelector().descriptionContains("Profile"))
        settingsButton.click()
        device.wait(Until.hasObject(By.pkg(packageName).text("Test")), timeout)
    }

    @After
    fun tearDown() {
        device.pressHome()
    }
}