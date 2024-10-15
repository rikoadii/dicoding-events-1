package com.submissionandroid.dicodingevents.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventsResponse(
	val listEvents: List<ListEventsItem?>? = null,
	val error: Boolean? = null,
	val message: String? = null
) : Parcelable

@Parcelize
data class ListEventsItem(
	val summary: String? = null,
	val mediaCover: String? = null,
	val registrants: Int? = null,
	val imageLogo: String? = null,
	val link: String? = null,
	val description: String? = null,
	val ownerName: String? = null,
	val cityName: String? = null,
	val quota: Int? = null,
	val name: String? = null,
	val id: Int? = null,
	val beginTime: String? = null,
	val endTime: String? = null,
	val category: String? = null
) : Parcelable

