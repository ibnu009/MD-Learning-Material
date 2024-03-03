package com.ibnu.gitfriend.presentation.following

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibnu.gitfriend.base.BaseFragment
import com.ibnu.gitfriend.data.network.ApiResponse
import com.ibnu.gitfriend.databinding.FragmentFollowingBinding
import com.ibnu.gitfriend.presentation.adapter.UserDetailAdapter
import com.ibnu.gitfriend.presentation.adapter.UserPagerAdapter.Companion.EXTRA_USER_NAME
import com.ibnu.gitfriend.presentation.detail.DetailUserFragmentDirections
import com.ibnu.gitfriend.utils.AnimationConstant
import com.ibnu.gitfriend.utils.gone
import com.ibnu.gitfriend.utils.popTap
import com.ibnu.gitfriend.utils.show
import org.koin.android.ext.android.inject

class FollowingFragment : BaseFragment<FragmentFollowingBinding>() {

    private val viewModel: FollowingViewModel by inject()
    private val userAdapter: UserDetailAdapter by lazy {
        UserDetailAdapter {
            navigateToDetail(it)
        }
    }

    private var username: String = ""

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFollowingBinding {
        return FragmentFollowingBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        if (arguments != null) {
            val uName = arguments?.getString(EXTRA_USER_NAME)
            if (uName != null) {
                username = uName
            }
        }
    }

    override fun initUI() {
        binding.rvUser.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun initProcess() {
        viewModel.getUserFollowing(username)
    }

    override fun initActions() {
        binding.btnRetry.setOnClickListener {
            it.popTap()
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.getUserFollowing(username)
            }, AnimationConstant.POP_ANIMATION)
        }
    }

    override fun initObservers() {
        viewModel.userResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                    binding.layoutError.gone()
                }
                is ApiResponse.Success -> {
                    userAdapter.setData(response.data)
                    showLoading(false)
                    binding.layoutError.gone()
                }
                is ApiResponse.Error -> {
                    showLoading(false)
                    binding.layoutError.show()
                    binding.rvUser.gone()
                }
                else -> {
                    showLoading(false)
                    binding.layoutError.gone()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerLoading.startShimmer()
                shimmerLoading.showShimmer(true)
                shimmerLoading.show()
                rvUser.gone()
            } else {
                shimmerLoading.stopShimmer()
                shimmerLoading.showShimmer(false)
                shimmerLoading.gone()
                rvUser.show()
            }
        }
    }

    private fun navigateToDetail(username: String) {
        findNavController().navigate(
            DetailUserFragmentDirections.actionDetailFragmentToDetailFragment(
                username
            )
        )
    }

}