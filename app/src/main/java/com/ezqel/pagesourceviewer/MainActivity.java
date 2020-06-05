package com.ezqel.pagesourceviewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.Service;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.net.NetworkInterface;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText mUrlText;
    private TextView mPageText;
    private static final int LOADER_ID = 0;
    private static final String URL_KEY = "pageUtl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPageText = findViewById(R.id.pageText);
        mUrlText = findViewById(R.id.urlText);

        if(LoaderManager.getInstance(this).getLoader(LOADER_ID) != null){
            LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
        }
    }

    public void getPage(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null){
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null){
            Bundle url = new Bundle();
            url.putString(URL_KEY, mUrlText.getText().toString());
            LoaderManager.getInstance(this).restartLoader(0, url, this);
        }
        else {
            mPageText.setText(R.string.unavailable);
        }



    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        String s = null;
        if (args != null){
            s = args.getString(URL_KEY);
        }

        return new PageLoader(this, s);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        mPageText.setText(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
