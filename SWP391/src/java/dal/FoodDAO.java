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
public class FoodDAO extends DBContext {

    public List<Food> getPaginatedFoods(int page, int size, String search, String sortColumn, Boolean ascending) {
        List<Food> foods = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT f.FoodID, f.FoodName, f.Status, f.CategoryID, f.Image, 
               i.IngredientID, i.IngredientName, i.Quantity 
        FROM Food f
        LEFT JOIN Ingredient_Food ifd ON f.FoodID = ifd.FoodID
        LEFT JOIN Ingredient i ON ifd.IngredientID = i.IngredientID
    """);

        // Adding search conditions
        List<String> conditions = new ArrayList<>();
        if (search != null && !search.isEmpty()) {
            conditions.add("(f.FoodName LIKE ? OR f.Status LIKE ? OR f.Image LIKE ? OR i.IngredientName LIKE ?)");
        }

        // If there are any conditions, append them to the query
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        // Adding sorting logic
        sql.append(" ORDER BY ");
        if (sortColumn != null && !sortColumn.isEmpty()) {
            sql.append(sortColumn); // Add specified column for sorting
        } else {
            sql.append("f.FoodID"); // Default sorting column
        }

        // Apply sorting order only if ascending is not null
        if (ascending != null) {
            sql.append(ascending ? " ASC" : " DESC");
        }

        // Adding pagination
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try ( PreparedStatement st = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Set search parameters if the search string is provided
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search + "%";
                st.setString(paramIndex++, searchPattern); // For f.FoodName
                st.setString(paramIndex++, searchPattern); // For f.Status
                st.setString(paramIndex++, searchPattern); // For f.Image
                st.setString(paramIndex++, searchPattern); // For i.IngredientName
            }

            // Set pagination parameters
            int offset = page * size;
            st.setInt(paramIndex++, offset);
            st.setInt(paramIndex, size);

            ResultSet rs = st.executeQuery();

            Map<Integer, Food> foodMap = new HashMap<>();
            while (rs.next()) {
                int foodID = rs.getInt("FoodID");

                Food food = foodMap.getOrDefault(foodID, new Food(
                        foodID,
                        rs.getString("FoodName"),
                        rs.getString("Status"),
                        rs.getInt("CategoryID"),
                        rs.getString("Image"),
                        new ArrayList<>()
                ));

                int ingredientID = rs.getInt("IngredientID");
                if (ingredientID > 0) {
                    Ingredient ingredient = new Ingredient(
                            ingredientID,
                            rs.getString("IngredientName"),
                            rs.getInt("Quantity"),
                            null
                    );
                    food.getIngredients().add(ingredient);
                }

                foodMap.putIfAbsent(foodID, food);
            }

            foods.addAll(foodMap.values());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sql);
        return foods; // Returns empty list if no foods are found
    }

    public int getTotalPages(int size, String search) {
        int totalFoods = 0;
        StringBuilder sql = new StringBuilder("""
        SELECT COUNT(DISTINCT f.FoodID) AS total
        FROM Food f
        LEFT JOIN Ingredient_Food ifd ON f.FoodID = ifd.FoodID
        LEFT JOIN Ingredient i ON ifd.IngredientID = i.IngredientID
    """);

        // Adding search conditions
        List<String> conditions = new ArrayList<>();
        if (search != null && !search.isEmpty()) {
            conditions.add("(f.FoodName LIKE ? OR f.Status LIKE ? OR f.Image LIKE ? OR i.IngredientName LIKE ?)");
        }

        // Append search conditions if any
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        try ( PreparedStatement st = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Set search parameters if the search string is provided
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search + "%";
                st.setString(paramIndex++, searchPattern); // For f.FoodName
                st.setString(paramIndex++, searchPattern); // For f.Status
                st.setString(paramIndex++, searchPattern); // For f.Image
                st.setString(paramIndex++, searchPattern); // For i.IngredientName
            }

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                totalFoods = rs.getInt("total"); // Get the total count of filtered foods
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalFoods / size);
        return totalPages;
    }

}
