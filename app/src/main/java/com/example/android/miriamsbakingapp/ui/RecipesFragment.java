package com.example.android.miriamsbakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.miriamsbakingapp.Objects.Recipe;
import com.example.android.miriamsbakingapp.R;
import com.example.android.miriamsbakingapp.Services.FetchRecipesTask;
import com.example.android.miriamsbakingapp.Services.OnEventListener;
import com.example.android.miriamsbakingapp.adapters.RecipesAdapter;

public class RecipesFragment extends Fragment implements RecipesAdapter.ItemClickListener,
        OnEventListener<Recipe[]> {


    public static final String RECIPE_CLICKED = "recipe_clicked";
    private static final String TAG = RecipesFragment.class.getSimpleName();
    private RecyclerView mRecipesRv;
    private RecipesAdapter mRecipesAdapter;
    public Recipe[] mRecipes;
    private Context mContext;
    private RecyclerView.LayoutManager mLayoutManager;

    public RecipesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipes_list, container,
                false);
        mRecipesRv = rootView.findViewById(R.id.recipes_rv);
        mRecipesAdapter = new RecipesAdapter(mRecipes);
        if (RecipeDetailFragment.isTablet(mContext))
            mLayoutManager = new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false);
        else
            mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mRecipesRv.setLayoutManager(mLayoutManager);
        mRecipesRv.setAdapter(mRecipesAdapter);
        mRecipesAdapter.setClickListener(this);

        new FetchRecipesTask(this, mContext).execute();

        return rootView;
    }


    @Override
    public void onItemClick(int position) {
        if (mRecipes[position] != null) {
            Intent intent = new Intent(mContext, RecipeDetailActivity.class);
            intent.putExtra(RECIPE_CLICKED, mRecipes[position]);
            startActivity(intent);
        }

    }


    @Override
    public void onSuccess(Recipe[] recipes) {
        mRecipes = recipes;
        mRecipesAdapter.setRecipesArray(mRecipes);
    }

    @Override
    public void onFailure(Exception e) {
    }
}
