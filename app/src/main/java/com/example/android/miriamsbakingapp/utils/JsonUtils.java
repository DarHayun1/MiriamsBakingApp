package com.example.android.miriamsbakingapp.utils;

import com.example.android.miriamsbakingapp.Objects.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();

    public static Recipe[] getRecipesArray(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            Recipe[] recipes = new Recipe[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                recipes[i] = parseRecipesJson(jsonArray.getJSONObject(i));
            }
            return recipes;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Recipe parseRecipesJson(JSONObject recipeJson) {
        Recipe recipe = null;
        ArrayList<Recipe.Ingredient> ingredients = new ArrayList<>();
        ArrayList<Recipe.Step> steps = new ArrayList<>();

        try {
            int id = recipeJson.getInt("id");
            String name = recipeJson.getString("name");
            JSONArray ingredJsonArray = recipeJson.getJSONArray("ingredients");
            for (int i = 0; i < ingredJsonArray.length(); i++) {
                JSONObject ingredient = ingredJsonArray.getJSONObject(i);
                ingredients.add(new Recipe.Ingredient(
                        ingredient.getDouble("quantity"),
                        ingredient.getString("measure"),
                        ingredient.getString("ingredient")));
            }
            JSONArray stepsJsonArray = recipeJson.getJSONArray("steps");
            for (int i = 0; i < stepsJsonArray.length(); i++) {
                JSONObject step = stepsJsonArray.getJSONObject(i);
                steps.add(new Recipe.Step(
                        step.getInt("id"),
                        step.getString("shortDescription"),
                        step.getString("description"),
                        step.getString("videoURL"),
                        step.getString("thumbnailURL")));
            }
            int servings = recipeJson.getInt("servings");
            String imageUrl = recipeJson.getString("image");
            Recipe.Ingredient[] ingredients1 = ingredients.toArray(new Recipe.Ingredient[ingredients.size()]);
            Recipe.Step[] steps1 = steps.toArray(new Recipe.Step[steps.size()]);
            recipe = new Recipe(id, name, ingredients1, steps1, servings, imageUrl);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return recipe;
    }


}
