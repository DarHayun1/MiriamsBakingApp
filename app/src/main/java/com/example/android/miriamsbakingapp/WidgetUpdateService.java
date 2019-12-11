package com.example.android.miriamsbakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.miriamsbakingapp.Objects.Recipe;

public class WidgetUpdateService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE = "com.example.android.miriamsbakingapp.action.update_recipe";
    public static final String EXTRA_RECIPE_UPDATE = "com.example.android.miriamsbakingapp.extra.recipe_update";
    private static final String TAG = WidgetUpdateService.class.getSimpleName();

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    public static void startActionUpdateRecipeWidget(Context context, Recipe recipe) {
        Log.d(TAG, "                  222222                      updating widget! (Service)");
        Intent serviceIntent = new Intent(context, WidgetUpdateService.class);
        serviceIntent.setAction(WidgetUpdateService.ACTION_UPDATE_RECIPE);
        serviceIntent.putExtra(WidgetUpdateService.EXTRA_RECIPE_UPDATE, recipe);
        context.startService(serviceIntent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "                  11111111                      updating widget! (Service)");

        if (intent.getAction().equals(ACTION_UPDATE_RECIPE)) {
            if (intent.hasExtra(EXTRA_RECIPE_UPDATE)) {
                Log.d(TAG, "updating widget! (Service)");
                Recipe recipe = intent.getParcelableExtra(EXTRA_RECIPE_UPDATE);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                        new ComponentName(this, WidgetAppProvider.class));
                WidgetAppProvider.updateWidgetRecipe(this, appWidgetManager,
                        recipe, appWidgetIds);
            }
        }
    }
}
