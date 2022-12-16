import java.sql.DriverManager;

import ceng.ceng351.foodrecdb.IFOODRECDB;
import ceng.ceng351.foodrecdb.QueryResult.CuisineWithAverageResult;
import ceng.ceng351.foodrecdb.QueryResult.MenuItemAverageRatingResult;

public class FOODRECDB implements IFOODRECDB {

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int createTables() {
        /*  • MenuItems(itemID:int, itemName:varchar(40), cuisine:varchar(20), price:int)
            • Ingredients(ingredientID:int, ingredientName:varchar(40))
            • Includes(itemID:int, ingredientID:int)
            • Ratings(ratingID:int, itemID:int, rating:int, ratingDate:date)
            • DietaryCategories(ingredientID:int, dietaryCategory:varchar(20)) */
        
    }

    @Override
    public int dropTables() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insertMenuItems(MenuItem[] items) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insertIngredients(Ingredient[] ingredients) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insertIncludes(Includes[] includes) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insertDietaryCategories(DietaryCategory[] categories) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insertRatings(Rating[] ratings) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public MenuItem[] getMenuItemsWithGivenIngredient(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MenuItem[] getMenuItemsWithoutAnyIngredient() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Ingredient[] getNotIncludedIngredients() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MenuItem getMenuItemWithMostIngredients() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MenuItemAverageRatingResult[] getMenuItemsWithAvgRatings() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MenuItem[] getMenuItemsForDietaryCategory(String category) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Ingredient getMostUsedIngredient() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CuisineWithAverageResult[] getCuisinesWithAvgRating() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CuisineWithAverageResult[] getCuisinesWithAvgIngredientCount() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int increasePrice(String ingredientName, String increaseAmount) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Rating[] deleteOlderRatings(String date) {
        // TODO Auto-generated method stub
        return null;
    }

    
     

}
