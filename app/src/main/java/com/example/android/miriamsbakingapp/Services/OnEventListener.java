package com.example.android.miriamsbakingapp.Services;

public interface OnEventListener<T> {
    void onSuccess(T object);

    void onFailure(Exception e);
}
