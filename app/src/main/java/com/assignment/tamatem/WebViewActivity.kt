package com.assignment.tamatem

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.assignment.tamatem.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadWebView()
        initListener()
    }

    private fun loadWebView() {
        binding.webView.apply {
            loadUrl(WEB_URL)
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val url = request?.url.toString()
                    view?.loadUrl(url)
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.progressBar.isVisible = true
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.isVisible = false
                    super.onPageFinished(view, url)
                }

                override fun onReceivedError(
                    view: WebView,
                    request: WebResourceRequest,
                    error: WebResourceError
                ) {
                    binding.progressBar.isVisible = false
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Toast.makeText(this@WebViewActivity, "${error.description}", Toast.LENGTH_LONG)
                            .show()
                    }
                    super.onReceivedError(view, request, error)
                }
            }
        }
    }

    private fun initListener() {
        binding.apply {
            buttonBack.setOnClickListener {
                webView.goBack()
            }
            buttonForward.setOnClickListener {
                webView.goForward()
            }
            buttonRefresh.setOnClickListener {
                webView.reload()
            }
            buttonClose.setOnClickListener {
                finish()
            }
        }
    }

    companion object {
        const val WEB_URL = "https://tamatemplus.com"
    }

}
