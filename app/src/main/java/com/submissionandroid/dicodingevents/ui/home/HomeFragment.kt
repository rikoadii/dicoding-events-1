// HomeFragment.kt
package com.submissionandroid.dicodingevents.ui.home

import com.submissionandroid.dicodingevents.ui.detail.DetailActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.submissionandroid.dicodingevents.EventsViewModel
import com.submissionandroid.dicodingevents.EventsViewModelFactory
import com.submissionandroid.dicodingevents.databinding.FragmentHomeBinding
import com.submissionandroid.dicodingevents.repository.EventsRepository
import com.submissionandroid.dicodingevents.retrofit.ApiConfig
import com.submissionandroid.dicodingevents.EventsAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventsViewModel: EventsViewModel
    private lateinit var activeEventsAdapter: EventsAdapter
    private lateinit var finishedEventsAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val repository = EventsRepository(ApiConfig.getApiService())
        val viewModelFactory = EventsViewModelFactory(repository)
        eventsViewModel = ViewModelProvider(this, viewModelFactory)[EventsViewModel::class.java]


        activeEventsAdapter = EventsAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("eventId", event.id)
            }
            startActivity(intent)
        }

        finishedEventsAdapter = EventsAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("eventId", event.id)
            }
            startActivity(intent)
        }


        binding.recyclerViewActiveEvents.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewFinishedEvents.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewActiveEvents.adapter = activeEventsAdapter
        binding.recyclerViewFinishedEvents.adapter = finishedEventsAdapter


        showLoading(true)


        eventsViewModel.fetchActiveEvents()
        eventsViewModel.fetchFinishedEvents()


        eventsViewModel.activeEvents.observe(viewLifecycleOwner) { response ->
            showLoading(false)
            response.listEvents?.let { events ->
                val limitedActiveEvents = events.take(5)
                activeEventsAdapter.updateEvents(limitedActiveEvents)
            }
        }


        eventsViewModel.finishedEvents.observe(viewLifecycleOwner) { response ->
            response.listEvents?.let { events ->
                val limitedFinishedEvents = events.take(5)
                finishedEventsAdapter.updateEvents(limitedFinishedEvents)
            }
        }


        eventsViewModel.errorMessage.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { errorMessage ->
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            }
        }

        return binding.root
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLoading.visibility = if (isLoading) VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
