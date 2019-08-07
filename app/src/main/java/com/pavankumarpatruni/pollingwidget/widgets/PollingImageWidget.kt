package com.pavankumarpatruni.pollingwidget.widgets

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.*
import com.bumptech.glide.Glide
import com.pavankumarpatruni.pollingwidget.OnItemClickListener
import com.pavankumarpatruni.pollingwidget.R
import com.pavankumarpatruni.pollingwidget.models.Answer
import com.pavankumarpatruni.pollingwidget.models.AnswerImage
import com.pavankumarpatruni.pollingwidget.models.PollingImageItem
import com.pavankumarpatruni.pollingwidget.models.PollingItem


class PollingImageWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var onItemClickListener: OnItemClickListener? = null
    private var selectedAnswer: Int = -1
    private lateinit var pollingItem: PollingImageItem
    private var answerSubmitted: Boolean = false
    private var firstClick: Boolean = true
    private var progressBarTitle: ProgressBar? = null
    private var linearLayoutAnswers: LinearLayout? = null

    /**
     * Setting Data
     */
    fun setPollingData(input: PollingImageItem) {
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
            .inflate(R.layout.layout_polling_image_widget, this, false)

        selectedAnswer = pollingItem.selectedAnswer
        if (selectedAnswer != -1) {
            answerSubmitted = true
            firstClick = false
        }

        val textViewQuestion = view.findViewById<TextView>(R.id.textViewQuestion)
        textViewQuestion.text = pollingItem.question

        progressBarTitle = view.findViewById<ProgressBar>(R.id.progressBarTitle)
        progressBarTitle?.visibility = GONE

        linearLayoutAnswers = view.findViewById<LinearLayout>(R.id.linearLayoutAnswers)
        attachAnswers()

        this.addView(view)
    }

    /**
     * Preparing Answers
     */
    private fun attachAnswers() {
        linearLayoutAnswers?.removeAllViews()

        val answers = pollingItem.answers
        val len = answers.size

        for ((index, answer: AnswerImage) in answers.withIndex()) {

            println("$index $answer")

            val view = LayoutInflater.from(context)
                .inflate(R.layout.layout_item_polling_image_widget, this, false)

            val imageView = view.findViewById<ImageView>(R.id.imageView)
            Glide.with(this)
                .load(answer.img)
                .into(imageView)

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
    }

    /**
     * Clearing UI
     */
    fun clearViews() {
        linearLayoutAnswers?.removeAllViews()
        linearLayoutAnswers?.invalidate()
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
        progressBarTitle?.visibility = View.GONE
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