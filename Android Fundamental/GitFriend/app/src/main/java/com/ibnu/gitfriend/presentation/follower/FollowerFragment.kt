package com.ibnu.gitfriend.presentation.follower

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibnu.gitfriend.base.BaseFragment
import com.ibnu.gitfriend.data.network.ApiResponse
import com.ibnu.gitfriend.databinding.FragmentFollowerBinding
import com.ibnu.gitfriend.presentation.adapter.UserDetailAdapter
import com.ibnu.gitfriend.presentation.adapter.UserPagerAdapter.Companion.EXTRA_USER_NAME
import com.ibnu.gitfriend.presentation.detail.DetailUserFragmentDirections
import com.ibnu.gitfriend.presentation.following.FollowingFragment
import com.ibnu.gitfriend.utils.AnimationConstant
import com.ibnu.gitfriend.utils.gone
import com.ibnu.gitfriend.utils.popTap
import com.ibnu.gitfriend.utils.show
import org.koin.android.ext.android.inject

class FollowerFragment : BaseFragment<FragmentFollowerBinding>() {
    private val viewModel: FollowerViewModel by inject()
    private val userAdapter: UserDetailAdapter by lazy {
        UserDetailAdapter {
            navigateToDetail(it)
        }
    }

    private var username = ""

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFollowerBinding {
        return FragmentFollowerBinding.inflate(inflater, container, false)
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
        viewModel.getUserFollower(username)
    }

    override fun initActions() {
        binding.btnRetry.setOnClickListener {
            it.popTap()
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.getUserFollower(username)
            }, AnimationConstant.POP_ANIMATION)
        }
    }

    override fun initObservers() {
        viewModel.userResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                }
                is ApiResponse.Success -> {
                    userAdapter.setData(response.data)
                    showLoading(false)
                }
                is ApiResponse.Error -> {
                    showLoading(false)
                    binding.layoutError.show()
                    binding.rvUser.gone()
                }
                else -> {
                    showLoading(false)
                    binding.layoutError.visibility = View.GONE
                    binding.rvUser.visibility = View.GONE
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerLoading.startShimmer()
            binding.shimmerLoading.showShimmer(true)
            binding.shimmerLoading.show()
            binding.rvUser.gone()
        } else {
            binding.shimmerLoading.stopShimmer()
            binding.shimmerLoading.showShimmer(false)
            binding.shimmerLoading.gone()
            binding.rvUser.show()
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