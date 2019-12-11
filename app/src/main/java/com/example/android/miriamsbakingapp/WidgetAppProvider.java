package com.example.android.miriamsbakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.miriamsbakingapp.Objects.Recipe;
import com.example.android.miriamsbakingapp.ui.RecipeDetailActivity;
import com.example.android.miriamsbakingapp.ui.RecipesFragment;
import com.squareup.picasso.Picasso;

import static com.example.android.miriamsbakingapp.WidgetUpdateService.ACTION_UPDATE_RECIPE;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetAppProvider extends AppWidgetProvider {

    private static final String TAG = WidgetAppProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe recipe, int appWidgetId) {

        String recipeName = recipe.getmName();
        String recipeUrl = recipe.getmImageUrl();

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_app_provider);

        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RecipesFragment.RECIPE_CLICKED, recipe);
        intent.setAction(ACTION_UPDATE_RECIPE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_iv, pendingIntent);

        if (recipeUrl.trim().isEmpty()) {
            views.setImageViewResource(R.id.widget_iv, R.drawable.default_recipe_img);
        } else {
            Picasso.get()
                    .load(recipeUrl)
                    .into(views, R.id.widget_iv, appWidgetManager.getAppWidgetIds(
                            new ComponentName(context, WidgetAppProvider.class)));
        }
        Log.d(TAG, "                     ABC                 " + recipeName);
        if (!recipeName.trim().isEmpty())
            views.setTextViewText(R.id.widget_tv, recipeName);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    public static void updateWidgetRecipe(Context context, AppWidgetManager appWidgetManager,
                                          Recipe recipe, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

