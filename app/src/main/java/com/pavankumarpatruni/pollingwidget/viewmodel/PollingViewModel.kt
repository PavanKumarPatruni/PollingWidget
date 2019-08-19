package com.pavankumarpatruni.pollingwidget.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.pavankumarpatruni.pollingwidget.models.PollingItem
import com.pavankumarpatruni.pollingwidget.repo.PollingRepo

class PollingViewModel : ViewModel() {

    private var pollingItem: MutableLiveData<PollingItem> = MutableLiveData()

    init {
        pollingItem = PollingRepo.getInstance().getPollingTextData()
    }

    fun getPollingItem(): LiveData<PollingItem> {
        return pollingItem
    }

    fun selectAnswer(selected: Int) {
        val pollData = pollingItem.value
        pollData?.selectedAnswer = selected

        pollingItem.value = pollData
    }

}