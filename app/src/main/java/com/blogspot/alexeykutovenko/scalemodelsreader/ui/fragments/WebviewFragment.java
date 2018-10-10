package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppConctants;

/**
 * Simple browser with WebView. Correctly navigates with back button. Default textScale percent for
 * Scalemodels.ru - 30%.
 */

public class WebviewFragment extends Fragment {
    private int textScalePercent = 30;

    public WebviewFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        String url = getArguments().getString(MyAppConctants.URL);
        WebView webView = view.findViewById(R.id.wvBrowse);
        webView.setWebViewClient(new MyWebViewClient());
        final WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(textScalePercent);
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(url);

        webView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction()!= KeyEvent.ACTION_DOWN)
                return true;

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    getActivity().onBackPressed();
                }
                return true;
            }
            return false;
        });

        return view;
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