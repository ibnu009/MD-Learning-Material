package com.ibnu.gitfriend.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibnu.gitfriend.R
import com.ibnu.gitfriend.base.BaseFragment
import com.ibnu.gitfriend.data.network.ApiResponse
import com.ibnu.gitfriend.databinding.FragmentFavoriteBinding
import com.ibnu.gitfriend.presentation.adapter.UserAdapter
import com.ibnu.gitfriend.presentation.adapter.UserFavoriteAdapter
import com.ibnu.gitfriend.utils.gone
import com.ibnu.gitfriend.utils.show
import org.koin.android.ext.android.inject
import timber.log.Timber

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val viewModel: FavoriteViewModel by inject()
    private val userAdapter: UserFavoriteAdapter by lazy {
        UserFavoriteAdapter(
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
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.toolBar.apply {
            title = context.getString(R.string.title_favorite_page)
            setNavigationOnClickListener {
                it.findNavController().popBackStack()
            }
        }

        binding.rvUser.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
        }
    }

    override fun initProcess() {
        viewModel.getFavoriteUsers()
    }

    override fun initObservers() {
        viewModel.userResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.layoutEmpty.gone()
                    binding.rvUser.gone()
                }
                is ApiResponse.Success -> {
                    binding.layoutEmpty.gone()
                    binding.rvUser.show()
                    userAdapter.setData(response.data)
                }
                is ApiResponse.Error -> {
                    binding.layoutEmpty.gone()
                    binding.rvUser.gone()
                }
                is ApiResponse.Empty -> {
                    binding.layoutEmpty.show()
                    binding.rvUser.gone()
                }
                else -> {
                    binding.layoutEmpty.gone()
                    binding.rvUser.gone()
                }
            }
        }
    }

    private fun navigateToDetail(username: String) {
        findNavController().navigate(
            FavoriteFragmentDirections.actionFavoriteFragmentToDetailUserFragment(
                username
            )
        )
    }

}