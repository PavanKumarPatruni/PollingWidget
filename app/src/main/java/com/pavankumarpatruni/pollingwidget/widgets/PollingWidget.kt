package com.pavankumarpatruni.pollingwidget.widgets

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.pavankumarpatruni.pollingwidget.R
import com.pavankumarpatruni.pollingwidget.listeners.OnItemClickListener
import com.pavankumarpatruni.pollingwidget.models.Answer
import com.pavankumarpatruni.pollingwidget.models.PollingItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_polling_widget.view.*

class PollingWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var onItemClickListener: OnItemClickListener? = null
    private var selectedAnswer: Int = -1
    private lateinit var pollingItem: PollingItem
    private var answerSubmitted: Boolean = false
    private var firstClick: Boolean = true
    private var progressBarTitle: ProgressBar? = null
    private var linearLayoutAnswers: LinearLayout? = null

    /**
     * Setting Data
     */
    fun setPollingData(input: PollingItem) {
        pollingItem = input

        initUI()
    }

    /**
     * Setting Listener
     */
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    /**
     * Initializing UI
     */
    private fun initUI() {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_polling_widget, this, false)

        selectedAnswer = pollingItem.selectedAnswer
        if (selectedAnswer != -1) {
            answerSubmitted = true
            firstClick = false
        }

        val textViewQuestion = view.textViewQuestion
        textViewQuestion.text = pollingItem.question

        progressBarTitle = view.progressBarTitle
        progressBarTitle?.visibility = GONE

        linearLayoutAnswers = view.linearLayoutAnswers
        attachAnswers()

        this.addView(view)
    }

    /**
     * Preparing Answers
     */
    @SuppressLint("CheckResult")
    private fun attachAnswers() {
        linearLayoutAnswers?.removeAllViews()

        val answers = pollingItem.answers
        val len = answers.size
        var index = 0

        val observable = Observable.fromIterable(answers)
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                prepareChildView(index, len, it)
                index++
            }
    }

    private fun prepareChildView(index: Int, len: Int, answer: Answer) {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_item_polling_widget, this, false)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progressDrawable =
            this.resources.getDrawable(R.drawable.custom_progressbar)
        progressBar.max = 100
        progressBar.progress = 0

        val textViewAnswer = view.findViewById<TextView>(R.id.textViewAnswer)
        textViewAnswer.text = answer.answer

        val textViewPercentage = view.findViewById<TextView>(R.id.textViewPercentage)
        val percent = answer.count % answer.total

        textViewPercentage.text = percent.toString().plus("%")
        if (selectedAnswer != -1) {
            progressBar.progress = percent
            textViewPercentage.visibility = View.VISIBLE
        } else {
            progressBar.progress = 0
            textViewPercentage.visibility = View.GONE
        }
        view.isSelected = false

        view.tag = index
        view.setOnClickListener {
            showProgressbar()

            onItemClickListener?.onItemClick(index)

            answerSubmitted = true
            selectedAnswer = view.tag as Int
            attachAnswers()
        }

        if (selectedAnswer == index) {
            view.isSelected = true
            progressBar.progressDrawable =
                this.resources.getDrawable(R.drawable.custom_progressbar_active)
        }

        if (answerSubmitted && firstClick) {
            setProgressAnimate(progressBar, percent)

            if (index == len - 1) {
                firstClick = false
            }
        }

        linearLayoutAnswers?.addView(view)
    }

    /**
     * Show ProgressBar
     */
    private fun showProgressbar() {
        progressBarTitle?.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed({ hideProgressbar() }, 1000)
    }

    /**
     * Hide ProgressBar
     */
    private fun hideProgressbar() {
        progressBarTitle?.visibility = View.INVISIBLE
    }

    /**
     * Setting ProgressBar Max Value
     */
    private fun setProgressMax(progressBar: ProgressBar, max: Int) {
        progressBar.max = max * 100
    }

    /**
     * Setting ProgressBar Progress Value and Animation
     */
    private fun setProgressAnimate(progressBar: ProgressBar, progressTo: Int) {
        setProgressMax(progressBar, 100)
        val animation =
            ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, progressTo * 100)
        animation.duration = 1500
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }

}