package com.dicoding.asclepius.helper

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yalantis.ucrop.UCrop
import java.io.File

private const val PERMISSION_CODE = 101

class ImageFileFinder : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Permission request granted")
            } else {
                showToast("Permission request denied")
            }
        }

    fun pickImageFromGallery(activity: AppCompatActivity) {
        if (ContextCompat.checkSelfPermission(
                activity,
                READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("ImageFileFinder", "Here 1")
            launchGalleryIntent(activity)
        } else {
            Log.d("ImageFileFinder", "Here 2")
            requestStoragePermission(activity)
        }
    }

    private fun requestStoragePermission(activity: AppCompatActivity) {
        ActivityCompat.requestPermissions(activity, arrayOf(READ_EXTERNAL_STORAGE), PERMISSION_CODE)
    }

    private fun launchGalleryIntent(activity: AppCompatActivity) {
        val galleryActivityResultLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let { startUCrop(activity, it) }
            }
        }
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private fun startUCrop(activity: AppCompatActivity, sourceUri: Uri) {
        val destinationUri = Uri.fromFile(
            File(activity.cacheDir, "my_cropped_image_${System.currentTimeMillis()}.jpg")
        )
        val uCropOptions = UCrop.Options()
        uCropOptions.setCompressionFormat(Bitmap.CompressFormat.JPEG)
        UCrop.of(sourceUri, destinationUri)
            .withOptions(uCropOptions)
            .start(activity)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}