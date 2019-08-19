package com.pavankumarpatruni.pollingwidget.models

data class PollingItem(val question: String, val answers: List<Answer>, var selectedAnswer: Int)

data class Answer(
    val answer: String,
    val img: String = "",
    val count: Int,
    val total: Int,
    var selected: Boolean = false
)