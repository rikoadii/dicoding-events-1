package com.submissionandroid.dicodingevents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submissionandroid.dicodingevents.repository.EventsRepository
import com.submissionandroid.dicodingevents.response.EventsResponse
import com.submissionandroid.dicodingevents.utils.Event
import kotlinx.coroutines.launch

class EventsViewModel(private val repository: EventsRepository) : ViewModel() {
    private val _activeEvents = MutableLiveData<EventsResponse>()
    val activeEvents: LiveData<EventsResponse> = _activeEvents

    private val _finishedEvents = MutableLiveData<EventsResponse>()
    val finishedEvents: LiveData<EventsResponse> = _finishedEvents


    private val _eventDetail = MutableLiveData<DetailEventResponse>()
    val eventDetail: LiveData<DetailEventResponse> = _eventDetail

    private val _searchedEvents = MutableLiveData<EventsResponse>()
    val searchedEvents: LiveData<EventsResponse> = _searchedEvents

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    fun fetchActiveEvents() {
        viewModelScope.launch {
            try {
                val response = repository.getActiveEvents()
                _activeEvents.value = response
            } catch (e: Exception) {
                Log.e("EventsViewModel", "Error fetching active events", e)
                _errorMessage.value = Event(e.message ?: "Get Error.")
            }
        }
    }

    fun fetchFinishedEvents() {
        viewModelScope.launch {
            try {
                val response = repository.getFinishedEvents()
                _finishedEvents.value = response
            } catch (e: Exception) {
                Log.e("EventsViewModel", "Error fetching finished events", e)
                _errorMessage.value = Event(e.message ?: "Get Error.")
            }
        }
    }


    fun searchEvents(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchEvents(query)
                _searchedEvents.value = response
            } catch (e: Exception) {
                Log.e("EventsViewModel", "Error searching events", e)
                _errorMessage.value = Event(e.message ?: "Ger Error.")
            }
        }
    }

    fun fetchEventDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getEventDetail(id)
                _eventDetail.value = response
            } catch (e: Exception) {
                Log.e("EventsViewModel", "Error fetching event detail", e)
            }
        }
    }
}
