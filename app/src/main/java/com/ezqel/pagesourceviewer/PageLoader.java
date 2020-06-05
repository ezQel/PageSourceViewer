package com.ezqel.pagesourceviewer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class PageLoader extends AsyncTaskLoader<String> {
    private String pageUrl;

    public PageLoader(@NonNull Context context, String url) {
        super(context);
        this.pageUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetWorkUtil.getPage(pageUrl);
    }
}
