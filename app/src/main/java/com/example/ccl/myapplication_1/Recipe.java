package com.example.ccl.myapplication_1;

public class Recipe {
    private String mName;
    private String mAuthor;
    private String mDescription; // a short description
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private int mYield; // number of servings
    private String mCreatedDate;

    /** Constant value that represents no image was provided for this recipe */
    private static final int NO_IMAGE_PROVIDED = -1;

    public Recipe() {
    }

    public Recipe(String name, String description, int imageResourceId) {
        mName = name;
        mDescription = description;
        mImageResourceId = imageResourceId;
        mAuthor = "Hank";
        mYield = 1;
    }

    /**
     *  get the name of the recipe
     * @return
     */
    public String getName () {
        return mName;
    }

    /**
     * get the author of the recipe
     * @return
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * get the short description of the recipe
     * @return
     */
    public String getDescription() {
        return mDescription;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
