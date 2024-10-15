package com.submissionandroid.dicodingevents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submissionandroid.dicodingevents.response.ListEventsItem

class EventsAdapter(
    private var events: List<ListEventsItem> = emptyList(),
    private val onClick: (ListEventsItem) -> Unit
) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImage: ImageView = view.findViewById(R.id.ivEventImage)
        val eventName: TextView = view.findViewById(R.id.tvEventName)
        val eventDate: TextView = view.findViewById(R.id.tvEventDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_layout, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.eventName.text = event.name
        holder.eventDate.text = event.beginTime


        Glide.with(holder.itemView.context)
            .load(event.imageLogo)
            .into(holder.eventImage)


        holder.itemView.setOnClickListener {
            onClick(event)
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateEvents(newEvents: List<ListEventsItem?>) {
        events = newEvents.filterNotNull()
        notifyDataSetChanged()
    }
}
