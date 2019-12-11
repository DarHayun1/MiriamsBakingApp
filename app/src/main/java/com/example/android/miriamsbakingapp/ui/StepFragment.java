package com.example.android.miriamsbakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.miriamsbakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

public class StepFragment extends Fragment {


    private static final String TAG = StepFragment.class.getSimpleName();
    private String mDescription;
    private String mVideoUrl;

    private PlayerView mPlayerView;
    private TextView mDescriptionTv;
    private SimpleExoPlayer mSimpleExoPlayer;
    private Context mContext;
    private ImageView mDefaultImgIv;
    private TextView mStepNumTv;
    public static final String EXTRA_PLAY_WHEN_READY = "extra_play_when_ready";
    public static final String EXTRA_PLAYER_POS = "extra_player_pos";


    public StepFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            mPlayerPos = savedInstanceState.getLong(EXTRA_PLAYER_POS);
            Log.d(TAG, "       onCreate!! :            " + mPlayerPos);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //inflating the view objects
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        mPlayerView = rootView.findViewById(R.id.player_view);
        mDescriptionTv = rootView.findViewById(R.id.step_full_desc_tv);
        mDefaultImgIv = rootView.findViewById(R.id.default_img_iv);
        mStepNumTv = (TextView) rootView.findViewById(R.id.step_num_full_desc_tv);
        mContext = getActivity().getApplicationContext();

        if (mDescription != null && mDescription.substring(1, 3).equals(". ")) {
            String stepNumText = "Step #" + mDescription.substring(0, 1);
            mStepNumTv.setText(stepNumText);
            mStepNumTv.setVisibility(View.VISIBLE);
            mDescription = mDescription.substring(3).replaceAll("\\.\\s?", "\\.\n");
        }
        mDescriptionTv.setText(mDescription);

        if (savedInstanceState != null){
            mPlayerPos = savedInstanceState.getLong(EXTRA_PLAYER_POS);
            Log.d(TAG, "                 POS:             " + mPlayerPos);
            mIsPlayWhenReady = savedInstanceState.getBoolean(EXTRA_PLAY_WHEN_READY);
        }
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            Log.d(TAG, "           onStart:    " + mPlayerPos);
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || mSimpleExoPlayer == null)) {
            Log.d(TAG, "           onResume:    " + mPlayerPos);
            initializePlayer();
        }
    }

    public void setContent(String description, String videoUrl) {
        mDescription = description;
        mVideoUrl = videoUrl;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "             Setting the position:  " + mPlayerPos);
        outState.putLong(EXTRA_PLAYER_POS, mPlayerPos);
        outState.putBoolean(EXTRA_PLAY_WHEN_READY, mIsPlayWhenReady);
        super.onSaveInstanceState(outState);
    }

    private boolean mIsPlayWhenReady = false;
    private long mPlayerPos = 0;
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "       onPause!! :            " + mPlayerPos);
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "       onStop!! :            " + mPlayerPos);
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void initializePlayer(){
        if (mVideoUrl != null && !mVideoUrl.trim().isEmpty()) {
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext);
            mPlayerView.setPlayer(mSimpleExoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                    Util.getUserAgent(mContext, "MiriamsBakingApp"));
            // This is the MediaSource representing the media to be played.
            Uri uri = Uri.parse(mVideoUrl);
            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);
            // Prepare the player with the source.
            mSimpleExoPlayer.prepare(videoSource);
            Log.d(TAG, "           Player SAVED Position:    " + mPlayerPos);
            mSimpleExoPlayer.seekTo(mPlayerPos);
            Log.d(TAG, "           Player Position:    " + mSimpleExoPlayer.getCurrentPosition());
            mSimpleExoPlayer.setPlayWhenReady(mIsPlayWhenReady);
            } else {
                mPlayerView.setVisibility(View.GONE);
                mDefaultImgIv.setVisibility(View.VISIBLE);
            }
    }

    private void releasePlayer(){
        if (mSimpleExoPlayer != null){
            mPlayerPos = mSimpleExoPlayer.getCurrentPosition();
            mIsPlayWhenReady = mSimpleExoPlayer.getPlayWhenReady();
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "       onDestroy!! :            " + mPlayerPos);
        super.onDestroy();
    }
}
