package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class TopicFragment : Fragment() {

    private lateinit var prefManager: PrefManager
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        prefManager = PrefManager(requireContext())
        
        initViews(view)
        setupRecyclerView()
        loadTopics()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        
        swipeRefreshLayout.setOnRefreshListener {
            loadTopics()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        topicAdapter = TopicAdapter(emptyList()) { topic ->
            val intent = Intent(requireContext(), TopicDetailActivity::class.java).apply {
                putExtra(TopicDetailActivity.EXTRA_TOPIC_ID, topic.id)
            }
            startActivity(intent)
        }
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topicAdapter
        }
    }

    private fun loadTopics() {
        prefManager.initializeMockData()
        val topics = prefManager.getTopics()
        topicAdapter.updateData(topics)
    }
}
