package com.example.android.miriamsbakingapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.android.miriamsbakingapp.Objects.Recipe;
import com.example.android.miriamsbakingapp.R;

import static com.example.android.miriamsbakingapp.ui.RecipesFragment.RECIPE_CLICKED;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener {

    private static final String STEP_POS_TWO_PANE_KEY = "step_pos_twopane_key";
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();

    private boolean mTwoPane;

    private Recipe mRecipe;
    private int mStepPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(RECIPE_CLICKED)) {
            mRecipe = intent.getParcelableExtra(RECIPE_CLICKED);
            if (savedInstanceState == null) {
                mStepPos = 0;
            } else {
                mStepPos = savedInstanceState.getInt(STEP_POS_TWO_PANE_KEY);
            }

            if (findViewById(R.id.two_pane_step_container) != null) {
                mTwoPane = true;
                Recipe.Step[] steps = mRecipe.getmSteps();

                if (savedInstanceState == null) {

                    StepFragment stepFragment = new StepFragment();
                    stepFragment.setContent(steps[mStepPos].getDescription(),
                            steps[mStepPos].getVideoUrl());

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.two_pane_step_container, stepFragment)
                            .commit();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepSelected(int position) {
        if (!mTwoPane) {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putExtra(RecipeStepActivity.RECIPE_KEY, mRecipe);
            intent.putExtra(RecipeStepActivity.POSITION_KEY, position);
            startActivity(intent);
        } else {
            Recipe.Step[] steps = mRecipe.getmSteps();

            mStepPos = position;
            StepFragment stepFragment = new StepFragment();
            stepFragment.setContent(steps[mStepPos].getDescription(),
                    steps[mStepPos].getVideoUrl());

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.two_pane_step_container, stepFragment)
                    .commit();
        }
    }
}
