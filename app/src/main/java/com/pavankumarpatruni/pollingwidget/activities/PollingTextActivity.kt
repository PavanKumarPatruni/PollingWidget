package com.pavankumarpatruni.pollingwidget.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.pavankumarpatruni.pollingwidget.R
import com.pavankumarpatruni.pollingwidget.listeners.OnItemClickListener
import com.pavankumarpatruni.pollingwidget.viewmodel.PollingViewModel
import kotlinx.android.synthetic.main.activity_polling_text.*

class PollingTextActivity : AppCompatActivity(),
    OnItemClickListener {

    private lateinit var pollingViewModel: PollingViewModel
    private var selectedAnswer: Int = -1

    override fun onItemClick(position: Int) {
        selectedAnswer = position
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polling_text)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        pollingViewModel = ViewModelProviders.of(this).get(PollingViewModel::class.java)
        pollingViewModel.getPollingItem().observe(this, Observer {
            selectedAnswer = it?.selectedAnswer!!

            pollingWidget?.setPollingData(it)
        })

    }

    override fun onStop() {
        super.onStop()

        pollingViewModel.selectAnswer(selectedAnswer)
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
