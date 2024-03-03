package com.ibnu.gitfriend.presentation.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibnu.gitfriend.R
import com.ibnu.gitfriend.base.BaseFragment
import com.ibnu.gitfriend.data.network.ApiResponse
import com.ibnu.gitfriend.databinding.FragmentHomeBinding
import com.ibnu.gitfriend.presentation.adapter.OnClick
import com.ibnu.gitfriend.presentation.adapter.UserAdapter
import com.ibnu.gitfriend.utils.AnimationConstant
import com.ibnu.gitfriend.utils.gone
import com.ibnu.gitfriend.utils.popTap
import com.ibnu.gitfriend.utils.show
import org.koin.android.ext.android.inject
import timber.log.Timber
import kotlin.math.abs

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by inject()
    private val userAdapter: UserAdapter by lazy {
        UserAdapter({
            navigateToDetail(it)
        }, { isFavorite, id ->
            viewModel.changeFavoriteUserStatus(isFavorite, id)
        })
    }

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            rvUser.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = userAdapter
                isNestedScrollingEnabled = false
            }

            appBarCoor.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                    btnSearch.show()
                    btnFavorite.show()
                    btnSetting.show()
                    tvAppName.show()
                } else if (verticalOffset == 0) {
                    btnSearch.gone()
                    btnFavorite.gone()
                    btnSetting.gone()
                    tvAppName.gone()
                } else {
                    btnSearch.gone()
                    btnFavorite.gone()
                    btnSetting.gone()
                    tvAppName.gone()
                }
            }
        }
    }

    override fun initProcess() {
        viewModel.getUsers()
    }

    override fun initObservers() {
        viewModel.userResult.observe(viewLifecycleOwner) { response ->
            Timber.d("Response is $response")
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
                    binding.rvUser.gone()
                    binding.layoutError.show()
                }
                else -> {
                    binding.layoutError.gone()
                    showLoading(false)
                }
            }
        }
    }

    override fun initActions() {
        binding.apply {
            //Search
            btnSearch.setOnClickListener {
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    navigateToSearch()
                }, AnimationConstant.POP_ANIMATION)
            }

            btnSearchBig.setOnClickListener {
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    navigateToSearch()
                }, AnimationConstant.POP_ANIMATION)
            }

            //Favorite
            btnFavorite.setOnClickListener {
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    navigateToFavorite()
                }, AnimationConstant.POP_ANIMATION)
            }

            btnFavoriteBig.setOnClickListener {
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    navigateToFavorite()
                }, AnimationConstant.POP_ANIMATION)
            }

            //Setting
            btnSetting.setOnClickListener {
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    navigateToSetting()
                }, AnimationConstant.POP_ANIMATION)
            }

            btnSettingBig.setOnClickListener {
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    navigateToSetting()
                }, AnimationConstant.POP_ANIMATION)
            }

            btnRetry.setOnClickListener {
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.getUsers()
                }, AnimationConstant.POP_ANIMATION)
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmeringLoadingHome.startShimmer()
                shimmeringLoadingHome.showShimmer(true)
                shimmeringLoadingHome.show()
                rvUser.gone()
            } else {
                shimmeringLoadingHome.stopShimmer()
                shimmeringLoadingHome.showShimmer(false)
                shimmeringLoadingHome.gone()
                rvUser.show()
            }
        }
    }

    private fun navigateToDetail(username: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailUserFragment(
                username
            )
        )
    }

    private fun navigateToSearch() {
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
    }

    private fun navigateToFavorite() {
        findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
    }

    private fun navigateToSetting() {
        findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
    }

}