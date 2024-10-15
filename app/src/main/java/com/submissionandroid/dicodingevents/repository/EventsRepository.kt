package com.submissionandroid.dicodingevents.repository

import com.submissionandroid.dicodingevents.DetailEventResponse
import com.submissionandroid.dicodingevents.response.EventsResponse
import com.submissionandroid.dicodingevents.retrofit.ApiService

class EventsRepository(private val apiService: ApiService) {
    suspend fun getActiveEvents(): EventsResponse {
        return apiService.getActiveEvents()
    }

    suspend fun getFinishedEvents(): EventsResponse {
        return apiService.getFinishedEvents()
    }

    suspend fun searchEvents(keyword: String): EventsResponse {
        return apiService.searchEvents(keyword = keyword)
    }


    suspend fun getEventDetail(id: Int): DetailEventResponse {
        return apiService.getEventById(id)
    }
}
