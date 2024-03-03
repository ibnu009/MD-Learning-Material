package com.ibnu.distory.presentation.add

import android.Manifest
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ibnu.distory.R
import com.ibnu.distory.base.BaseLocationFragment
import com.ibnu.distory.databinding.FragmentAddStoryBinding
import com.ibnu.distory.utils.helper.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.android.ext.android.inject
import java.io.File

class AddStoryFragment : BaseLocationFragment<FragmentAddStoryBinding>() {

    private val viewModel: AddStoryViewModel by inject()

    private var imageFile: File? = null
    private var mUri: Uri? = null
    private var myLocation: Location? = null
    private var allowLocation: Boolean = false

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddStoryBinding {
        return FragmentAddStoryBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.toolbarAccount.apply {
            title = context.getString(R.string.title_add_story)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        mUri?.let {
            binding.imgStoryHolder.gone()
            binding.imgStory.show()
            binding.imgStory.setImageURI(it)
        }
    }

    override fun initActions() {
        binding.apply {
            imgStoryHolder.popClick {
                initTakePicture()
            }

            imgStory.popClick {
                initTakePicture()
            }

            btnUploadStory.popClick {
                uploadStory()
            }

            switchLocation.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    allowLocation = true
                    layoutPosition.show()
                } else {
                    allowLocation = false
                    layoutPosition.gone()
                }
            }

            btnGetLocation.popClick {
                initPermissionAndGetLocation()
                getLocation {
                    myLocation = it
                    binding.apply {
                        tvLatitude.text = it.latitude.toString()
                        tvLongitude.text = it.longitude.toString()
                    }
                }
            }
        }
    }

    override fun initProcess() {}

    override fun initObservers() {
        viewModel.uploadResult.observeSingleEvent(
            viewLifecycleOwner,
            loading = {
                binding.layoutLoading.root.show()
            },
            success = {
                binding.layoutLoading.root.gone()
                showOKDialog(
                    title = getString(R.string.title_success),
                    message = getString(R.string.message_success_upload_story),
                    onYes = {
                        setFragmentResult(
                            IS_UPLOAD_SUCCESS_KEY,
                            bundleOf(IS_UPLOAD_SUCCESS_BUNDLE to true)
                        )
                        findNavController().navigateUp()
                    }
                )
            },
            error = { response ->
                binding.layoutLoading.root.gone()
                binding.root.showSnackBar(response.errorMessage)
            },
        )
    }

    private fun uploadStory() {
        binding.apply {
            val storyDescription = edtDescription.text.toString()
            when {
                storyDescription.isEmpty() -> {
                    edtDescription.showError(getString(R.string.validation_must_not_empty))
                }
                imageFile == null -> {
                    root.showSnackBar(getString(R.string.validation_photo))
                }
                else -> {
                    viewModel.uploadStory(
                        photo = imageFile!!,
                        description = storyDescription,
                        latitude = if (allowLocation) myLocation?.latitude?.toFloat() else null,
                        longitude = if (allowLocation) myLocation?.longitude?.toFloat() else null
                    )
                }
            }
        }
    }

    private fun initTakePicture() {
        Dexter.withContext(requireActivity())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    showImagePickerMenu()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).withErrorListener {
                binding.root.showSnackBar(getString(R.string.warning_image_picker_permission))
            }.onSameThread()
            .check()
    }

    private fun showImagePickerMenu() {
        AlertDialog.Builder(requireActivity())
            .setTitle(context?.getString(R.string.label_choose_image_picker_method))
            .setItems(R.array.pictures) { _, p1 ->
                if (p1 == 0) {
                    takePictureRegistration.launch()
                } else {
                    pickFileImage.launch("image/*")
                }
            }.create().show()
    }

    private val takePictureRegistration =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                val uri = requireActivity().getImageUri(bitmap)
                mUri = uri
                imageFile = requireActivity().getFileFromUri(uri)
                binding.imgStoryHolder.gone()
                binding.imgStory.show()
                binding.imgStory.setImageBitmap(bitmap)
            }
        }

    private val pickFileImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                mUri = uri
                imageFile = requireActivity().getFileFromUri(uri)
                binding.imgStoryHolder.gone()
                binding.imgStory.show()
                binding.imgStory.setImageURI(uri)
            }
        }

    companion object {
        const val IS_UPLOAD_SUCCESS_BUNDLE = "is_upload_success_bundle"
        const val IS_UPLOAD_SUCCESS_KEY = "is_upload_success_key"
    }

}