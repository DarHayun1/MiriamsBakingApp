package com.example.android.miriamsbakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.miriamsbakingapp.Objects.Recipe;
import com.example.android.miriamsbakingapp.R;
import com.example.android.miriamsbakingapp.Services.WidgetUpdateService;
import com.example.android.miriamsbakingapp.adapters.IngredientsAdapter;
import com.example.android.miriamsbakingapp.adapters.StepsAdapter;

public class RecipeDetailFragment extends Fragment implements StepsAdapter.StepClickListener, View.OnClickListener {

    private static final String TAG = RecipeDetailFragment.class.getSimpleName();

    private Context mContext;
    private TextView mNameTv;
    private StepsAdapter mStepsAdapter;
    private RecyclerView mStepsRv;
    private LinearLayoutManager mStepsLayoutManager;
    private RecyclerView mIngredientsRv;
    private IngredientsAdapter mIngrAdapter;
    private LinearLayoutManager mIngLayoutManager;
    private TextView mServingsTv;
    private ImageView mSaveToWidgetIv;

    OnStepClickListener mCallback;
    private Recipe mRecipe;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater
                .inflate(R.layout.fragment_recipe_details, container, false);

        Intent parentIntent = getActivity().getIntent();
        if (parentIntent.hasExtra(RecipesFragment.RECIPE_CLICKED)) {
            mRecipe = parentIntent.getParcelableExtra(RecipesFragment.RECIPE_CLICKED);

            mStepsRv = (RecyclerView) rootView.findViewById(R.id.steps_rv);

            if (isTablet(mContext) ||
                    getResources().getConfiguration().orientation ==
                            Configuration.ORIENTATION_PORTRAIT)
                mStepsLayoutManager =
                        new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            else
                mStepsLayoutManager =
                        new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            mStepsRv.setLayoutManager(mStepsLayoutManager);

            mNameTv = (TextView) rootView.findViewById(R.id.detail_name);
            mNameTv.setText(mRecipe.getmName());

            mServingsTv = (TextView) rootView.findViewById(R.id.detail_servings);
            mServingsTv.setText(String.valueOf(mRecipe.getmServings()));

            mIngredientsRv = (RecyclerView) rootView.findViewById(R.id.ingredients_rv);
            mIngrAdapter = new IngredientsAdapter(mRecipe.getmIngredients());
            mIngredientsRv.setAdapter(mIngrAdapter);
            mIngLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            mIngredientsRv.setLayoutManager(mIngLayoutManager);

            mSaveToWidgetIv = rootView.findViewById(R.id.save_to_widget_iv);
            mSaveToWidgetIv.setOnClickListener(this);

            mStepsAdapter = new StepsAdapter(mRecipe.getmSteps());
            mStepsRv.setAdapter(mStepsAdapter);
            mStepsAdapter.setClickListener(this);
        }
        return rootView;
    }

    @Override
    public void onStepClicked(int position) {
        mCallback.onStepSelected(position);
    }

    @Override
    public void onClick(View view) {
        WidgetUpdateService.startActionUpdateRecipeWidget(mContext, mRecipe);
        Toast.makeText(mContext, "Widget's ingredients list updated", Toast.LENGTH_LONG).show();

    }


}
