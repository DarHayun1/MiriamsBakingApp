package com.example.android.miriamsbakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        mNextButton = findViewById(R.id.next_step_button);
        mPrevButton = findViewById(R.id.prev_step_button);

        if (savedInstanceState != null) {
            mStepPosition = savedInstanceState.getInt(POSITION_KEY, 0);
            mRecipe = savedInstanceState.getParcelable(RECIPE_KEY);
            mSteps = mRecipe.getmSteps();
        } else {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(RECIPE_KEY)) {
                    mRecipe = intent.getParcelableExtra(RECIPE_KEY);
                    mSteps = mRecipe.getmSteps();
                }
                if (intent.hasExtra(POSITION_KEY)) {
                    mStepPosition = intent.getIntExtra(POSITION_KEY, 0);
                }

                StepFragment stepFragment = new StepFragment();
                stepFragment.setContent(mSteps[mStepPosition].getDescription(),
                        mSteps[mStepPosition].getVideoUrl());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.step_details_container, stepFragment, FRAGMENT_TAG)
                        .commit();
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
        changeStep();

        if (mStepPosition + 1 == mSteps.length)
            mNextButton.setVisibility(View.INVISIBLE);
        else if (mStepPosition == 1)
            mPrevButton.setVisibility(View.VISIBLE);
    }

    public void onPrevClicked(View view) {

        mStepPosition--;

        changeStep();

        if (mStepPosition == 0) {
            mPrevButton.setVisibility(View.INVISIBLE);
        } else if (mStepPosition == mSteps.length - 2)
            mNextButton.setVisibility(View.VISIBLE);
    }

    public void changeStep() {

        StepFragment fragment = (StepFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment != null)
            fragment.updateStep(mSteps[mStepPosition].getDescription(),
                  mSteps[mStepPosition].getVideoUrl());
    }

}
