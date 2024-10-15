package com.submissionandroid.dicodingevents.retrofit

import com.submissionandroid.dicodingevents.DetailEventResponse
import com.submissionandroid.dicodingevents.response.EventsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getActiveEvents(
        @Query("active") active: Int = 1
    ): EventsResponse

    @GET("events")
    suspend fun getFinishedEvents(
        @Query("active") active: Int = 0
    ): EventsResponse

    @GET("events")
    suspend fun searchEvents(
        @Query("active") active: Int = -1,
        @Query("q") keyword: String
    ): EventsResponse


    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): DetailEventResponse
}
