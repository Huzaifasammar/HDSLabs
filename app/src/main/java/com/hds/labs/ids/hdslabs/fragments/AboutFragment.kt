package com.hds.labs.ids.hdslabs.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hds.labs.ids.hdslabs.databinding.FragmentAboutBinding
import com.hds.labs.ids.hdslabs.databinding.FragmentHomeBinding
import android.webkit.WebView

import android.webkit.WebViewClient





class AboutFragment : Fragment() {
    lateinit var binding: FragmentAboutBinding
    private val webUrl = "https://www.hds-labs.com/about-us/"
//    private val webUrl = "https://www.tutorialspoint.com"


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val navController = findNavController()
        binding = FragmentAboutBinding.inflate(layoutInflater,container,false)
        binding.aboutWebView.webViewClient = MyBrowser()
        binding.aboutWebView.settings.loadsImagesAutomatically = true;
        binding.aboutWebView.settings.javaScriptEnabled = true;
        binding.aboutWebView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY;
        binding.aboutWebView.loadUrl(webUrl)
        return binding.root
    }


    private class MyBrowser : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

}