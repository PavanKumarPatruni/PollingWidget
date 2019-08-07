package com.pavankumarpatruni.pollingwidget

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pavankumarpatruni.pollingwidget.models.Answer
import com.pavankumarpatruni.pollingwidget.models.PollingItem
import com.pavankumarpatruni.pollingwidget.widgets.PollingWidget
import android.content.res.Configuration
import android.view.MenuItem


class PollingTextActivity : AppCompatActivity(), OnItemClickListener {

    private var isRotated: Boolean = false
    private var selectedAnswer: Int = -1
    private var pollingWidget: PollingWidget? = null

    override fun onItemClick(position: Int) {
        selectedAnswer = position
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polling_text)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //getting data from saved instance state to retain the UI
        if (savedInstanceState != null) {
            selectedAnswer = savedInstanceState.getInt("selected_option")
            isRotated = savedInstanceState.getBoolean("rotated")
        } else {
            isRotated = false
        }
    }

    override fun onResume() {
        super.onResume()

        setData()
    }

    /**
     * setting data to widget
     */
    private fun setData() {
        if (pollingWidget == null) {
            pollingWidget = this.findViewById<PollingWidget>(R.id.pollingWidget)

            val answerList: List<Answer> = arrayListOf(
                Answer("Java", 22, 100),
                Answer("Kotlin", 56, 100),
                Answer("JavaScript", 14, 100),
                Answer("Python", 8, 100)
            )

            val pollingItem = PollingItem("Whats your most favorite programming language to work?", answerList, selectedAnswer)
            pollingWidget?.setOnItemClickListener(this)
            pollingWidget?.setPollingData(pollingItem)
        }
    }

    /**
     * Storing data while config changes
     */
    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("selected_option", selectedAnswer)
        savedInstanceState.putBoolean("rotated", isRotated)
    }

    /**
     * managing UI while activity state changes
     */
    override fun onPause() {
        if (isRotated) {
            pollingWidget?.clearViews()
        }
        super.onPause()
    }

    /**
     * managing UI while activity config changes
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        isRotated = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
