package com.kbmsi.kbmsiapps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class WebActivity extends AppCompatActivity {

    WebView webview;
    String link;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_web);

        try{
            String action = intent.getAction();
            String data = intent.getDataString();
            if (Intent.ACTION_VIEW.equals(action) && data != null) {
                String recipeId = data.substring(data.lastIndexOf("/") + 1);
                System.out.println("link : "+data);
                link = data+"";
//            Uri contentUri = RecipeContentProvider.CONTENT_URI.buildUpon()
//                    .appendPath(recipeId).build();
//            showRecipe(contentUri);
            }
        } catch (Exception e){

        }
        try{
            intent = getIntent();
            link = intent.getStringExtra("id_berita");
        } catch (Exception e){

        }

        webview = (WebView) findViewById(R.id.webview);
        // Let's display the progress in the activity title bar, like the
        // browser app does.

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(false);
        final Activity activity = this;
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
//                    requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
        });
        webview.loadUrl(""+link);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                viewx.loadUrl(urlx);
                return false;
            }
        });


    }

    protected void onNewIntent(Intent intent) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
