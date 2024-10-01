/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.*;

/**
 *
 * @author ADMIN
 */
public class IngredientDAO extends DBContext {

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = """
                 SELECT i.IngredientID, i.IngredientName, i.Quantity, 
                        f.FoodID, f.FoodName, f.Status, f.CategoryID, f.Image 
                 FROM Ingredient i
                 LEFT JOIN Ingredient_Food ifd ON i.IngredientID = ifd.IngredientID
                 LEFT JOIN Food f ON ifd.FoodID = f.FoodID""";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();

            Map<Integer, Ingredient> ingredientMap = new HashMap<>();
            while (rs.next()) {
                int ingredientID = rs.getInt("IngredientID");

                Ingredient ingredient = ingredientMap.getOrDefault(ingredientID, new Ingredient(
                        ingredientID,
                        rs.getString("IngredientName"),
                        rs.getInt("Quantity"),
                        new ArrayList<>()
                ));

                int foodID = rs.getInt("FoodID");
                if (foodID > 0) {
                    Food food = new Food(
                            foodID,
                            rs.getString("FoodName"),
                            rs.getString("Status"),
                            rs.getInt("CategoryID"),
                            rs.getString("Image"),
                            null
                    );
                    ingredient.getFoods().add(food);
                }

                ingredientMap.putIfAbsent(ingredientID, ingredient);
            }

            ingredients.addAll(ingredientMap.values());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ingredients; // Returns empty list if no ingredients are found
    }
}
