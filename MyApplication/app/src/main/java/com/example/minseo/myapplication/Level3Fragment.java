package com.example.minseo.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class Level3Fragment extends Fragment {

    WebView webview;
    final static String chartAddr = "http://115.68.228.55/atms_page/page/month_statistics.php";
    String myUrl;

    public static Level3Fragment newInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();

        Level3Fragment fragment = new Level3Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level3, container, false);
        webview = (WebView)view.findViewById(R.id.char_view3);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new Level3Fragment.MyWebViewClient());
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        if(myUrl == null){
            myUrl = chartAddr;
        }
        webview.loadUrl(myUrl);

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
