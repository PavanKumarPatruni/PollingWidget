package com.pavankumarpatruni.pollingwidget

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var context: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        buttonPollingWithText.setOnClickListener {

            val intent = Intent(context, PollingTextActivity::class.java)
            startActivity(intent)

        }

        buttonPollingWithImage.setOnClickListener {

            val intent = Intent(context, PollingImageActivity::class.java)
            startActivity(intent)

        }

    }


}
