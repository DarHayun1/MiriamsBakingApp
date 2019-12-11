package com.example.android.miriamsbakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.android.miriamsbakingapp.Objects.Recipe;
import com.example.android.miriamsbakingapp.R;

public class RecipeStepActivity extends AppCompatActivity {

    public final static String RECIPE_KEY = "recipe_key";
    public final static String POSITION_KEY = "position_key";
    private static final String TAG = RecipeStepActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG = "fragment_tag";

    private Recipe.Step[] mSteps;
    private int mStepPosition;
    private Button mNextButton;
    private Button mPrevButton;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "             onCreate(Activity)  ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        mNextButton = findViewById(R.id.next_step_button);
        mPrevButton = findViewById(R.id.prev_step_button);

        if (savedInstanceState != null) {
            mStepPosition = savedInstanceState.getInt(POSITION_KEY, 0);
            mRecipe = savedInstanceState.getParcelable(RECIPE_KEY);
            mSteps = mRecipe.getmSteps();

            FragmentManager fragmentManager = getSupportFragmentManager();

            StepFragment fragment = (StepFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);

            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container,
                            fragmentManager.findFragmentByTag(FRAGMENT_TAG),
                            FRAGMENT_TAG)
                    .commit();
            Log.d(TAG, "             onCreate(Activity) 222222222222  ");
        } else {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(RECIPE_KEY)) {
                    mRecipe = intent.getParcelableExtra(RECIPE_KEY);
                    Log.d(TAG, mRecipe.toString());
                    mSteps = mRecipe.getmSteps();
                }
                if (intent.hasExtra(POSITION_KEY)) {
                    mStepPosition = intent.getIntExtra(POSITION_KEY, 0);
                }

                Log.d(TAG, "             onCreate(Activity)creating a fragment  1  ");

                StepFragment stepFragment = new StepFragment();
                stepFragment.setContent(mSteps[mStepPosition].getDescription(),
                        mSteps[mStepPosition].getVideoUrl());
                Log.d(TAG, "             onCreate(Activity)creating a fragment  2  ");

                FragmentManager fragmentManager = getSupportFragmentManager();

                Log.d(TAG, "             onCreate(Activity)creating a fragment  3  ");

                fragmentManager.beginTransaction()
                        .replace(R.id.step_details_container, stepFragment, FRAGMENT_TAG)
                        .commit();

                Log.d(TAG, "             onCreate(Activity)creating a fragment  4  ");
            }
        }

        if (mStepPosition != mSteps.length - 1)
            mNextButton.setVisibility(View.VISIBLE);
        if (mStepPosition != 0)
            mPrevButton.setVisibility(View.VISIBLE);


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(POSITION_KEY, mStepPosition);
        outState.putParcelable(RECIPE_KEY, mRecipe);
        super.onSaveInstanceState(outState);
    }

    public void onNextClicked(View view) {
        mStepPosition++;
        changeFragment();

        if (mStepPosition + 1 == mSteps.length)
            mNextButton.setVisibility(View.INVISIBLE);
        else if (mStepPosition == 1)
            mPrevButton.setVisibility(View.VISIBLE);
    }

    public void onPrevClicked(View view) {

        mStepPosition--;

        changeFragment();

        if (mStepPosition == 0) {
            mPrevButton.setVisibility(View.INVISIBLE);
        } else if (mStepPosition == mSteps.length - 2)
            mNextButton.setVisibility(View.VISIBLE);
    }

    public void changeFragment() {

        Log.d(TAG, "             ChangeFragment  ");
        StepFragment fragment = new StepFragment();

        fragment.setContent(mSteps[mStepPosition].getDescription(),
                mSteps[mStepPosition].getVideoUrl());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.step_details_container, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "             onCreate(Activity) 222222222222  ");

    }
}
