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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileFragment : Fragment() {

    private lateinit var prefManager: PrefManager
    private lateinit var loggedInLayout: LinearLayout
    private lateinit var notLoggedInLayout: LinearLayout
    private lateinit var usernameTextView: TextView
    private lateinit var avatarImageView: ImageView
    private lateinit var avatarContainer: View
    private lateinit var avatarInitial: TextView
    private lateinit var favoritesMenuItem: View
    private lateinit var historyMenuItem: View
    private lateinit var hotRecommendMenuItem: View
    private lateinit var messageMenuItem: View
    private lateinit var cashRewardMenuItem: View
    private lateinit var pointsCenterMenuItem: View
    private lateinit var pointsShopMenuItem: View
    private lateinit var achievementMenuItem: View
    private lateinit var groupChatMenuItem: View
    private lateinit var activityCenterMenuItem: View
    private lateinit var creationCenterMenuItem: View
    private lateinit var myReportMenuItem: View
    private lateinit var changePasswordView: View
    private lateinit var aboutView: View
    private lateinit var darkModeMenuItem: View
    private lateinit var darkModeValueText: TextView
    private lateinit var logoutView: Button
    private lateinit var loginButton: Button
    private lateinit var checkInButton: Button
    private lateinit var pointsTextView: TextView
    private lateinit var followingCountTextView: TextView
    private lateinit var followersCountTextView: TextView
    private lateinit var friendsCountTextView: TextView
    private lateinit var likesCountTextView: TextView
    
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
            Toast.makeText(requireContext(), "需要相机权限才能拍照", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())
        prefManager.initializeMockData()

        initViews(view)
        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun initViews(view: View) {
        loggedInLayout = view.findViewById(R.id.loggedInLayout)
        notLoggedInLayout = view.findViewById(R.id.notLoggedInLayout)
        usernameTextView = view.findViewById(R.id.usernameTextView)
        avatarImageView = view.findViewById(R.id.avatarImageView)
        avatarContainer = view.findViewById(R.id.avatarContainer)
        avatarInitial = view.findViewById(R.id.avatarInitial)
        favoritesMenuItem = view.findViewById(R.id.favoritesMenuItem)
        historyMenuItem = view.findViewById(R.id.historyMenuItem)
        hotRecommendMenuItem = view.findViewById(R.id.hotRecommendMenuItem)
        messageMenuItem = view.findViewById(R.id.messageMenuItem)
        cashRewardMenuItem = view.findViewById(R.id.cashRewardMenuItem)
        pointsCenterMenuItem = view.findViewById(R.id.pointsCenterMenuItem)
        pointsShopMenuItem = view.findViewById(R.id.pointsShopMenuItem)
        achievementMenuItem = view.findViewById(R.id.achievementMenuItem)
        groupChatMenuItem = view.findViewById(R.id.groupChatMenuItem)
        activityCenterMenuItem = view.findViewById(R.id.activityCenterMenuItem)
        creationCenterMenuItem = view.findViewById(R.id.creationCenterMenuItem)
        myReportMenuItem = view.findViewById(R.id.myReportMenuItem)
        changePasswordView = view.findViewById(R.id.changePasswordView)
        aboutView = view.findViewById(R.id.aboutView)
        darkModeMenuItem = view.findViewById(R.id.darkModeMenuItem)
        darkModeValueText = view.findViewById(R.id.darkModeValueText)
        logoutView = view.findViewById(R.id.logoutView)
        loginButton = view.findViewById(R.id.loginButton)
        checkInButton = view.findViewById(R.id.checkInButton)
        pointsTextView = view.findViewById(R.id.pointsTextView)
        followingCountTextView = view.findViewById(R.id.followingCountTextView)
        followersCountTextView = view.findViewById(R.id.followersCountTextView)
        friendsCountTextView = view.findViewById(R.id.friendsCountTextView)
        likesCountTextView = view.findViewById(R.id.likesCountTextView)
        
        avatarContainer.setOnClickListener {
            showAvatarPickerDialog()
        }
    }

    private fun updateUI() {
        if (prefManager.isLoggedIn()) {
            loggedInLayout.visibility = View.VISIBLE
            notLoggedInLayout.visibility = View.GONE
            setupLoggedInViews()
        } else {
            loggedInLayout.visibility = View.GONE
            notLoggedInLayout.visibility = View.VISIBLE
            setupNotLoggedInViews()
        }
    }

    private fun setupLoggedInViews() {
        val username = prefManager.getUsername() ?: "用户"
        usernameTextView.text = username
        
        val initial = if (username.isNotEmpty()) username.first().uppercase() else "U"
        avatarInitial.text = initial
        
        loadAvatar()

        initializeUserStats()
        updatePointsDisplay()
        updateUserStatsDisplay()
        updateCheckInButton()

        favoritesMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), FavoritesActivity::class.java)
            startActivity(intent)
        }

        historyMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), BrowsingHistoryActivity::class.java)
            startActivity(intent)
        }

        hotRecommendMenuItem.setOnClickListener {
            val activity = requireActivity() as? HomeActivity
            activity?.switchToHotPushFragment()
        }

        messageMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), MessageListActivity::class.java)
            startActivity(intent)
        }

        cashRewardMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), CashRewardActivity::class.java)
            startActivity(intent)
        }

        pointsCenterMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), PointsCenterActivity::class.java)
            startActivity(intent)
        }

        pointsShopMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), PointsMallActivity::class.java)
            startActivity(intent)
        }

        achievementMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), AchievementsActivity::class.java)
            startActivity(intent)
        }

        groupChatMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), GroupListActivity::class.java)
            startActivity(intent)
        }

        activityCenterMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), ActivityCenterActivity::class.java)
            startActivity(intent)
        }

        creationCenterMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), CreationCenterActivity::class.java)
            startActivity(intent)
        }

        myReportMenuItem.setOnClickListener {
            val intent = Intent(requireContext(), MyReportsActivity::class.java)
            startActivity(intent)
        }

        changePasswordView.setOnClickListener {
            val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        aboutView.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }

        updateDarkModeDisplay()
        darkModeMenuItem.setOnClickListener {
            showNightModeDialog()
        }

        checkInButton.setOnClickListener {
            performCheckIn()
        }

        logoutView.setOnClickListener {
            showLogoutConfirmDialog()
        }
    }

    private fun setupNotLoggedInViews() {
        loginButton.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeUserStats() {
        val following = prefManager.getFollowingCount()
        val followers = prefManager.getFollowersCount()
        val friends = prefManager.getFriendsCount()
        val likes = prefManager.getLikesCount()
        
        if (following == 0 && followers == 0 && friends == 0 && likes == 0) {
            prefManager.saveUserStats(128, 256, 64, 1200)
        }
    }

    private fun updatePointsDisplay() {
        val points = prefManager.getPoints()
        pointsTextView.text = "$points 积分"
    }

    private fun updateUserStatsDisplay() {
        followingCountTextView.text = prefManager.getFollowingCount().toString()
        followersCountTextView.text = prefManager.getFollowersCount().toString()
        friendsCountTextView.text = prefManager.getFriendsCount().toString()
        
        val likes = prefManager.getLikesCount()
        likesCountTextView.text = if (likes >= 1000) {
            String.format("%.1fk", likes / 1000.0)
        } else {
            likes.toString()
        }
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun updateCheckInButton() {
        val today = getTodayDate()
        if (prefManager.isCheckedInToday(today)) {
            checkInButton.text = "已签到"
            checkInButton.isEnabled = false
            checkInButton.alpha = 0.5f
        } else {
            checkInButton.text = "每日签到"
            checkInButton.isEnabled = true
            checkInButton.alpha = 1.0f
        }
    }

    private fun performCheckIn() {
        val today = getTodayDate()
        
        if (prefManager.isCheckedInToday(today)) {
            showAlreadyCheckedInDialog()
            return
        }

        val currentPoints = prefManager.getPoints()
        val newPoints = currentPoints + 10
        prefManager.savePoints(newPoints)
        prefManager.saveCheckInDate(today)

        updatePointsDisplay()
        updateCheckInButton()
        showCheckInSuccessDialog(newPoints)
    }

    private fun showCheckInSuccessDialog(newPoints: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("签到成功")
            .setMessage("恭喜获得 +10 积分！\n当前积分：$newPoints")
            .setPositiveButton("确定", null)
            .show()
    }

    private fun showAlreadyCheckedInDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("提示")
            .setMessage("您今天已经签到过了，明天再来吧！")
            .setPositiveButton("确定", null)
            .show()
    }

    private fun showLogoutConfirmDialog() {
        AlertDialog.Builder(requireContext())
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
        updateUI()
    }
    
    private fun showAvatarPickerDialog() {
        val options = arrayOf("拍照", "从相册选择")
        AlertDialog.Builder(requireContext())
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
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
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
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                tempFile
            )
            cameraLauncher.launch(tempImageUri)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "无法打开相机", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }
    
    private fun createTempImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireContext().externalCacheDir
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
    
    private fun handleImageResult(uri: Uri) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            val compressedBitmap = compressBitmap(bitmap)
            val savedPath = saveBitmapToInternalStorage(compressedBitmap)
            
            prefManager.saveAvatarPath(savedPath)
            loadAvatar()
            
            Toast.makeText(requireContext(), "头像已更新", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "处理图片失败", Toast.LENGTH_SHORT).show()
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
        val file = File(requireContext().filesDir, fileName)
        
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
                avatarImageView.visibility = View.VISIBLE
                avatarInitial.visibility = View.GONE
                return
            }
        }
        avatarImageView.visibility = View.GONE
        avatarInitial.visibility = View.VISIBLE
    }

    private fun updateDarkModeDisplay() {
        val mode = prefManager.getNightMode()
        darkModeValueText.text = when (mode) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> "跟随系统"
            AppCompatDelegate.MODE_NIGHT_NO -> "浅色模式"
            AppCompatDelegate.MODE_NIGHT_YES -> "深色模式"
            else -> "跟随系统"
        }
    }

    private fun showNightModeDialog() {
        val currentMode = prefManager.getNightMode()
        val checkedItem = when (currentMode) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> 0
            AppCompatDelegate.MODE_NIGHT_NO -> 1
            AppCompatDelegate.MODE_NIGHT_YES -> 2
            else -> 0
        }
        val options = arrayOf("跟随系统", "浅色模式", "深色模式")
        AlertDialog.Builder(requireContext())
            .setTitle("深色模式")
            .setSingleChoiceItems(options, checkedItem) { dialog, which ->
                val selectedMode = when (which) {
                    0 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    1 -> AppCompatDelegate.MODE_NIGHT_NO
                    2 -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                AppCompatDelegate.setDefaultNightMode(selectedMode)
                prefManager.saveNightMode(selectedMode)
                darkModeValueText.text = options[which]
                dialog.dismiss()
                requireActivity().recreate()
            }
            .setNegativeButton("取消", null)
            .show()
    }
}
