package com.example.android.miriamsbakingapp;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import com.example.android.miriamsbakingapp.Objects.Recipe;
import com.example.android.miriamsbakingapp.ui.RecipeDetailActivity;
import com.example.android.miriamsbakingapp.ui.RecipesFragment;
import com.example.android.miriamsbakingapp.utils.JsonUtils;
import com.example.android.miriamsbakingapp.utils.NetworkUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DetailActivityIntentTest {

    private static final String FIRST_RECIPE_NAME = "Nutella Pie";
    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeDetailActivity.class,
                    true,
                    false);
    private Recipe mRecipe;

    @Before
    public void settingRecipe() {
        try {
            String jsonText = NetworkUtils.getRecipesFromHttp();
            mRecipe = JsonUtils.getRecipesArray(jsonText)[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void detailActivityIntentTest() {
        Intent intent = new Intent();
        intent.putExtra(RecipesFragment.RECIPE_CLICKED, mRecipe);
        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.detail_name)).check(matches(withText(FIRST_RECIPE_NAME)));
    }


}
