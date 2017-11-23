package com.example.ccl.Cookiary.model;

/**
 * @author Chun-Chieh Liang
 * 11/20/17.
 */

public class Direction {
    private int mId;
    private int mRecipeId;
    private int mStep;
    private String mDescription;

    public Direction(){
    }

    public Direction(int id, int recipeId, int step, String description){
        mId = id;
        mRecipeId = recipeId;
        mStep = step;
        mDescription = description;
    }

    /**
     * get id of the direction
     * @return id
     */
    public int getId() {
        return mId;
    }

    /**
     * get recipe id of the direction
     * @return recipe id
     */
    public int getRecipeId() {
        return mRecipeId;
    }

    /**
     * get step of the direction
     * @return step
     */
    public int getStep() {
        return mStep;
    }

    /**
     * get description of the direction
     * @return description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * set id for the direction
     * @param id given id [INTEGER]
     */
    public void setId(int id) {
        mId = id;
    }

    /**
     * set recipe id for the direction
     * @param recipeId the recipe [INTEGER]
     */
    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

    /**
     * set step for the direction
     * @param step direction step [INTEGER]
     */
    public void setStep(int step) {
        mStep = step;
    }

    /**
     * set description for the direction
     * @param description the description of the direction [TEXT]
     */
    public void setDescription(String description) {
        mDescription = description;
    }
}
