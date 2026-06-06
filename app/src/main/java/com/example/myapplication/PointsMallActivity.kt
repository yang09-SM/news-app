package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PointsMallActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var pointsTextView: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: ProductAdapter
    private val productList = mutableListOf<ProductItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points_mall)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        prefManager = PrefManager(this)

        initViews()
        loadData()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.productsRecyclerView)
        emptyView = findViewById(R.id.emptyView)
        pointsTextView = findViewById(R.id.pointsTextView)

        adapter = ProductAdapter(
            productList,
            onExchangeClickListener = { productItem ->
                handleExchange(productItem)
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PointsMallActivity)
            adapter = this@PointsMallActivity.adapter
        }
    }

    private fun loadData() {
        val points = prefManager.getPoints()
        pointsTextView.text = points.toString()

        val products = prefManager.getProducts()
        productList.clear()
        productList.addAll(products)
        updateUI()
    }

    private fun updateUI() {
        if (productList.isEmpty()) {
            recyclerView.visibility = android.view.View.GONE
            emptyView.visibility = android.view.View.VISIBLE
        } else {
            recyclerView.visibility = android.view.View.VISIBLE
            emptyView.visibility = android.view.View.GONE
        }
        adapter.notifyDataSetChanged()
    }

    private fun handleExchange(productItem: ProductItem) {
        val currentPoints = prefManager.getPoints()
        
        if (currentPoints < productItem.points) {
            Toast.makeText(this, "积分不足，无法兑换", Toast.LENGTH_SHORT).show()
            return
        }

        if (productItem.stock <= 0) {
            Toast.makeText(this, "商品库存不足", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(this)
            .setTitle("确认兑换")
            .setMessage("确定要使用 ${productItem.points} 积分兑换 ${productItem.name} 吗？")
            .setPositiveButton("确定") { _, _ ->
                executeExchange(productItem)
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun executeExchange(productItem: ProductItem) {
        val currentPoints = prefManager.getPoints()
        val newPoints = currentPoints - productItem.points
        prefManager.savePoints(newPoints)

        val newRecord = ExchangeRecord(
            id = System.currentTimeMillis().toString(),
            productId = productItem.id,
            productName = productItem.name,
            productPic = productItem.pic,
            points = productItem.points,
            exchangeTime = System.currentTimeMillis(),
            status = ExchangeStatus.COMPLETED
        )
        prefManager.addExchangeRecord(newRecord)

        Toast.makeText(this, "兑换成功", Toast.LENGTH_SHORT).show()
        loadData()
    }
}
