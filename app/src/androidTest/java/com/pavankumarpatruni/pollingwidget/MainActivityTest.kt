package com.pavankumarpatruni.pollingwidget

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    /**
     * Polling with Text Button UI TestCase
     */
    @Test
    fun testTextWidget() {
        Espresso.onView(withId(R.id.buttonPollingWithText)).perform(ViewActions.click())
    }

    /**
     * Polling with Image Button UI TestCase
     */
    @Test
    fun testImageWidget() {
        Espresso.onView(withId(R.id.buttonPollingWithImage)).perform(ViewActions.click())
    }

    @After
    fun tearDown() {
    }

}