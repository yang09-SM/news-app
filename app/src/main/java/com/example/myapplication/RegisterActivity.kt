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

class RegisterActivity : AppCompatActivity() {
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var btnRegister: MaterialButton
    private lateinit var tvLogin: TextView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initViews()
        setupToolbar()
        setupListeners()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupListeners() {
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (validateInputs(username, password, confirmPassword)) {
                register(username, password)
            }
        }

        tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateInputs(username: String, password: String, confirmPassword: String): Boolean {
        if (username.isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, "密码长度至少6位", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun register(username: String, password: String) {
        showLoadingDialog()

        ApiClient.getInstance(this).register(username, password, object : ApiClient.ApiCallback {
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
                            val token = jsonObject.optString("token", "")
                            val refreshToken = jsonObject.optString("refreshToken", "")
                            val prefManager = PrefManager(this@RegisterActivity)
                            prefManager.saveLoginState(true, username, userId)
                            if (token.isNotEmpty()) prefManager.saveAuthToken(token)
                            if (refreshToken.isNotEmpty()) prefManager.saveRefreshToken(refreshToken)
                            Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@RegisterActivity, "解析响应失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onError(error: String) {
                runOnUiThread {
                    hideLoadingDialog()
                    try {
                        val jsonObject = JSONObject(error)
                        val message = jsonObject.optString("message", "注册失败")
                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@RegisterActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private var loadingDialog: AlertDialog? = null

    private fun showLoadingDialog() {
        loadingDialog = AlertDialog.Builder(this)
            .setMessage("注册中...")
            .setCancelable(false)
            .create()
        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }
}
