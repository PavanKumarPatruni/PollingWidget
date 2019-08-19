package com.pavankumarpatruni.pollingwidget.repo

import android.arch.lifecycle.MutableLiveData
import com.pavankumarpatruni.pollingwidget.models.Answer
import com.pavankumarpatruni.pollingwidget.models.PollingItem

class PollingRepo {

    private var pollingData: MutableLiveData<PollingItem> = MutableLiveData()

    private fun setData(): PollingItem {
        val answerImageList: List<Answer> = arrayListOf(
            Answer(
                "Java",
                "https://thumbs.dreamstime.com/b/java-modern-programming-language-software-development-application-concept-multi-color-arrows-pointing-to-word-center-152236215.jpg",
                22,
                100
            ),
            Answer(
                "Kotlin",
                "https://qph.fs.quoracdn.net/main-qimg-6febfa71a82b75db0bdd9777630cad2e",
                56,
                100
            ),
            Answer(
                "JavaScript",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmQlhMD7ht9GCrinuC5xOM8OIkiuJ_FRFiqna7_52OY8Uw_8Be",
                14,
                100
            ),
            Answer(
                "Python",
                "https://i.pinimg.com/736x/2f/9c/11/2f9c11f9e55efbf1791f12c06d60729b.jpg",
                8,
                100
            )
        )

        return PollingItem(
            "Whats your most favorite programming language to work?",
            answerImageList,
            -1
        )
    }

    companion object {
        private lateinit var pollingRepo: PollingRepo

        fun getInstance(): PollingRepo {
            pollingRepo = PollingRepo()
            return pollingRepo
        }
    }

    fun getPollingTextData(): MutableLiveData<PollingItem> {
        pollingData.value = setData()

        return pollingData
    }

}