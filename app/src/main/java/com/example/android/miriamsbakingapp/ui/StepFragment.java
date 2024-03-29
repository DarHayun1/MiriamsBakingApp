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
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepFragment extends Fragment {


    private static final String TAG = StepFragment.class.getSimpleName();
    public static final String EXTRA_PLAY_WHEN_READY = "extra_play_when_ready";
    public static final String EXTRA_PLAYER_POS = "extra_player_pos";
    private static final String EXTRA_VIDEO_URL = "extra_video_url";
    private static final String EXTRA_DESCRIPTION = "extra_desc";

    private String mDescription;
    private String mVideoUrl;
    private SimpleExoPlayer mSimpleExoPlayer;
    private Context mContext;

    private boolean mIsPlayWhenReady = false;
    private long mPlayerPos = 0;

    private PlayerView mPlayerView;
    private TextView mDescriptionTv;
    private ImageView mDefaultImgIv;
    private TextView mStepNumTv;
    private DefaultDataSourceFactory mDataSourceFactory;


    public StepFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            mPlayerPos = savedInstanceState.getLong(EXTRA_PLAYER_POS);
            mDescription = savedInstanceState.getString(EXTRA_DESCRIPTION);
            mVideoUrl = savedInstanceState.getString(EXTRA_VIDEO_URL);
            mIsPlayWhenReady = savedInstanceState.getBoolean(EXTRA_PLAY_WHEN_READY);
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

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || mSimpleExoPlayer == null)) {
            initializePlayer();
        }
    }

    private void settingDescTv() {
        if (mDescription != null && mDescription.substring(1, 3).equals(". ")) {
            String stepNumText = "Step #" + mDescription.substring(0, 1);
            mStepNumTv.setText(stepNumText);
            mStepNumTv.setVisibility(View.VISIBLE);
            mDescription = mDescription.substring(3).replaceAll("\\.\\s?", "\\.\n");
        }
        mDescriptionTv.setText(mDescription);
    }

    public void setContent(String description, String videoUrl) {
        mDescription = description;
        mVideoUrl = videoUrl;
    }

    public void updateStep(String description, String videoUrl) {

        setContent(description, videoUrl);
        mIsPlayWhenReady = false;
        mPlayerPos = 0;
        settingDescTv();
        initializePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(EXTRA_PLAYER_POS, mPlayerPos);
        outState.putBoolean(EXTRA_PLAY_WHEN_READY, mIsPlayWhenReady);
        outState.putString(EXTRA_DESCRIPTION, mDescription);
        outState.putString(EXTRA_VIDEO_URL, mVideoUrl);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void initializePlayer(){
        if (mVideoUrl != null && !mVideoUrl.trim().isEmpty()) {
            if (mSimpleExoPlayer == null) {
                mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext);
                mPlayerView.setPlayer(mSimpleExoPlayer);
                mDataSourceFactory = new DefaultDataSourceFactory(mContext,
                        Util.getUserAgent(mContext, "MiriamsBakingApp"));
            }
            // This is the MediaSource representing the media to be played.
            Uri uri = Uri.parse(mVideoUrl);
            MediaSource videoSource = new ProgressiveMediaSource.Factory(mDataSourceFactory)
                    .createMediaSource(uri);
            // Prepare the player with the source.
            mSimpleExoPlayer.prepare(videoSource);
            mSimpleExoPlayer.seekTo(mPlayerPos);
            mSimpleExoPlayer.setPlayWhenReady(mIsPlayWhenReady);
            mPlayerView.setVisibility(View.VISIBLE);
            mDefaultImgIv.setVisibility(View.GONE);
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

}
