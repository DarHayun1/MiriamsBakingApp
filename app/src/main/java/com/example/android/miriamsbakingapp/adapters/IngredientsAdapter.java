package com.example.android.miriamsbakingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.miriamsbakingapp.Objects.Recipe;
import com.example.android.miriamsbakingapp.R;

import java.text.DecimalFormat;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientVH> {

    private static final String TAG = IngredientsAdapter.class.getSimpleName();
    Recipe.Ingredient[] mIngredients;

    public IngredientsAdapter(Recipe.Ingredient[] ingredients) {
        this.mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientVH holder, int position) {

        TextView ingredientNameTv = holder.itemView.findViewById(R.id.ingredient_name);
        ingredientNameTv.setText(mIngredients[position].getIngredient());

        TextView ingredientMeasureTv = holder.itemView.findViewById(R.id.ingredient_measure);
        double quantity = mIngredients[position].getQuantity();
        DecimalFormat format = new DecimalFormat("0.##");
        String measureText = format.format(quantity) + " "
                + mIngredients[position].getMeasure_type();
        ingredientMeasureTv.setText(measureText);

    }

    @Override
    public int getItemCount() {
        return mIngredients.length;
    }

    public class IngredientVH extends RecyclerView.ViewHolder {

        public IngredientVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
