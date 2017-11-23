package com.example.ccl.Cookiary.model;

/**
 * @author Chun-Chieh Liang
 *
 */
public class Recipe {
    private int mRecipe_id;
    private String mName, mAuthor;
    private String mDescription, mCategory; // a short description
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private int mYield; // number of servings
    private String mCookingTime;
    private String mDifficulty;
    private String mCreatedDate;

    /** Constant value that represents no image was provided for this recipe */
    private static final int NO_IMAGE_PROVIDED = -1;

    public Recipe(){

    }

    public Recipe(String name, String description, int imageResourceId) {
        mName = name;
        mDescription = description;
        mImageResourceId = imageResourceId;
    }

    public Recipe(String name, String description, String category, int imageResourceId) {
        mName = name;
        mDescription = description;
        if (category.equals("")) {
            mCategory = "Uncategorized";
        } else {
            mCategory = category;
        }
        mImageResourceId = imageResourceId;
    }

    public Recipe(String name, String description, String category, int imageResourceId, int yield, String cookingTime, String difficulty){
        mName = name;
        mDescription = description;
        mImageResourceId = imageResourceId;
        mCategory = category;
        mYield = yield;
        mCookingTime = cookingTime;
        mDifficulty = difficulty;
    }

    /**
     *  get the recipe id
     * @return mRecipe_id
     */
    public int getRecipe_id() {
        return mRecipe_id;
    }

    /**
     *  get the name of the recipe
     * @return mName
     */
    public String getName () {
        return mName;
    }

    /**
     * get the author of the recipe
     * @return mAuthor
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * get the short description of the recipe
     * @return mDescription
     */
    public String getDescription() {
        return mDescription;
    }


    /**
     * get the category of the recipe
     * @return mCategory
     */
    public String getCategory () {
        return  mCategory;
    }

    /**
     * get the image of the recipe
     * @return imageId
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * get the cooking time of the recipe
     * @return mTime
     */
    public String getCookingTime() { return mCookingTime; }

    /**
     * get the servings of the recipe
     * @return mYield
     */
    public int getYield() { return mYield;}

    /**
     * get the difficulty of the recipe
     * @return mDifficulty
     */
    public String getDifficulty() {return mDifficulty; }

    /**
     * set the id of the recipe
     * @param recipe_id id
     */
    public void setRecipe_id(int recipe_id) {
        mRecipe_id = recipe_id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setmCategory(String category) {
        mCategory = category;
    }

    public void setmImageResourceId(int imageResourceId){
        mImageResourceId = imageResourceId;
    }

    public void setCookingTime (String cookingTime) {mCookingTime = cookingTime; }

    public void setYield (int yield) { mYield = yield; }

    public void setDifficulty (String difficulty) { mDifficulty = difficulty;}



    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "mRecipe_id=" + mRecipe_id +
                ", mName='" + mName + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mCookingTime=" + mCookingTime +
                ", mYield=" + mYield +
                ", mDifficulty=" + mDifficulty +
                ", mCreatedDate='" + mCreatedDate + '\'' +
                '}';
    }
}
