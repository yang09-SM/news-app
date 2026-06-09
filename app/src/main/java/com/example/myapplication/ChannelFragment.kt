package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class ChannelFragment : Fragment() {

    private lateinit var prefManager: PrefManager
    private lateinit var channelAdapter: ChannelAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        prefManager = PrefManager(requireContext())
        
        initViews(view)
        setupRecyclerView()
        loadChannels()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        
        swipeRefreshLayout.setOnRefreshListener {
            loadChannels()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        channelAdapter = ChannelAdapter(emptyList()) { channel ->
            val updatedChannels = prefManager.toggleChannelSubscription(channel.id)
            channelAdapter.updateData(updatedChannels)
        }
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = channelAdapter
        }
    }

    private fun loadChannels() {
        prefManager.initializeMockData()
        val channels = prefManager.getChannels()
        channelAdapter.updateData(channels)
    }
}
