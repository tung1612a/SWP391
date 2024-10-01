/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Food {
    private int foodID;
    private String foodName;
    private String status;
    private int categoryID;
    private String image;
    private List<Ingredient> ingredients; // Many-to-many relationship with Ingredient

    public Food() {
    }

    // Constructor
    public Food(int foodID, String foodName, String status, int categoryID, String image, List<Ingredient> ingredients) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.status = status;
        this.categoryID = categoryID;
        this.image = image;
        this.ingredients = ingredients;
    }

    // Getters and Setters
    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
