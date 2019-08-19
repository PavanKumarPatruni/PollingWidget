package com.pavankumarpatruni.pollingwidget

import android.support.test.espresso.Espresso
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import com.pavankumarpatruni.pollingwidget.activities.PollingImageActivity
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PollingImageActivityTest {


    @get:Rule
    var activityTestRule = ActivityTestRule(PollingImageActivity::class.java)

    @Before
    fun setUp() {
    }

    /**
     * ProgressBar UI TestCase
     */
    @Test
    fun testWidget() {
        Espresso.onData(ViewMatchers.withId(R.id.progressBar)).inAdapterView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.relativeLayoutItem),
                ViewMatchers.isDisplayed()
            )
        )
    }

    @After
    fun tearDown() {
    }
}