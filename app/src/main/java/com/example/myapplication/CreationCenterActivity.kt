package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreationCenterActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_EDIT_CREATION = 1001
    }

    private lateinit var prefManager: PrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var adapter: CreationAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var fabAdd: FloatingActionButton
    private val creationList = mutableListOf<CreationItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creation_center)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefManager = PrefManager(this)

        initViews()
        loadCreations()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.creationsRecyclerView)
        emptyView = findViewById(R.id.emptyView)
        fabAdd = findViewById(R.id.fabAddCreation)

        adapter = CreationAdapter(
            creationList,
            onEditClickListener = { item, position ->
                openEditCreation(item, position)
            },
            onDeleteClickListener = { item, position ->
                deleteCreation(item, position)
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CreationCenterActivity)
            adapter = this@CreationCenterActivity.adapter
        }

        fabAdd.setOnClickListener {
            openEditCreation(null, -1)
        }
    }

    private fun loadCreations() {
        val creations = prefManager.getCreations()
        creationList.clear()
        creationList.addAll(creations)
        updateUI()
    }

    private fun updateUI() {
        if (creationList.isEmpty()) {
            recyclerView.visibility = android.view.View.GONE
            emptyView.visibility = android.view.View.VISIBLE
        } else {
            recyclerView.visibility = android.view.View.VISIBLE
            emptyView.visibility = android.view.View.GONE
        }
        adapter.notifyDataSetChanged()
    }

    private fun openEditCreation(item: CreationItem?, position: Int) {
        val intent = Intent(this, EditCreationActivity::class.java)
        if (item != null) {
            intent.putExtra(EditCreationActivity.EXTRA_CREATION_ID, item.id)
            intent.putExtra(EditCreationActivity.EXTRA_POSITION, position)
        }
        startActivityForResult(intent, REQUEST_CODE_EDIT_CREATION)
    }

    private fun deleteCreation(item: CreationItem, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("确认删除")
            .setMessage("确定要删除这篇创作吗？")
            .setPositiveButton("删除") { _, _ ->
                prefManager.deleteCreation(item.id)
                adapter.removeItem(position)
                updateUI()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDIT_CREATION && resultCode == RESULT_OK) {
            loadCreations()
        }
    }
}
