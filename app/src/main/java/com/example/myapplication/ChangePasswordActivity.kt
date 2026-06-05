package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var etOldPassword: TextInputEditText
    private lateinit var etNewPassword: TextInputEditText
    private lateinit var etConfirmNewPassword: TextInputEditText
    private lateinit var btnChangePassword: MaterialButton
    private lateinit var toolbar: Toolbar
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        initViews()
        setupToolbar()
        setupListeners()

        prefManager = PrefManager(this)
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        etOldPassword = findViewById(R.id.etOldPassword)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword)
        btnChangePassword = findViewById(R.id.btnChangePassword)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupListeners() {
        btnChangePassword.setOnClickListener {
            val oldPassword = etOldPassword.text.toString().trim()
            val newPassword = etNewPassword.text.toString().trim()
            val confirmNewPassword = etConfirmNewPassword.text.toString().trim()

            if (validateInputs(oldPassword, newPassword, confirmNewPassword)) {
                changePassword(oldPassword, newPassword)
            }
        }
    }

    private fun validateInputs(oldPassword: String, newPassword: String, confirmNewPassword: String): Boolean {
        if (oldPassword.isEmpty()) {
            Toast.makeText(this, "请输入旧密码", Toast.LENGTH_SHORT).show()
            return false
        }
        if (newPassword.isEmpty()) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show()
            return false
        }
        if (newPassword.length < 6) {
            Toast.makeText(this, "新密码长度至少6位", Toast.LENGTH_SHORT).show()
            return false
        }
        if (newPassword != confirmNewPassword) {
            Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show()
            return false
        }
        if (oldPassword == newPassword) {
            Toast.makeText(this, "新密码不能与旧密码相同", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
        showLoadingDialog()

        val username = prefManager.getUsername()

        ApiClient.getInstance().changePassword(username, oldPassword, newPassword, object : ApiClient.ApiCallback {
            override fun onSuccess(response: String) {
                runOnUiThread {
                    hideLoadingDialog()
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.optBoolean("success", false)
                        val message = jsonObject.optString("message", "")

                        if (success) {
                            Toast.makeText(this@ChangePasswordActivity, message, Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@ChangePasswordActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@ChangePasswordActivity, "解析响应失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onError(error: String) {
                runOnUiThread {
                    hideLoadingDialog()
                    try {
                        val jsonObject = JSONObject(error)
                        val message = jsonObject.optString("message", "修改密码失败")
                        Toast.makeText(this@ChangePasswordActivity, message, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@ChangePasswordActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private var loadingDialog: AlertDialog? = null

    private fun showLoadingDialog() {
        loadingDialog = AlertDialog.Builder(this)
            .setMessage("修改中...")
            .setCancelable(false)
            .create()
        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }
}
