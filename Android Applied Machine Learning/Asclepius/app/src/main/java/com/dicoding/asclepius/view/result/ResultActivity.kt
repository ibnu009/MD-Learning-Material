package com.dicoding.asclepius.view.result

import android.content.Intent
import android.util.Base64
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.network.ApiResponse
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.utils.gone
import com.dicoding.asclepius.utils.setImage
import com.dicoding.asclepius.utils.show
import com.dicoding.asclepius.view.adapter.ArticleAdapter
import com.dicoding.asclepius.view.base.BaseActivity
import com.dicoding.asclepius.view.webview.WebViewActivity
import org.koin.android.ext.android.inject
import timber.log.Timber

class ResultActivity : BaseActivity<ActivityResultBinding>()  {

    private val viewModel: ResultViewModel by inject()

    private var label: String? = ""
    private var confidenceScore: String? = ""
    private var imageBase64: String? = ""
    private var isSaved = false

    private val articleAdapter: ArticleAdapter by lazy {
        ArticleAdapter {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.EXTRA_URL, it)
            startActivity(intent)
        }
    }

    override fun getViewBinding(): ActivityResultBinding {
        return ActivityResultBinding.inflate(layoutInflater)
    }

    override fun initIntent() {
        label = intent.getStringExtra(EXTRA_LABEL)
        confidenceScore = intent.getStringExtra(EXTRA_CONFIDENCE_SCORE)
        imageBase64 = intent.getStringExtra(EXTRA_IMAGE_BASE_64)
        isSaved = intent.getBooleanExtra(EXTRA_IS_SAVED, false)
    }

    override fun initUI() {
        binding.apply {
            toolBar.setNavigationOnClickListener {
                finish()
            }
            resultText.text = label
            tvConfidenceScore.text = confidenceScore
            adjustResultColor(label ?: "")
            showDescriptionResult(label ?: "")
            val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
            resultImage.setImage(imageBytes)
            if (isSaved){
                saveButton.setBackgroundColor(resources.getColor(R.color.lightGrey, null))
            }
        }
    }

    override fun initAdapter(){
        binding.rvArticle.apply {
            layoutManager =
                LinearLayoutManager(this@ResultActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = articleAdapter
        }
    }

    override fun initActions() {
        binding.saveButton.setOnClickListener {
            if (isSaved) return@setOnClickListener
            viewModel.addHistory(
                label ?: "",
                confidenceScore ?: "",
                imageBase64 ?: ""
            )
            showToast(getString(R.string.info_result_saved))
            isSaved = true
            binding.saveButton.setBackgroundColor(resources.getColor(R.color.lightGrey, null))
        }
    }

    override fun initProcess() {
        viewModel.getArticles()
    }

    override fun initObservers() {
        viewModel.articleResult.observe(this) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    Timber.d("Loading")
                    binding.progressBar.show()
                }

                is ApiResponse.Success -> {
                    Timber.d("Success")
                    binding.progressBar.gone()
                    val data = response.data
                    articleAdapter.setData(data)
                }

                is ApiResponse.Error -> {
                    binding.progressBar.gone()
                    Timber.d("Error")
                }

                else -> {
                    binding.progressBar.gone()
                    Timber.d("Else")
                }
            }
        }
    }

    private fun adjustResultColor(label: String) {
        val color = when (label.trim()) {
            "Non Cancer" -> R.color.green
            "Cancer" -> R.color.red
            else -> R.color.red
        }
        binding.resultText.setTextColor(resources.getColor(color, null))
    }

    private fun showDescriptionResult(label: String) {
        val description = when (label.trim()) {
            "Non Cancer" -> getString(R.string.non_cancer_description)
            "Cancer" -> getString(R.string.cancer_description)
            else -> getString(R.string.cancer_description)
        }
        binding.tvResultDescription.text = description
    }

    companion object {
        const val EXTRA_IS_SAVED = "extra_is_saved"
        const val EXTRA_LABEL = "extra_label"
        const val EXTRA_CONFIDENCE_SCORE = "extra_confidence_score"
        const val EXTRA_IMAGE_BASE_64 = "extra_image_base_64"
    }
}