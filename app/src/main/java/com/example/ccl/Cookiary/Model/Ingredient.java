package com.example.ccl.Cookiary.Model;

/**
 * @author Chun-Chieh Liang
 * 11/19/17.
 */

public class Ingredient {
    private int mId;
    private String mName;

    public Ingredient(){}

    public Ingredient(int id, String name) {
        mId = id;
        mName = name;
    }

    public Ingredient(String name) {
        mName = name;
    }

    /**
     * get id of the ingredient
     * @return id
     */
    public int getId () {
        return mId;
    }

    /**
     * get name of the ingredient
     * @return name
     */
    public String getName(){
        return mName;
    }

    /**
     * set id of the ingredient
     * @param id given id
     */
    public void setId(int id) {
        mId = id;
    }

    /**
     * set name of the ingredient
     * @param name given name
     */
    public void setName(String name) {
        mName = name;
    }
}
