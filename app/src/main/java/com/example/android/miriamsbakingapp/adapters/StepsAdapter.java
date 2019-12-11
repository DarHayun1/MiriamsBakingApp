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

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private static final String TAG = StepsAdapter.class.getSimpleName();

    private Recipe.Step[] mSteps;
    private StepClickListener mClickListener;

    public StepsAdapter(Recipe.Step[] steps) {
        this.mSteps = steps;

    }

    public void setClickListener(StepClickListener clickListener){
        mClickListener = clickListener;
    }
    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stepts_list_item, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        ImageView imageView = holder.itemView.findViewById(R.id.step_item_iv);
        String thumbnailUrl = mSteps[position].getThumbnailUrl();
        if (thumbnailUrl != null && !thumbnailUrl.trim().isEmpty()) {
            Picasso.get()
                    .load(thumbnailUrl)
                    .into(imageView); }
        TextView shortDescTv = holder.itemView.findViewById(R.id.step_short_desc_tv);
        shortDescTv.setText(mSteps[position].getShortDesc());
        TextView stepNumTv = holder.itemView.findViewById(R.id.step_num_tv);
        String stepNumText = "Recipe Introduction";
        if (mSteps[position].getId()>0)
            stepNumText = "Step #" + (mSteps[position].getId());
        stepNumTv.setText(stepNumText);

    }

    @Override
    public int getItemCount() {
        if (mSteps != null)
            return mSteps.length;
        else
            return 14;
    }
    public interface StepClickListener{
        void onStepClicked(int position);
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onStepClicked(getAdapterPosition());

        }
    }
}
