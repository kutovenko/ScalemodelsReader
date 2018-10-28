package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragments;

import android.annotation.TargetApi;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.FragmentWebviewBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppConctants;

/**
 * Simple browser with WebView. Correctly navigates with back button. Default textScale percent for
 * Scalemodels.ru - 30%.
 */

public class WebviewFragment extends Fragment {
    private FragmentWebviewBinding mBinding;
    private WebSettings webSettings;

    public WebviewFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_webview, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle("");
        mBinding.pbLoadStatus.setIndeterminate(true);


        String url = getArguments().getString(MyAppConctants.PRINTING_URL);
        mBinding.wvBrowse.setWebViewClient(new MyWebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mBinding.pbLoadStatus.setVisibility(View.GONE);
            }

        });
        webSettings = mBinding.wvBrowse.getSettings();
        int textScalePercent = 30;
        webSettings.setTextZoom(textScalePercent);
        webSettings.setJavaScriptEnabled(true);

        mBinding.wvBrowse.loadUrl(url);

        mBinding.wvBrowse.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction()!= KeyEvent.ACTION_DOWN)
                return true;

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (mBinding.wvBrowse.canGoBack()) {
                    mBinding.wvBrowse.goBack();
                } else {
                    getActivity().onBackPressed();
                }
                return true;
            }
            return false;
        });

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.webview, menu);

        MenuItem openInChrome = menu.findItem(R.id.miChrome);
        String url = getArguments().getString(MyAppConctants.ORIGINAL_URL);
        openInChrome.setOnMenuItemClickListener(item -> {
            mBinding.wvBrowse.getContext().startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return false;
        });

        MenuItem setScale50 = menu.findItem(R.id.miScale50);
        setScale50.setOnMenuItemClickListener(item -> {
            webSettings.setTextZoom(15);
            return false;
        });

        MenuItem setScale100 = menu.findItem(R.id.miScale100);
        setScale100.setOnMenuItemClickListener(item -> {
            webSettings.setTextZoom(30);
            return false;
        });

        MenuItem setScale150 = menu.findItem(R.id.miScale150);
        setScale150.setOnMenuItemClickListener(item -> {
            webSettings.setTextZoom(45);
            return false;
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}