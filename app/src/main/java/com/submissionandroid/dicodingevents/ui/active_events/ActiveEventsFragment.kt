package com.submissionandroid.dicodingevents.ui.active_events

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import com.google.android.material.snackbar.Snackbar
import com.submissionandroid.dicodingevents.EventsViewModel
import com.submissionandroid.dicodingevents.EventsViewModelFactory
import com.submissionandroid.dicodingevents.databinding.FragmentActiveEventsBinding
import com.submissionandroid.dicodingevents.repository.EventsRepository
import com.submissionandroid.dicodingevents.retrofit.ApiConfig
import com.submissionandroid.dicodingevents.EventsAdapter
import com.submissionandroid.dicodingevents.ui.detail.DetailActivity

class ActiveEventsFragment : Fragment() {

    private var _binding: FragmentActiveEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventsViewModel: EventsViewModel
    private lateinit var activeEventsAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveEventsBinding.inflate(inflater, container, false)

        val repository = EventsRepository(ApiConfig.getApiService())
        val viewModelFactory = EventsViewModelFactory(repository)
        eventsViewModel = ViewModelProvider(this, viewModelFactory)[EventsViewModel::class.java]

        activeEventsAdapter = EventsAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("eventId", event.id)
            }
            startActivity(intent)
        }

        binding.recyclerViewActiveEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewActiveEvents.adapter = activeEventsAdapter

        showLoading(true)

        eventsViewModel.fetchActiveEvents()

        eventsViewModel.activeEvents.observe(viewLifecycleOwner) { response ->
            showLoading(false)
            response.listEvents?.let { events ->
                activeEventsAdapter.updateEvents(events)
            }
        }

        eventsViewModel.errorMessage.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { errorMessage ->
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
            }
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    showLoading(true)
                    eventsViewModel.searchEvents(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    showLoading(true)
                    eventsViewModel.searchEvents(it)
                }
                return true
            }
        })

        eventsViewModel.searchedEvents.observe(viewLifecycleOwner) { response ->
            showLoading(false)
            response.listEvents?.let { events ->
                activeEventsAdapter.updateEvents(events)
            }
        }

        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
