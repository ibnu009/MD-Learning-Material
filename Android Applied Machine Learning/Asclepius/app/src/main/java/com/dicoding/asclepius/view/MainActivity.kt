package com.dicoding.asclepius.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.utils.uriToBase64
import com.dicoding.asclepius.view.base.BaseActivity
import com.dicoding.asclepius.view.history.HistoryActivity
import com.dicoding.asclepius.view.result.ResultActivity
import com.dicoding.asclepius.view.webview.WebViewActivity
import com.yalantis.ucrop.UCrop
import timber.log.Timber
import java.io.File

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var currentImageUri: Uri? = null
    private var currentLabel: String? = null
    private var currentConfidenceScore: String? = null

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initUI() {
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
    }

    override fun initActions() {
        binding.apply {
            galleryButton.setOnClickListener {
                startGallery()
            }

            analyzeButton.setOnClickListener {
                analyzeImage()
            }

            historyButton.setOnClickListener {
                moveToHistory()
            }
        }
    }

    override fun initProcess() {}

    override fun initObservers() {}

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startUCrop(sourceUri: Uri) {
        val destinationUri = Uri.fromFile(
            File(this.cacheDir, "my_cropped_image_${System.currentTimeMillis()}.jpg")
        )
        val uCropOptions = UCrop.Options()
        uCropOptions.setCompressionFormat(Bitmap.CompressFormat.JPEG)
        UCrop.of(sourceUri, destinationUri)
            .withOptions(uCropOptions)
            .start(this)
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            startUCrop(uri)
        } else {
            Timber.tag("Photo Picker").d("No media selected")
        }
    }

    private fun showImage() {
        binding.apply {
            previewImageView.setImageURI(currentImageUri)
        }
    }

    private fun analyzeImage() {
        if (currentImageUri == null) {
            showToast("Belum ada gambar")
            return
        }

        ImageClassifierHelper(
            context = this,
            onSuccess = { label, confidenceScore ->
                currentLabel = label
                currentConfidenceScore = confidenceScore
                moveToResult()
            },
            onFailed = { showToast(it) }
        ).classifyStaticImage(this, currentImageUri!!)
    }

    private fun moveToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_LABEL, currentLabel)
        intent.putExtra(ResultActivity.EXTRA_CONFIDENCE_SCORE, currentConfidenceScore)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_BASE_64, currentImageUri?.uriToBase64(this))
        startActivity(intent)
    }

    private fun moveToHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                UCrop.REQUEST_CROP -> {
                    val croppedImageUri = UCrop.getOutput(data!!)
                    currentImageUri = croppedImageUri
                    showImage()
                }
            }
        }
    }
}