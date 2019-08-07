package com.pavankumarpatruni.pollingwidget

import android.support.test.espresso.Espresso
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test

class PollingTextActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(PollingTextActivity::class.java)

    @Before
    fun setUp() {
    }

    /**
     * ProgressBar UI TestCase
     */
    @Test
    fun testWidget() {
        Espresso.onData(withId(R.id.progressBar)).inAdapterView(allOf(withId(R.id.relativeLayoutItem), isDisplayed()))
    }

    @After
    fun tearDown() {
    }
}