package com.dicoding.asclepius.view.history

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.asclepius.data.network.ApiResponse
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.utils.gone
import com.dicoding.asclepius.utils.show
import com.dicoding.asclepius.view.adapter.HistoryAdapter
import com.dicoding.asclepius.view.base.BaseActivity
import com.dicoding.asclepius.view.result.ResultActivity
import org.koin.android.ext.android.inject
import timber.log.Timber

class HistoryActivity : BaseActivity<ActivityHistoryBinding>() {

    private val viewModel: HistoryViewModel by inject()
    private val historyAdapter: HistoryAdapter by lazy {
        HistoryAdapter {
            moveToResult(it.label, it.confidence, it.imageBase64)
        }
    }

    override fun getViewBinding(): ActivityHistoryBinding {
        return ActivityHistoryBinding.inflate(layoutInflater)
    }

    override fun initActions() {}

    override fun initUI() {
        binding.apply {
            toolBar.setNavigationOnClickListener {
                finish()
            }
        }
    }

    override fun initAdapter() {
        binding.historyRecycler.apply {
            layoutManager =
                GridLayoutManager(this@HistoryActivity, 2, GridLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }
    }

    override fun initProcess() {
        viewModel.getHistories()
    }

    override fun initObservers() {
        viewModel.historyResult.observe(this) {
            when (it) {
                ApiResponse.Empty -> {
                    Timber.d("Empty data")
                    binding.emptyText.show()
                }

                is ApiResponse.Error -> {
                    Timber.d("Error: ${it.errorMessage}")
                    binding.emptyText.gone()
                }

                ApiResponse.Loading -> {
                    Timber.d("Loading")
                    binding.emptyText.gone()
                }

                is ApiResponse.Success -> {
                    binding.emptyText.gone()
                    Timber.d("Data: ${it.data}")
                    historyAdapter.setData(it.data)
                }
            }
        }
    }

    private fun moveToResult(label: String, confidenceScore: String, imageBase64: String) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_LABEL, label)
        intent.putExtra(ResultActivity.EXTRA_CONFIDENCE_SCORE, confidenceScore)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_BASE_64, imageBase64)
        intent.putExtra(ResultActivity.EXTRA_IS_SAVED, true)
        startActivity(intent)
    }


}