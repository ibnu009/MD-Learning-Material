package com.ibnu.gitfriend.presentation.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.ibnu.gitfriend.R
import com.ibnu.gitfriend.base.BaseFragment
import com.ibnu.gitfriend.data.network.ApiResponse
import com.ibnu.gitfriend.databinding.FragmentDetailUserBinding
import com.ibnu.gitfriend.presentation.adapter.UserPagerAdapter
import com.ibnu.gitfriend.utils.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class DetailUserFragment : BaseFragment<FragmentDetailUserBinding>() {
    private val viewModel: DetailUserViewModel by inject()

    private var username = ""
    private var sectionsPagerAdapter: UserPagerAdapter? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailUserBinding {
        return FragmentDetailUserBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { DetailUserFragmentArgs.fromBundle(it) }
        username = safeArgs?.username ?: ""
    }

    override fun initUI() {
        sectionsPagerAdapter = UserPagerAdapter(username, requireActivity())

        binding.apply {
            toolBar.apply {
                title = username
                setNavigationOnClickListener {
                    it.findNavController().popBackStack()
                }
            }
           viewPager.adapter = sectionsPagerAdapter
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    override fun initProcess() {
        viewModel.getUserDetail(username)
    }

    override fun initObservers() {
        viewModel.userDetailResult.observe(viewLifecycleOwner) { response ->
            Timber.d("Response is $response")
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                }
                is ApiResponse.Success -> {
                    val user = response.data
                    binding.apply {
                        tvUserName.text = user.username
                        tvRealName.text = user.name
                        tvUserCompany.text = user.company ?: "-"
                        tvUserLocation.text = user.location ?: "-"
                        tvFollower.text =
                            getString(R.string.label_follower, "${user.followers ?: 0}")
                        tvFollowing.text =
                            getString(R.string.label_following, "${user.following ?: 0}")
                        btnFavoriteBig.text =
                            if (user.isFavorite) "Hapus dari Favorit" else "Tambahkan ke Favorit"
                        btnFavoriteBig.setOnClickListener {
                            it.popTap()
                            Handler(Looper.getMainLooper()).postDelayed({
                                user.isFavorite = !user.isFavorite
                                viewModel.changeFavoriteUserStatus(user.isFavorite, user.id ?: 0)
                                btnFavoriteBig.text =
                                    if (user.isFavorite) "Hapus dari Favorit" else "Tambahkan ke Favorit"
                            }, AnimationConstant.POP_ANIMATION)
                        }
                        Glide.with(root).load(user.avatarUrl).apply(RequestOptions())
                            .into(imgUser)
                    }
                    showLoading(false)
                }
                is ApiResponse.Error -> {
                    showLoading(false)
                    showCustomToast(response.errorMessage)
                }
                else -> {
                    showLoading(false)
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
                layoutContent.hide()
            } else {
                shimmerLoading.stopShimmer()
                shimmerLoading.showShimmer(false)
                shimmerLoading.gone()
                layoutContent.show()
            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_title_following,
            R.string.tab_title_follower,
        )
    }
}