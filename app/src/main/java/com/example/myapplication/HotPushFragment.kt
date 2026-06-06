package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HotPushFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var hotPushAdapter: HotPushAdapter
    private lateinit var prefManager: PrefManager
    private val hotPushList = mutableListOf<HotPushItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hot_push, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())

        initViews(view)
        setupRecyclerView()
        loadHotPushData()
    }

    override fun onResume() {
        super.onResume()
        loadHotPushData()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.hot_push_recycler_view)
    }

    private fun setupRecyclerView() {
        hotPushAdapter = HotPushAdapter(hotPushList) { hotPushItem ->
            openHotPushDetail(hotPushItem)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = hotPushAdapter
        }
    }

    private fun loadHotPushData() {
        val data = prefManager.getHotPushes()
        val sortedData = data.sortedWith(compareByDescending<HotPushItem> { it.isTop }
            .thenByDescending { it.pushTime })
        hotPushAdapter.updateData(sortedData)
    }

    private fun openHotPushDetail(hotPushItem: HotPushItem) {
        val intent = Intent(requireContext(), NewsDetailActivity::class.java)
        intent.putExtra("news_url", hotPushItem.newsUrl)
        intent.putExtra("news_title", hotPushItem.title)
        intent.putExtra("news_pic", hotPushItem.pic)
        intent.putExtra("news_category", "热推")
        intent.putExtra("news_id", hotPushItem.id)
        startActivity(intent)
    }
}
