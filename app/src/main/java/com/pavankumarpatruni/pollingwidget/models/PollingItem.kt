package com.pavankumarpatruni.pollingwidget.models

data class PollingItem(val question: String, val answers: List<Answer>, val selectedAnswer: Int)

data class Answer(val answer: String, val count: Int, val total: Int)