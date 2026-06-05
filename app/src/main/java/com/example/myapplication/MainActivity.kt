package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var prefManager: PrefManager
    private val tabTitles = listOf("头条新闻", "体育新闻", "科技新闻")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefManager = PrefManager(this)

        if (!prefManager.isLoggedIn()) {
            navigateToLogin()
            return
        }

        initViews()
        setupToolbar()
        setupViewPager()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_change_password -> {
                val intent = Intent(this, ChangePasswordActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_logout -> {
                showLogoutConfirmDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun showLogoutConfirmDialog() {
        AlertDialog.Builder(this)
            .setTitle("退出登录")
            .setMessage("确定要退出登录吗？")
            .setPositiveButton("确定") { _, _ ->
                logout()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun logout() {
        prefManager.clearLoginState()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
