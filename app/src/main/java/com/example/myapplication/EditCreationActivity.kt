package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import java.util.UUID

class EditCreationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CREATION_ID = "extra_creation_id"
        const val EXTRA_POSITION = "extra_position"
    }

    private lateinit var prefManager: PrefManager
    private lateinit var toolbar: Toolbar
    private lateinit var etTitle: TextInputEditText
    private lateinit var etContent: TextInputEditText
    private lateinit var etCategory: TextInputEditText
    private lateinit var rgStatus: RadioGroup
    private lateinit var rbDraft: RadioButton
    private lateinit var rbPublished: RadioButton
    private lateinit var rbReviewing: RadioButton
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private var creationId: String? = null
    private var isEditing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_creation)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefManager = PrefManager(this)

        initViews()
        checkIntent()
    }

    private fun initViews() {
        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        etCategory = findViewById(R.id.etCategory)
        rgStatus = findViewById(R.id.rgStatus)
        rbDraft = findViewById(R.id.rbDraft)
        rbPublished = findViewById(R.id.rbPublished)
        rbReviewing = findViewById(R.id.rbReviewing)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        btnSave.setOnClickListener {
            saveCreation()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun checkIntent() {
        creationId = intent.getStringExtra(EXTRA_CREATION_ID)
        if (creationId != null) {
            isEditing = true
            loadCreation()
            supportActionBar?.title = "编辑创作"
        } else {
            supportActionBar?.title = "新建创作"
            rbDraft.isChecked = true
        }
    }

    private fun loadCreation() {
        val creations = prefManager.getCreations()
        val creation = creations.find { it.id == creationId }
        creation?.let {
            etTitle.setText(it.title)
            etContent.setText(it.content)
            etCategory.setText(it.category)
            
            when (it.status) {
                CreationStatus.DRAFT -> rbDraft.isChecked = true
                CreationStatus.PUBLISHED -> rbPublished.isChecked = true
                CreationStatus.REVIEWING -> rbReviewing.isChecked = true
                CreationStatus.REJECTED -> rbDraft.isChecked = true
            }
        }
    }

    private fun saveCreation() {
        val title = etTitle.text.toString().trim()
        val content = etContent.text.toString().trim()
        val category = etCategory.text.toString().trim()

        if (title.isEmpty()) {
            etTitle.error = "请输入标题"
            return
        }

        if (content.isEmpty()) {
            etContent.error = "请输入内容"
            return
        }

        val status = when {
            rbPublished.isChecked -> CreationStatus.PUBLISHED
            rbReviewing.isChecked -> CreationStatus.REVIEWING
            else -> CreationStatus.DRAFT
        }

        if (isEditing && creationId != null) {
            val existingCreations = prefManager.getCreations()
            val existingCreation = existingCreations.find { it.id == creationId }
            existingCreation?.let {
                val updatedCreation = it.copy(
                    title = title,
                    content = content,
                    category = category.ifEmpty { "未分类" },
                    status = status
                )
                prefManager.updateCreation(updatedCreation)
            }
        } else {
            val newCreation = CreationItem(
                id = UUID.randomUUID().toString(),
                title = title,
                content = content,
                images = emptyList(),
                category = category.ifEmpty { "未分类" },
                createTime = System.currentTimeMillis(),
                viewCount = 0,
                likeCount = 0,
                commentCount = 0,
                status = status
            )
            prefManager.addCreation(newCreation)
        }

        setResult(RESULT_OK)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
