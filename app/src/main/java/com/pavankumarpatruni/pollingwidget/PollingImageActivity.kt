package com.pavankumarpatruni.pollingwidget

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.pavankumarpatruni.pollingwidget.models.AnswerImage
import com.pavankumarpatruni.pollingwidget.models.PollingImageItem
import com.pavankumarpatruni.pollingwidget.widgets.PollingImageWidget


class PollingImageActivity : AppCompatActivity(), OnItemClickListener {

    private var isRotated: Boolean = false
    private var selectedAnswer: Int = -1
    private var pollingImageWidget: PollingImageWidget? = null

    override fun onItemClick(position: Int) {
        selectedAnswer = position
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polling_image)

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
        if (pollingImageWidget == null) {
            pollingImageWidget = this.findViewById<PollingImageWidget>(R.id.pollingImageWidget)

            val answerImageList: List<AnswerImage> = arrayListOf(
                AnswerImage(
                    "Java",
                    "https://thumbs.dreamstime.com/b/java-modern-programming-language-software-development-application-concept-multi-color-arrows-pointing-to-word-center-152236215.jpg",
                    22,
                    100
                ),
                AnswerImage(
                    "Kotlin",
                    "https://qph.fs.quoracdn.net/main-qimg-6febfa71a82b75db0bdd9777630cad2e",
                    56,
                    100
                ),
                AnswerImage(
                    "JavaScript",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmQlhMD7ht9GCrinuC5xOM8OIkiuJ_FRFiqna7_52OY8Uw_8Be",
                    14,
                    100
                ),
                AnswerImage(
                    "Python",
                    "https://i.pinimg.com/736x/2f/9c/11/2f9c11f9e55efbf1791f12c06d60729b.jpg",
                    8,
                    100
                )
            )

            val pollingImageItem =
                PollingImageItem(
                    "Whats your most favorite programming language to work?",
                    answerImageList,
                    selectedAnswer
                )
            pollingImageWidget?.setOnItemClickListener(this)
            pollingImageWidget?.setPollingData(pollingImageItem)
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
            pollingImageWidget?.clearViews()
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
