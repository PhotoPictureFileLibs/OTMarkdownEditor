package com.jiushig.rich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiushig.rich.utils.MarkDownHandler;

/**
 * Created by zk on 2017/9/14.
 */

public class RichView extends WebView {

    private String text;

    private LinkClickListener listener;

    public RichView(Context context) {
        super(context);
        addClient();
    }

    public RichView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addClient();
    }

    public RichView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addClient();
    }

    public void setText(String text) {
        this.text = text;
        MarkDownHandler.getInstance().toHtml(text, new MarkDownHandler.Callback() {
            @Override
            public void done(String html) {
                if (html != null)
                    loadDataWithBaseURL(null, html, "text/html", "utf8mb4", null);
            }
        });
    }

    public String getText() {
        return text;
    }

    private void addClient() {
        setWebViewClient(new MyWebClient());

        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
    }

    public void setListener(LinkClickListener listener) {
        this.listener = listener;
    }

    private final class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (listener != null) {
                listener.click(url);
            }
            return true;
        }
    }

    public interface LinkClickListener {
        void click(String url);
    }
}
