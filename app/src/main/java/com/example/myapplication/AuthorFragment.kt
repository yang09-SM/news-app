package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class AuthorFragment : Fragment() {

    private lateinit var prefManager: PrefManager
    private lateinit var authorAdapter: AuthorAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_author, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        prefManager = PrefManager(requireContext())
        
        initViews(view)
        setupRecyclerView()
        loadAuthors()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        
        swipeRefreshLayout.setOnRefreshListener {
            loadAuthors()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        authorAdapter = AuthorAdapter(emptyList()) { author ->
            val updatedAuthors = prefManager.toggleAuthorFollow(author.id)
            authorAdapter.updateData(updatedAuthors)
        }
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = authorAdapter
        }
    }

    private fun loadAuthors() {
        prefManager.initializeMockData()
        val authors = prefManager.getAuthors()
        authorAdapter.updateData(authors)
    }
}
