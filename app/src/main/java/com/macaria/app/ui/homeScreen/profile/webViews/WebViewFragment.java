package com.macaria.app.ui.homeScreen.profile.webViews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.macaria.app.databinding.WebViewFragmentBinding;

public class WebViewFragment extends Fragment {
    private WebViewFragmentBinding binding ;
    private String link , title ;

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = WebViewFragmentBinding.inflate(inflater, container, false);
        initWebView();
        return binding.getRoot();
    }

    private void initWebView(){
        link = getArguments() != null ? getArguments().getString("link") : "";
        title = getArguments() != null ? getArguments().getString("title") : "";
        binding.title.setText(title);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.loadUrl(link);
    }

}