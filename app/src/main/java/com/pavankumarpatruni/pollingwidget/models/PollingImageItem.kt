package com.pavankumarpatruni.pollingwidget.models

data class PollingImageItem(
    val question: String,
    val answers: List<AnswerImage>,
    val selectedAnswer: Int
)

data class AnswerImage(val answer: String, val img: String, val count: Int, val total: Int)