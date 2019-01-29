package com.blogspot.alexeykutovenko.scalemodelsreader.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

/**
 * Class handles common network operations.
 */
public class NetworkUtils {
    /**
     * Checks for network availability.
     *
     * @param context Context
     * @return true if network is available.
     */
    public static boolean isNetworkAwailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Objects.requireNonNull(context, "Context must not be null")
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
