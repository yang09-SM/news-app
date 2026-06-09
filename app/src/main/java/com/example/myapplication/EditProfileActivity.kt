package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class EditProfileActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var prefManager: PrefManager
    private lateinit var avatarContainer: FrameLayout
    private lateinit var avatarImageView: ImageView
    private lateinit var avatarInitial: TextView
    private lateinit var nicknameEditText: EditText
    private lateinit var bioEditText: EditText
    private lateinit var bioCharCount: TextView
    private lateinit var verifyIdentityLayout: LinearLayout
    private lateinit var saveButton: Button
    
    private var tempImageUri: Uri? = null
    
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && tempImageUri != null) {
            handleImageResult(tempImageUri!!)
        }
    }
    
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { handleImageResult(it) }
    }
    
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "需要相机权限才能拍照", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "编辑资料"

        prefManager = PrefManager(this)

        initViews()
        loadUserData()
        setupListeners()
    }

    private fun initViews() {
        avatarContainer = findViewById(R.id.avatarContainer)
        avatarImageView = findViewById(R.id.avatarImageView)
        avatarInitial = findViewById(R.id.avatarInitial)
        nicknameEditText = findViewById(R.id.nicknameEditText)
        bioEditText = findViewById(R.id.bioEditText)
        bioCharCount = findViewById(R.id.bioCharCount)
        verifyIdentityLayout = findViewById(R.id.verifyIdentityLayout)
        saveButton = findViewById(R.id.saveButton)
    }

    private fun loadUserData() {
        val nickname = prefManager.getNickname()
        val bio = prefManager.getUserBio()
        
        if (nickname.isNotEmpty()) {
            nicknameEditText.setText(nickname)
        }
        
        if (bio.isNotEmpty()) {
            bioEditText.setText(bio)
            bioCharCount.text = "${bio.length}/100"
        }
        
        loadAvatar()
    }

    private fun setupListeners() {
        avatarContainer.setOnClickListener {
            showAvatarPickerDialog()
        }
        
        bioEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                bioCharCount.text = "${s?.length ?: 0}/100"
            }
        })
        
        verifyIdentityLayout.setOnClickListener {
            showVerifyDialog()
        }
        
        saveButton.setOnClickListener {
            saveProfile()
        }
    }

    private fun showAvatarPickerDialog() {
        val options = arrayOf("拍照", "从相册选择")
        AlertDialog.Builder(this)
            .setTitle("选择头像")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> checkCameraPermissionAndOpenCamera()
                    1 -> openGallery()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }
    
    private fun checkCameraPermissionAndOpenCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                    openCamera()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        } else {
            openCamera()
        }
    }
    
    private fun openCamera() {
        try {
            val tempFile = createTempImageFile()
            tempImageUri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                tempFile
            )
            cameraLauncher.launch(tempImageUri)
        } catch (e: Exception) {
            Toast.makeText(this, "无法打开相机", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }
    
    private fun createTempImageFile(): File {
        val storageDir = externalCacheDir
        return File.createTempFile("JPEG_${System.currentTimeMillis()}_", ".jpg", storageDir)
    }
    
    private fun handleImageResult(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            val compressedBitmap = compressBitmap(bitmap)
            val savedPath = saveBitmapToInternalStorage(compressedBitmap)
            
            prefManager.saveAvatarPath(savedPath)
            loadAvatar()
            
            Toast.makeText(this, "头像已更新", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "处理图片失败", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun compressBitmap(bitmap: Bitmap): Bitmap {
        var width = bitmap.width
        var height = bitmap.height
        val maxSize = 400
        
        if (width > maxSize || height > maxSize) {
            val ratio = width.toFloat() / height.toFloat()
            if (ratio > 1) {
                width = maxSize
                height = (maxSize / ratio).toInt()
            } else {
                height = maxSize
                width = (maxSize * ratio).toInt()
            }
        }
        
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }
    
    private fun saveBitmapToInternalStorage(bitmap: Bitmap): String {
        val fileName = "avatar_${System.currentTimeMillis()}.jpg"
        val file = File(filesDir, fileName)
        
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        outputStream.flush()
        outputStream.close()
        
        return file.absolutePath
    }
    
    private fun loadAvatar() {
        val avatarPath = prefManager.getAvatarPath()
        if (avatarPath.isNotEmpty()) {
            val file = File(avatarPath)
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(avatarPath)
                avatarImageView.setImageBitmap(bitmap)
                avatarImageView.visibility = android.view.View.VISIBLE
                avatarInitial.visibility = android.view.View.GONE
                return
            }
        }
        
        val username = prefManager.getNickname().ifEmpty { prefManager.getUsername() }
        val initial = if (username.isNotEmpty()) username.first().uppercase() else "U"
        avatarInitial.text = initial
        avatarImageView.visibility = android.view.View.GONE
        avatarInitial.visibility = android.view.View.VISIBLE
    }

    private fun showVerifyDialog() {
        AlertDialog.Builder(this)
            .setTitle("实名认证")
            .setMessage("实名认证功能即将上线，敬请期待！")
            .setPositiveButton("确定", null)
            .show()
    }

    private fun saveProfile() {
        val nickname = nicknameEditText.text.toString().trim()
        val bio = bioEditText.text.toString().trim()
        
        if (nickname.isNotEmpty()) {
            prefManager.saveNickname(nickname)
        }
        
        prefManager.saveUserBio(bio)
        
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
