package com.ibnu.distory.presentation.detail

import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.ibnu.distory.R
import com.ibnu.distory.base.BaseFragment
import com.ibnu.distory.data.model.Story
import com.ibnu.distory.databinding.FragmentDetailStoryBinding
import com.ibnu.distory.utils.constant.AnimationConstants.SPEED_NORMAL
import com.ibnu.distory.utils.constant.AnimationConstants.SPEED_SLOW
import com.ibnu.distory.utils.helper.*
import org.koin.android.ext.android.inject

class DetailStoryFragment : BaseFragment<FragmentDetailStoryBinding>() {

    private val viewModel: DetailStoryViewModel by inject()

    private var id = ""

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailStoryBinding {
        return FragmentDetailStoryBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.toolBar.apply {
            title = getString(R.string.title_story_detail)
            setNavigationOnClickListener {
                it.findNavController().popBackStack()
            }
        }
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { DetailStoryFragmentArgs.fromBundle(it) }
        id = safeArgs?.id ?: ""
    }

    override fun initProcess() {
        viewModel.getStoryDetail(id)
    }

    override fun initObservers() {
        viewModel.storyResult.observeResponse(
            viewLifecycleOwner,
            loading = {
                showLoading(true)
            },
            success = { response ->
                showLoading(false)
                setDetailContent(response.data)
                playAnimation()
            },
            error = { response ->
                binding.root.showSnackBar(response.errorMessage)
                showLoading(false)
            }
        )
    }

    private fun setDetailContent(story: Story) {
        binding.apply {
            imgStory.setImageUrl(story.photoUrl)
            tvUserName.text = story.name
            tvUploadAt.text = story.createdAt.getTimeAgo(requireContext())
            tvDescription.text = story.description
        }
    }

    private fun playAnimation() {
        binding.apply {
            val imageStory = imgStory.setAlphaAnimation(SPEED_SLOW)
            val imageUser = imgUser.setAlphaAnimation(SPEED_NORMAL)
            val textUsername = tvUserName.setAlphaAnimation(SPEED_NORMAL)
            val textUploadAt = tvUploadAt.setAlphaAnimation(SPEED_NORMAL)
            val labelDescription = txLabelDescription.setAlphaAnimation(SPEED_SLOW)
            val textDescription = tvDescription.setAlphaAnimation(SPEED_SLOW)

            val together = AnimatorSet().apply {
                playTogether(labelDescription, textDescription)
            }

            AnimatorSet().apply {
                playSequentially(imageUser, textUsername, textUploadAt, imageStory, together)
                start()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerLoading.showAndStart()
            } else {
                shimmerLoading.hideAndStop()
            }
        }
    }

}