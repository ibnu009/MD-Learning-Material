package com.ibnu.distory.presentation.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibnu.distory.R
import com.ibnu.distory.base.BaseFragment
import com.ibnu.distory.databinding.FragmentHomeBinding
import com.ibnu.distory.presentation.add.AddStoryFragment
import com.ibnu.distory.presentation.home.adapter.LoadingStateAdapter
import com.ibnu.distory.presentation.home.adapter.StoryPagingAdapter
import com.ibnu.distory.utils.helper.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by inject()

    private val storyPagingAdapter: StoryPagingAdapter by lazy {
        StoryPagingAdapter {
            navigateToDetail(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showYesNoDialog(
                        title = getString(R.string.title_close_app),
                        message = getString(R.string.message_close_app),
                        onYes = {
                            closeApp()
                        }
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            rvStory.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = storyPagingAdapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        storyPagingAdapter.retry()
                    }
                )
                isNestedScrollingEnabled = false
            }

            storyPagingAdapter.addLoadStateListener { loadState ->
                if (loadState.append.endOfPaginationReached) {
                    if (storyPagingAdapter.itemCount < 1) {
                        layoutEmpty.root.show()
                        layoutError.root.gone()
                    }
                }
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        showLoading(true)
                    }
                    is LoadState.NotLoading -> {
                        showLoading(false)
                        binding.rvStory.scheduleLayoutAnimation()
                    }
                    is LoadState.Error -> {
                        showLoading(false)
                        layoutEmpty.root.gone()
                        rvStory.gone()
                        layoutError.root.show()
                    }
                    else -> showLoading(false)
                }
            }
        }
    }

    override fun initActions() {
        binding.apply {
            toolBar.apply {
                btnSetting.popClick {
                    findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
                }
                btnLocation.popClick {
                    findNavController().navigate(R.id.action_homeFragment_to_storyMapFragment)
                }
            }

            fabAddStory.popClick {
                findNavController().navigate(R.id.action_homeFragment_to_addStoryFragment)
            }

            layoutRefresh.setOnRefreshListener {
                storyPagingAdapter.refresh()
                layoutRefresh.isRefreshing = false
            }

            layoutError.btnRefresh.popClick {
                storyPagingAdapter.refresh()
                layoutError.root.gone()
            }
        }

        initResultData()
    }

    override fun initProcess() {}

    override fun initObservers() {
        viewModel.getStory().observe(this) {
            storyPagingAdapter.submitData(lifecycle, it)
        }
    }

    private fun initResultData(){
        setFragmentResultListener(AddStoryFragment.IS_UPLOAD_SUCCESS_KEY) { _, bundle ->
            val result = bundle.getBoolean(AddStoryFragment.IS_UPLOAD_SUCCESS_BUNDLE)
            Timber.d("Data is $result")

            // Force scroll top after success
            Handler(Looper.getMainLooper()).postDelayed({
                binding.rvStory.scrollToPosition(0)
            }, 300) //Give 300 ms delay
        }
    }

    private fun navigateToDetail(id: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailStoryFragment(
                id
            )
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                shimmerLoading.showAndStart()
                rvStory.hide()
            } else {
                shimmerLoading.hideAndStop()
                rvStory.show()
            }
        }
    }
}