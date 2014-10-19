package app.philm.in.network;

import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.uwetrottmann.tmdb.Tmdb;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import app.philm.in.Constants;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class PhilmTmdb extends Tmdb {

    private static final String TAG = "PhilmTmdb";

    private final File mCacheLocation;

    public PhilmTmdb(File cacheLocation) {
        mCacheLocation = cacheLocation;
    }

    @Override
    protected RestAdapter.Builder newRestAdapterBuilder() {
        RestAdapter.Builder b = super.newRestAdapterBuilder();

        if (mCacheLocation != null) {
            OkHttpClient client = new OkHttpClient();

            try {
                File cacheDir = new File(mCacheLocation, UUID.randomUUID().toString());
                Cache cache = new Cache(cacheDir, 1024);
                client.setCache(cache);
            } catch (IOException e) {
                Log.e(TAG, "Could not use OkHttp Cache", e);
            }

            client.setConnectTimeout(Constants.CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            client.setReadTimeout(Constants.READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

            b.setClient(new OkClient(client));
        }

        return b;
    }

}
