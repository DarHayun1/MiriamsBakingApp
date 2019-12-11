package com.example.android.miriamsbakingapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    private int mId;
    private String mName;
    private Ingredient[] mIngredients;
    private Step[] mSteps;
    private int mServings;
    private String mImageUrl;

    public Recipe() {
    }

    public Recipe(int mId, String mName, Ingredient[] mIngredients, Step[] mSteps, int mServings, String mImageUrl) {
        this.mId = mId;
        this.mName = mName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        this.mServings = mServings;
        this.mImageUrl = mImageUrl;
    }

    protected Recipe(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        int ingLength = in.readInt();
        this.mIngredients = new Ingredient[ingLength];
        in.readTypedArray(this.mIngredients, Ingredient.CREATOR);
        int stepsLength = in.readInt();
        this.mSteps = new Step[stepsLength];
        in.readTypedArray(this.mSteps, Step.CREATOR);
        this.mServings = in.readInt();
        this.mImageUrl = in.readString();
    }

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public Ingredient[] getmIngredients() {
        return mIngredients;
    }

    public Step[] getmSteps() {
        return mSteps;
    }

    public int getmServings() {
        return mServings;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeInt(this.mIngredients.length);
        dest.writeTypedArray(this.mIngredients, 0);
        dest.writeInt(this.mSteps.length);
        dest.writeTypedArray(this.mSteps, flags);
        dest.writeInt(this.mServings);
        dest.writeString(this.mImageUrl);
    }

    public static class Ingredient implements Parcelable {

        public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
            @Override
            public Ingredient createFromParcel(Parcel source) {
                return new Ingredient(source);
            }

            @Override
            public Ingredient[] newArray(int size) {
                return new Ingredient[size];
            }
        };
        protected double quantity;
        protected String measure_type;
        protected String ingredient;


        public Ingredient(double quantity, String measure_type, String ingredient) {
            this.quantity = quantity;
            this.measure_type = measure_type;
            this.ingredient = ingredient;
        }

        protected Ingredient(Parcel in) {
            this.quantity = in.readDouble();
            this.measure_type = in.readString();
            this.ingredient = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.quantity);
            dest.writeString(this.measure_type);
            dest.writeString(this.ingredient);
        }

        public double getQuantity() {
            return quantity;
        }

        public String getMeasure_type() {
            return measure_type;
        }

        public String getIngredient() {
            return ingredient;
        }
    }

    public static class Step implements Parcelable {

        public static final Creator<Step> CREATOR = new Creator<Step>() {
            @Override
            public Step createFromParcel(Parcel source) {
                return new Step(source);
            }

            @Override
            public Step[] newArray(int size) {
                return new Step[size];
            }
        };
        protected int id;
        protected String shortDesc;
        protected String description;
        protected String videoUrl;
        protected String thumbnailUrl;

        public Step(int id, String shortDesc, String description, String videoUrl, String thumbnailUrl) {
            this.id = id;
            this.shortDesc = shortDesc;
            this.description = description;
            if (thumbnailUrl.endsWith(".mp4") && videoUrl.trim().isEmpty()) {
                this.videoUrl = thumbnailUrl;
                this.thumbnailUrl = "";
            } else {
                this.videoUrl = videoUrl;
                this.thumbnailUrl = thumbnailUrl;
            }
        }

        protected Step(Parcel in) {
            this.id = in.readInt();
            this.shortDesc = in.readString();
            this.description = in.readString();
            this.videoUrl = in.readString();
            this.thumbnailUrl = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.shortDesc);
            dest.writeString(this.description);
            dest.writeString(this.videoUrl);
            dest.writeString(this.thumbnailUrl);
        }

        public int getId() {
            return id;
        }

        public String getShortDesc() {
            return shortDesc;
        }

        public String getDescription() {
            return description;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }
    }
}
