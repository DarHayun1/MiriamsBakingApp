package com.example.android.miriamsbakingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.miriamsbakingapp.Objects.Recipe;
import com.example.android.miriamsbakingapp.R;
import com.squareup.picasso.Picasso;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private Recipe[] mRecipesArray;
    private ItemClickListener mClickListener;

    public RecipesAdapter(Recipe[] recipes) {
        mRecipesArray = recipes;
    }

    public void setRecipesArray(Recipe[] recipes) {
        mRecipesArray = recipes;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        if (mRecipesArray != null)
            return mRecipesArray.length;
        else return 4;
    }

    @NonNull
    @Override
    public RecipesAdapter.RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipes_list_item, parent, false);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        if (mRecipesArray != null) {
            if (mRecipesArray[position].getmImageUrl().equals("")) {
                ImageView imageView = holder.itemView.findViewById(R.id.list_item_recipe_iv);
                imageView.setImageResource(R.drawable.default_recipe_img);
            } else {
                Picasso.get()
                        .load(mRecipesArray[position].getmImageUrl())
                        .into(holder.imageView);
            }
            TextView textView = holder.itemView.findViewById(R.id.master_list_recipe_name);
            textView.setText(mRecipesArray[position].getmName());
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        ImageView imageView;

        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_item_recipe_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            mClickListener.onItemClick(getAdapterPosition());
        }
    }
}
