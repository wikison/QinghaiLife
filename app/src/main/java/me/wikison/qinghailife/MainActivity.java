package me.wikison.qinghailife;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

  WebView mWebView;
  String mUrl = "http://www.qhbxsh.com";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mWebView = (WebView) findViewById(R.id.web_view);

    initSettings();
  }

  private void initSettings() {
    mWebView.clearCache(true);
    WebSettings settings = mWebView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);
    settings.setLoadWithOverviewMode(true);
    settings.setDatabaseEnabled(true);
    mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    mWebView.setWebChromeClient(new WebChromeClient());
    mWebView.setWebViewClient(new MyWebViewClient());
    mWebView.loadUrl(mUrl);
  }

  class MyWebViewClient extends WebViewClient {
    @Override public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
    }

    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
      //此处根据需求处理逻辑...
      if (url != null && url.startsWith("tel")) {
        String phoneNumber = url.replace("tel://", "");
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
      } else {
        view.loadUrl(url);
      }
      return true;
    }
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
      mWebView.goBack();
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }
}
