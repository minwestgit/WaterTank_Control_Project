package com.example.minseo.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class Level2Fragment extends Fragment {

    WebView webview;
    final static String chartAddr = "http://115.68.228.55/atms_page/page/day_statistics.php";
    String myUrl;

    public static Level2Fragment newInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();
        Level2Fragment fragment = new Level2Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level2, container, false);
        webview = (WebView)view.findViewById(R.id.char_view2);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new Level2Fragment.MyWebViewClient());
        webview.setWebChromeClient(new WebChromeClient());
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAllowContentAccess(true);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webview.getSettings().setJavaScriptEnabled(true);

        /*
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (Uri.parse(url).getHost().indexOf("google") >= 0) {
                    return false;
                }

                // 아니면 기본 브라우저를 띄움
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl));
                startActivity(intent);

                return true;
            }
        });
        */

        if(myUrl == null){
            myUrl = chartAddr;
        }
        webview.loadUrl(myUrl);

        final Level2Fragment activity = this;

        return view;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            myUrl = url;
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

}
