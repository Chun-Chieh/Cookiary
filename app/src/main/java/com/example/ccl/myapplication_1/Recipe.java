package com.example.ccl.myapplication_1;

public class Recipe {
    private int mRecipe_id;
    private String mName;
    private String mAuthor;
    private String mDescription, mCategory; // a short description
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private int mYield; // number of servings
    private String mCreatedDate;

    /** Constant value that represents no image was provided for this recipe */
    private static final int NO_IMAGE_PROVIDED = -1;

    Recipe(){

    }

    Recipe(String name, String description, int imageResourceId) {
        mName = name;
        mDescription = description;
        mCategory = "Uncategorized";
        mImageResourceId = imageResourceId;
        mAuthor = "Tom";
        mYield = 1;
    }

    Recipe(String name, String description, String category, int imageResourceId) {
        mName = name;
        mDescription = description;
        mCategory = category;
        mImageResourceId = imageResourceId;
        mAuthor = "Tom";
        mYield = 1;
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

    public String getCategory () {
        return  mCategory;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

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

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
