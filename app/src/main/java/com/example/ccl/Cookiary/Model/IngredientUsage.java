package com.example.ccl.Cookiary.Model;

/**
 * @author Chun-Chieh Liang
 * 11/19/17.
 */

public class IngredientUsage {
    private int mId;
    private int mRecipeId;
    private String mIngredientName;
    private int mQuantity;
    private String mMeasurement;

    public IngredientUsage(int id, int recipe_id, String ingredientName, int quantity, String measurement){
        mId = id;
        mRecipeId = recipe_id;
        mIngredientName = ingredientName;
        mQuantity = quantity;
        mMeasurement = measurement;
    }

    public int getId() {
        return mId;
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public String getIngredientName() {
        return mIngredientName;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public String getMeasurement() {
        return mMeasurement;
    }

    public void setId(int Id) {
        this.mId = Id;
    }

    public void setRecipeId(int recipeId) {
        this.mRecipeId = recipeId;
    }

    public void setIngredientName(String ingredientName) {
        this.mIngredientName = ingredientName;
    }

    public void setQuantity(int quantity) {
        this.mQuantity = quantity;
    }

    public void setMeasurement(String measurement) {
        this.mMeasurement = measurement;
    }
}
