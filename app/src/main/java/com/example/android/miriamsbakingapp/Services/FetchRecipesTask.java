package com.example.android.miriamsbakingapp.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.android.miriamsbakingapp.Objects.Recipe;
import com.example.android.miriamsbakingapp.utils.JsonUtils;
import com.example.android.miriamsbakingapp.utils.NetworkUtils;

import java.io.IOException;

public class FetchRecipesTask extends AsyncTask<String, Void, Recipe[]> {

    private final Context mContext;
    private OnEventListener<Recipe[]> mCallBack;
    private Exception mException;

    public FetchRecipesTask(OnEventListener callBack, Context context) {
        mCallBack = callBack;
        mContext = context;
    }

    @Override
    protected Recipe[] doInBackground(String... strings) {

        try {
            String jsonText = NetworkUtils.getRecipesFromHttp();
            return JsonUtils.getRecipesArray(jsonText);
        } catch (IOException e) {
            e.printStackTrace();
            mException = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(Recipe[] recipes) {
        if (mContext != null) {
            if (mException == null) {
                if (recipes != null)
                    mCallBack.onSuccess(recipes);
                else
                    Toast.makeText(mContext,
                            "No recipes available", Toast.LENGTH_LONG).show();
            } else {
                mCallBack.onFailure(mException);
            }
        }
    }
}
