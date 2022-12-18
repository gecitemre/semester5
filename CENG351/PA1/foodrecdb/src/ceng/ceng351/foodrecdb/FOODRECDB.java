package ceng.ceng351.foodrecdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ceng.ceng351.foodrecdb.QueryResult.CuisineWithAverageResult;
import ceng.ceng351.foodrecdb.QueryResult.MenuItemAverageRatingResult;

public class FOODRECDB implements IFOODRECDB {

    private static final String user = "e2521581"; // userName
    private static final String password = "Dfl1UIs_px0qy9GL"; // password
    private static final String host = "momcorp.ceng.metu.edu.tr"; // host name
    private static final String database = "db2521581"; // database name
    private static final int port = 8080; // port
    private Connection connection;

    @Override
    public void initialize() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createTables() {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            // MenuItems(itemID:int, itemName:varchar(40), cuisine:varchar(20), price:int)
            statement.executeUpdate("CREATE TABLE MenuItems(" +
                    "itemID INT PRIMARY KEY," +
                    "itemName VARCHAR(40)," +
                    "cuisine VARCHAR(20)," +
                    "price INT)");
            result++;

            // Ingredients(ingredientID:int, ingredientName:varchar(40))
            statement.executeUpdate("CREATE TABLE Ingredients(" +
                    "ingredientID INT PRIMARY KEY," +
                    "ingredientName VARCHAR(40))");
            result++;

            // Includes(itemID:int, ingredientID:int)
            statement.executeUpdate("CREATE TABLE Includes(" +
                    "itemID INT," +
                    "ingredientID INT," +
                    "PRIMARY KEY(itemID, ingredientID))");
            result++;

            // Ratings(ratingID:int, itemID:int, rating:int, ratingDate:date)
            statement.executeUpdate("CREATE TABLE Ratings(" +
                    "ratingID INT PRIMARY KEY," +
                    "itemID INT," +
                    "rating INT," +
                    "ratingDate DATE)");
            result++;

            // DietaryCategories(ingredientID:int, dietaryCategory:varchar(20))
            statement.executeUpdate("CREATE TABLE DietaryCategories(" +
                    "ingredientID INT," +
                    "dietaryCategory VARCHAR(20)," +
                    "PRIMARY KEY(ingredientID, dietaryCategory))");
            result++;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int dropTables() {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE MenuItems");
            result++;
            statement.executeUpdate("DROP TABLE Ingredients");
            result++;
            statement.executeUpdate("DROP TABLE Includes");
            result++;
            statement.executeUpdate("DROP TABLE Ratings");
            result++;
            statement.executeUpdate("DROP TABLE DietaryCategories");
            result++;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int insertMenuItems(MenuItem[] items) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            for (MenuItem item : items) {
                result += statement.executeUpdate("INSERT INTO MenuItems VALUES(" +
                        item.getItemID() + ", '" +
                        item.getItemName() + "', '" +
                        item.getCuisine() + "', " +
                        item.getPrice() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int insertIngredients(Ingredient[] ingredients) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            for (Ingredient ingredient : ingredients) {
                result += statement.executeUpdate("INSERT INTO Ingredients VALUES(" +
                        ingredient.getIngredientID() + ", '" +
                        ingredient.getIngredientName() + "')");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int insertIncludes(Includes[] includes) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            for (Includes include : includes) {
                result += statement.executeUpdate("INSERT INTO Includes VALUES(" +
                        include.getItemID() + ", " +
                        include.getIngredientID() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int insertDietaryCategories(DietaryCategory[] categories) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            for (DietaryCategory category : categories) {
                result += statement.executeUpdate("INSERT INTO DietaryCategories VALUES(" +
                        category.getIngredientID() + ", '" +
                        category.getDietaryCategory() + "')");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int insertRatings(Rating[] ratings) {
        int result = 0;
        try {
            Statement statement = connection.createStatement();
            for (Rating rating : ratings) {
                result += statement.executeUpdate("INSERT INTO Ratings VALUES(" +
                        rating.getRatingID() + ", " +
                        rating.getItemID() + ", " +
                        rating.getRating() + ", '" +
                        rating.getRatingDate() + "')");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public MenuItem[] getMenuItemsWithGivenIngredient(String name) {
        List<MenuItem> items = new ArrayList<MenuItem>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT itemID, itemName, cuisine, price FROM MenuItems WHERE itemID IN (SELECT itemID FROM Includes WHERE ingredientID IN (SELECT ingredientID FROM Ingredients WHERE ingredientName = '"
                            + name + "')) ORDER BY itemID ASC");
            while (resultSet.next()) {
                items.add(new MenuItem(resultSet.getInt("itemID"), resultSet.getString("itemName"),
                        resultSet.getString("cuisine"), resultSet.getInt("price")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items.toArray(new MenuItem[items.size()]);
    }

    @Override
    public MenuItem[] getMenuItemsWithoutAnyIngredient() {
        List<MenuItem> items = new ArrayList<MenuItem>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery(
                            "SELECT itemID, itemName, cuisine, price FROM MenuItems WHERE itemID NOT IN (SELECT itemID FROM Includes) ORDER BY itemID ASC");
            while (resultSet.next()) {
                items.add(new MenuItem(resultSet.getInt("itemID"), resultSet.getString("itemName"),
                        resultSet.getString("cuisine"), resultSet.getInt("price")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items.toArray(new MenuItem[items.size()]);
    }

    @Override
    public Ingredient[] getNotIncludedIngredients() {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT ingredientID, ingredientName FROM Ingredients WHERE ingredientID NOT IN (SELECT ingredientID FROM Includes) ORDER BY ingredientID ASC");
            while (resultSet.next()) {
                ingredients
                        .add(new Ingredient(resultSet.getInt("ingredientID"), resultSet.getString("ingredientName")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingredients.toArray(new Ingredient[ingredients.size()]);
    }

    @Override
    public MenuItem getMenuItemWithMostIngredients() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT itemID, itemName, cuisine, price FROM MenuItems WHERE itemID IN (SELECT itemID FROM Includes GROUP BY itemID HAVING COUNT(*) = (SELECT MAX(count) FROM (SELECT itemID, COUNT(*) AS count FROM Includes GROUP BY itemID) AS temp))");
            if (resultSet.next()) {
                return new MenuItem(resultSet.getInt("itemID"), resultSet.getString("itemName"),
                        resultSet.getString("cuisine"), resultSet.getInt("price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MenuItemAverageRatingResult[] getMenuItemsWithAvgRatings() {
        List<MenuItemAverageRatingResult> results = new ArrayList<MenuItemAverageRatingResult>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT MenuItems.itemID, itemName, AVG(rating) AS avgRating "
                            + "FROM MenuItems LEFT OUTER JOIN Ratings ON MenuItems.itemID = Ratings.itemID "
                            + "GROUP BY MenuItems.itemID "
                            + "ORDER BY avgRating DESC");
            while (resultSet.next()) {
                results.add(new MenuItemAverageRatingResult(resultSet.getString("itemID"),
                        resultSet.getString("itemName"), resultSet.getString("avgRating")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results.toArray(new MenuItemAverageRatingResult[results.size()]);
    }

    @Override
    public MenuItem[] getMenuItemsForDietaryCategory(String category) {
        List<MenuItem> items = new ArrayList<MenuItem>();
        try {
            Statement statement = connection.createStatement();
            // all the ingredients of this menu item should be from the given dietary
            // category.
            ResultSet resultSet = statement.executeQuery(
                    "SELECT itemID, itemName, cuisine, price FROM MenuItems WHERE NOT EXISTS(SELECT * FROM Includes WHERE itemID = MenuItems.itemID AND ingredientID NOT IN (SELECT ingredientID FROM Ingredients NATURAL JOIN DietaryCategories WHERE dietaryCategory = '"
                            + category + "') ORDER BY itemID ASC) AND EXISTS(SELECT * FROM Includes WHERE itemID = MenuItems.itemID)");
            while (resultSet.next()) {
                items.add(new MenuItem(resultSet.getInt("itemID"), resultSet.getString("itemName"),
                        resultSet.getString("cuisine"), resultSet.getInt("price")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items.toArray(new MenuItem[items.size()]);
    }

    @Override
    public Ingredient getMostUsedIngredient() {
        try {
            Statement statement = connection.createStatement();
            // get the ingredientID of the most used ingredient
            ResultSet resultSet = statement.executeQuery(
                    "SELECT ingredientID, ingredientName FROM Includes NATURAL JOIN Ingredients GROUP BY ingredientID HAVING COUNT(*) = (SELECT MAX(count) FROM (SELECT COUNT(*) AS count FROM Includes GROUP BY ingredientID) AS temp)");
            if (resultSet.next()) {
                return new Ingredient(resultSet.getInt("ingredientID"), resultSet.getString("ingredientName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CuisineWithAverageResult[] getCuisinesWithAvgRating() {
        List<CuisineWithAverageResult> results = new ArrayList<CuisineWithAverageResult>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                "SELECT cuisine, AVG(rating) AS avgRating "
                        + "FROM MenuItems LEFT OUTER JOIN Ratings ON MenuItems.itemID = Ratings.itemID "
                        + "GROUP BY MenuItems.cuisine "
                        + "ORDER BY avgRating DESC");
            while (resultSet.next()) {
                results.add(new CuisineWithAverageResult(resultSet.getString("cuisine"),
                        resultSet.getString("avgRating")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results.toArray(new CuisineWithAverageResult[results.size()]);
    }

    @Override
    public int increasePrice(String ingredientName, String increaseAmount) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate("UPDATE MenuItems SET price = price + " + increaseAmount
                    + " WHERE itemID IN (SELECT itemID FROM Includes WHERE ingredientID IN (SELECT ingredientID FROM Ingredients WHERE ingredientName = '"
                    + ingredientName + "'))");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Rating[] deleteOlderRatings(String date) {
        List<Rating> ratings = new ArrayList<Rating>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT ratingID, itemID, rating, ratingDate FROM Ratings WHERE ratingDate < '" + date
                            + "' ORDER BY ratingID ASC");
            while (resultSet.next()) {
                ratings.add(new Rating(resultSet.getInt("ratingID"), resultSet.getInt("itemID"),
                        resultSet.getInt("rating"), resultSet.getString("ratingDate")));
            }
            statement.executeUpdate("DELETE FROM Ratings WHERE ratingDate < '" + date + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ratings.toArray(new Rating[ratings.size()]);
    }

    @Override
    public QueryResult.CuisineWithAverageResult[] getCuisinesWithAvgIngredientCount() {
        List<CuisineWithAverageResult> results = new ArrayList<CuisineWithAverageResult>();
        try {
            Statement statement = connection.createStatement();
            // get cuisine and average count of ingredients for each cuisine
            ResultSet resultSet = statement.executeQuery(
                    "SELECT cuisine, AVG(count) AS avgCount FROM "
                    + "(SELECT MenuItems.itemID, COUNT(ingredientID) AS count FROM MenuItems LEFT JOIN Includes ON MenuItems.itemID=Includes.itemID GROUP BY MenuItems.itemID) "
                    + "counts NATURAL JOIN MenuItems "
                    + "GROUP BY cuisine ORDER BY avgCount DESC");
            while (resultSet.next()) {
                results.add(new CuisineWithAverageResult(resultSet.getString("cuisine"),
                        resultSet.getString("avgCount")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results.toArray(new CuisineWithAverageResult[results.size()]);
    }
}
