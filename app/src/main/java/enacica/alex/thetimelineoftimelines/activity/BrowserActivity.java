package enacica.alex.thetimelineoftimelines.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import enacica.alex.thetimelineoftimelines.R;

public class BrowserActivity extends AppCompatActivity {
    private WebView mWebView;
    private String eventUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            eventUrl = getIntent().getStringExtra("EXTRA_EVENT_URL");
        }
        if (eventUrl != null)
        mWebView = findViewById(R.id.browserWebView);
        WebSettings mWebSetting = mWebView.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(eventUrl);

    }
}
