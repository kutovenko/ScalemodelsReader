package com.blogspot.alexeykutovenko.scalemodelsreader.network;

/**
 * Interface for retrieving values from Retrofit asynchronous calls. Used in notification service.
 */
public interface GetValueCallback {
    void onSuccess(int value);

    void onError(Throwable throwable);
}