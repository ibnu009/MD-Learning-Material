package com.ibnu.gitfriend.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibnu.gitfriend.R
import com.ibnu.gitfriend.base.BaseFragment
import com.ibnu.gitfriend.data.network.ApiResponse
import com.ibnu.gitfriend.databinding.FragmentSearchBinding
import com.ibnu.gitfriend.presentation.adapter.UserAdapter
import com.ibnu.gitfriend.utils.gone
import com.ibnu.gitfriend.utils.show
import org.koin.android.ext.android.inject
import timber.log.Timber

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by inject()
    private val userAdapter: UserAdapter by lazy {
        UserAdapter(
            {
                navigateToDetail(it)
            },
            { isFavorite, id ->
                viewModel.changeFavoriteUserStatus(isFavorite, id)
            }
        )
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.toolBar.apply {
            title = context.getString(R.string.title_search_page)
            setNavigationOnClickListener {
                it.findNavController().popBackStack()
            }
        }

        binding.svUser.apply {
            requestFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    clearFocus()
                    viewModel.searchUser(query)
                    return true
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }

        binding.rvUser.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
        }
    }

    override fun initProcess() {}

    override fun initObservers() {
        viewModel.userResult.observe(viewLifecycleOwner) { response ->
            Timber.d("Response is $response")
            when (response) {
                is ApiResponse.Loading -> {
                    binding.layoutEmpty.gone()
                    binding.layoutError.gone()
                    binding.rvUser.gone()
                    showLoading(true)
                }
                is ApiResponse.Success -> {
                    binding.layoutEmpty.gone()
                    binding.layoutError.gone()
                    binding.rvUser.show()
                    userAdapter.setData(response.data)
                    showLoading(false)
                }
                is ApiResponse.Error -> {
                    binding.layoutEmpty.gone()
                    binding.layoutError.show()
                    binding.rvUser.gone()

                    binding.tvErrorMessage.text = response.errorMessage
                    showLoading(false)
                }
                is ApiResponse.Empty -> {
                    binding.layoutEmpty.show()
                    binding.layoutError.gone()
                    binding.rvUser.gone()
                    showLoading(false)
                }
                else -> {
                    binding.layoutEmpty.gone()
                    binding.layoutError.gone()
                    binding.rvUser.gone()
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerLoadingSearch.startShimmer()
            binding.shimmerLoadingSearch.showShimmer(true)
            binding.shimmerLoadingSearch.show()
        } else {
            binding.shimmerLoadingSearch.stopShimmer()
            binding.shimmerLoadingSearch.showShimmer(false)
            binding.shimmerLoadingSearch.gone()
        }
    }

    private fun navigateToDetail(username: String){
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailUserFragment(
                username
            )
        )
    }

}