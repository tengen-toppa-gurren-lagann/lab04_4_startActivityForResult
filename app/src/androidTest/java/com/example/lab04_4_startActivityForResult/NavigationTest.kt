package com.example.lab04_4_startActivityForResult

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import android.content.pm.ActivityInfo
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Assert
import org.junit.Rule
import java.lang.Thread.sleep

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private fun screenToLandscape() {
        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        sleep(1000) // Для визуального контроля
    }

    private fun screenToPortrait() {
        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        sleep(1000) // Для визуального контроля
    }

    private fun checkBackstack(size: Int) {
        repeat(size) {
            pressBackUnconditionally()
            sleep(500) // Для визуального контроля
        }
        Assert.assertEquals(Lifecycle.State.DESTROYED, activityScenarioRule.scenario.state)
    }

    private fun checkFragment1() { // Проверка 1-го фрагмента
        onView(withId(R.id.fragment1)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment2)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(doesNotExist())
        onView(withId(R.id.bnToFirst)).check(doesNotExist())
        onView(withId(R.id.bnToSecond)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToThird)).check(doesNotExist())
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    private fun checkFragment2() { // Проверка 2-го фрагмента
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment2)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment3)).check(doesNotExist())
        onView(withId(R.id.bnToFirst)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToSecond)).check(doesNotExist())
        onView(withId(R.id.bnToThird)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    private fun checkFragment3() { // Проверка 3-го фрагмента
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment2)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToFirst)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToSecond)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToThird)).check(doesNotExist())
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    private fun checkAbout() { // Проверка activityAbout
        onView(withId(R.id.activity_about)).check(matches(isDisplayed()))
        onView(withId(R.id.tvAbout)).check(matches(isDisplayed()))
        onView(withId(R.id.bnToFirst)).check(doesNotExist())
        onView(withId(R.id.bnToSecond)).check(doesNotExist())
        onView(withId(R.id.bnToThird)).check(doesNotExist())
        onView(withId(R.id.fragment1)).check(doesNotExist())
        onView(withId(R.id.fragment2)).check(doesNotExist())
        onView(withId(R.id.fragment3)).check(doesNotExist())
    }

    @Test
    fun testAbout() { // Тест "About"
        openAbout()
        checkAbout()
    }

    @Test
    fun testFragment1_to_Fragment2() { // Тест перехода от 1-го фрагмента ко 2-му
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
    }

    @Test
    fun testFragment2_to_Fragment3() { // Тест перехода от 2-го фрагмента к 3-му
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        checkFragment3()
    }

    @Test
    fun testFragment2_to_Fragment1() { // Тест перехода от 2-го фрагмента ко 1-му
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToFirst)).perform(click())
        checkFragment1()
    }

    @Test
    fun testFragment3_to_Fragment2() { // Тест перехода от 3-го фрагмента ко 2-му
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
    }

    @Test
    fun testFragment3_to_Fragment1() { // Тест перехода от 3-го фрагмента к 1-му
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        onView(withId(R.id.bnToFirst)).perform(click())
        checkFragment1()
    }

    @Test
    fun testBackstack() { // Тест backstack при переходах
        checkBackstack(1)

        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        checkBackstack(2)

        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        checkBackstack(3)

        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        testAbout()
        checkBackstack(4)

        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        testAbout()
        pressBack()
        checkBackstack(3)

        launchActivity<MainActivity>()
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        onView(withId(R.id.bnToFirst)).perform(click())
        checkBackstack(1)
    }

    @Test
    fun testNavigationForward() { // Тест навигации "вперед"
        checkFragment1()
        testAbout()
        pressBack()
        checkFragment1()
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
        testAbout()
        pressBack()
        checkFragment2()
        onView(withId(R.id.bnToFirst)).perform(click())
        checkFragment1()
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
        onView(withId(R.id.bnToThird)).perform(click())
        checkFragment3()
        testAbout()
        pressBack()
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
        onView(withId(R.id.bnToThird)).perform(click())
        checkFragment3()
        testAbout()
        pressBack()
        onView(withId(R.id.bnToFirst)).perform(click())
        checkFragment1()
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
        onView(withId(R.id.bnToThird)).perform(click())
        checkFragment3()
        pressBack()
        checkFragment2()
        pressBack()
        checkFragment1()
    }

    @Test
    fun testNavigationUp() { // Тест навигации "вверх"
        val strNavigateUp = "Navigate up"
        checkFragment1()
        testAbout()
        onView(withContentDescription(strNavigateUp)).perform(click())
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()

        testAbout()
        onView(withContentDescription(strNavigateUp)).perform(click())
        onView(withId(R.id.bnToFirst)).perform(click())
        checkFragment1()
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
        onView(withId(R.id.bnToThird)).perform(click())
        checkFragment3()

        testAbout()
        onView(withContentDescription(strNavigateUp)).perform(click())
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
        onView(withId(R.id.bnToThird)).perform(click())
        checkFragment3()

        testAbout()
        onView(withContentDescription(strNavigateUp)).perform(click())
        onView(withId(R.id.bnToFirst)).perform(click())
        checkFragment1()
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
        onView(withId(R.id.bnToThird)).perform(click())
        checkFragment3()
        onView(withContentDescription(strNavigateUp)).perform(click())
        checkFragment2()
        onView(withContentDescription(strNavigateUp)).perform(click())
        checkFragment1()
    }

    @Test
    fun testChangeOrientationFragment1() { // Проверка сохранения состояния при повороте экрана (1-й фрагмент)
        checkFragment1()
        screenToLandscape()
        checkFragment1()
        screenToPortrait()
        checkFragment1()
    }

    @Test
    fun testChangeOrientationFragment2() { // Проверка сохранения состояния при повороте экрана (2-й фрагмент)
        onView(withId(R.id.bnToSecond)).perform(click())
        checkFragment2()
        screenToLandscape()
        checkFragment2()
        screenToPortrait()
        checkFragment2()
    }

    @Test
    fun testChangeOrientationFragment3() { // Проверка сохранения состояния при повороте экрана (3-й фрагмент)
        onView(withId(R.id.bnToSecond)).perform(click())
        onView(withId(R.id.bnToThird)).perform(click())
        checkFragment3()
        screenToLandscape()
        checkFragment3()
        screenToPortrait()
        checkFragment3()
    }

    @Test
    fun testChangeOrientationAbout() { // Проверка сохранения состояния при повороте экрана (about)
        testAbout()
        screenToLandscape()
        checkAbout()
        screenToPortrait()
        checkAbout()
    }

    @Test
    fun testStartStopActivity() { // Проверка запуска и остановки приложения
        Assert.assertEquals(Lifecycle.State.RESUMED, activityScenarioRule.scenario.state)
        checkFragment1()
        pressBackUnconditionally()
        sleep(1000)
        Assert.assertEquals(Lifecycle.State.DESTROYED, activityScenarioRule.scenario.state)
    }
}