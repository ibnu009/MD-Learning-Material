package com.dicoding.asclepius.view.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import com.dicoding.asclepius.databinding.ActivityWebViewBinding
import com.dicoding.asclepius.utils.gone
import com.dicoding.asclepius.utils.show
import com.dicoding.asclepius.view.base.BaseActivity

class WebViewActivity : BaseActivity<ActivityWebViewBinding>()  {

    private var url: String? = ""

    override fun getViewBinding(): ActivityWebViewBinding {
        return ActivityWebViewBinding.inflate(layoutInflater)
    }

    override fun initIntent() {
        url = intent.getStringExtra(EXTRA_URL)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initUI() {
        binding.apply {
            toolBar.setNavigationOnClickListener {
                finish()
            }
            webView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    progressBar.show()
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.gone()
                }
            }
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(url ?: "")
        }
    }

    override fun initActions() {}

    override fun initProcess() {}

    override fun initObservers() {}
    companion object {
        const val EXTRA_URL = "extra_url"
    }
}