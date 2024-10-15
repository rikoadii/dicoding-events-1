package com.submissionandroid.dicodingevents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submissionandroid.dicodingevents.repository.EventsRepository

@Suppress("UNCHECKED_CAST")
class EventsViewModelFactory(private val repository: EventsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventsViewModel::class.java)) {
            return EventsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
