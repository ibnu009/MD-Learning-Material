package com.dicoding.asclepius.view.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import timber.log.Timber

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(_binding?.root)

        initIntent()
        initUI()
        initAdapter()
        initActions()
        initProcess()
        initObservers()
    }

    abstract fun getViewBinding(): VB

    abstract fun initUI()

    abstract fun initProcess()

    abstract fun initObservers()

    protected open fun initIntent() {}

    protected open fun initAdapter() {}

    protected open fun initActions() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    internal fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    open fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

   open val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Timber.tag("Permission").d("Permission granted")
            } else {
                Timber.tag("Permission").d("Permission denied")
            }
        }

    companion object {
        internal const val REQUIRED_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
    }
}