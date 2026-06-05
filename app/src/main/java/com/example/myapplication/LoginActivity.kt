package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var tvRegister: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        setupToolbar()
        setupListeners()

        prefManager = PrefManager(this)
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (validateInputs(username, password)) {
                login(username, password)
            }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInputs(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun login(username: String, password: String) {
        showLoadingDialog()

        ApiClient.getInstance().login(username, password, object : ApiClient.ApiCallback {
            override fun onSuccess(response: String) {
                runOnUiThread {
                    hideLoadingDialog()
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.optBoolean("success", false)
                        val message = jsonObject.optString("message", "")

                        if (success) {
                            val user = jsonObject.optJSONObject("user")
                            val userId = user?.optString("id", "") ?: ""
                            prefManager.saveLoginState(true, username, userId)

                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@LoginActivity, "解析响应失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onError(error: String) {
                runOnUiThread {
                    hideLoadingDialog()
                    try {
                        val jsonObject = JSONObject(error)
                        val message = jsonObject.optString("message", "登录失败")
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@LoginActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private var loadingDialog: AlertDialog? = null

    private fun showLoadingDialog() {
        loadingDialog = AlertDialog.Builder(this)
            .setMessage("登录中...")
            .setCancelable(false)
            .create()
        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }
}
