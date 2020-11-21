package backend;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseHandler() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean login() {
        try {
            if (connection != null) {
                connection.close();
            }
            String loginInfo = readInfo();
            String username = loginInfo.split(";")[0];
            String password = loginInfo.split(";")[1];
            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("connected to oracle");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StoredIn[] getStoredInInfo(int storageID) {
        ArrayList<StoredIn> result = new ArrayList<StoredIn>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Food_Stored_In WHERE storageID = " + storageID );
            while(rs.next()) {
                StoredIn model = new StoredIn(rs.getInt("storageID"), rs.getString("Food_Name"),
                        "");
                result.add(model);
            }
            rs = stmt.executeQuery("SELECT * FROM Seasoning_Stored_In WHERE storageID = " + storageID );
            while(rs.next()) {
                StoredIn model = new StoredIn(rs.getInt("storageID"), "",
                        rs.getString("seasoning_name"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new StoredIn[result.size()]);
    }

    public ArrayList<String> showStoredIn(int storageID) {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("StorageID " + storageID + " has the following items:");
        StoredIn[] storedIns = getStoredInInfo(storageID);
        for (int i = 0; i < storedIns.length; i++) {
            StoredIn model = storedIns[i];
            if (!toPrint.contains(model.getFoodName()) && model.getFoodName() != "") {
                toPrint.add(model.getFoodName());
            }
            if (!toPrint.contains(model.getSeasoningName()) && model.getSeasoningName() != "") {
                toPrint.add(model.getSeasoningName());
            }
        }
        if (toPrint.size() == 1) {
            toPrint.clear();
            toPrint.add("StorageID: "  + storageID + " is empty!");
        }
        return toPrint;
    }

    public ArrayList<String> insertFoodToStorage(FoodStoredIn model) {
        ArrayList<String> toPrint = new ArrayList<String>();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Food_Stored_In VALUES (?,?)");
            ps.setString(1, model.getFoodName());
            ps.setInt(2, model.getStorageID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
            toPrint.add("Successfully added " + model.getFoodName() + " to StorageID: " + model.getStorageID());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return toPrint;
    }

    public ArrayList<String> insertSeasoningToStorage(SeasoningStoredIn model) {
        ArrayList<String> toPrint = new ArrayList<String>();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Seasoning_Stored_In VALUES (?,?)");
            ps.setString(1, model.getSeasoningName());
            ps.setInt(2, model.getStorageID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
            toPrint.add("Successfully added " + model.getSeasoningName() + " to StorageID: " + model.getStorageID());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return toPrint;
    }

    public ArrayList<String> removeFoodFromStorage(FoodStoredIn model) {
        ArrayList<String> toPrint = new ArrayList<String>();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Food_Stored_In WHERE Food_Name = '" + model.getFoodName() + "' AND storageID = " + model.getStorageID());
            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Food_Stored_In Food_Name: " + model.getFoodName() + " storageID: " + model.getStorageID() + " does not exist!");
            }
            connection.commit();

            ps.close();
            toPrint.add("Successfully removed " + model.getFoodName() + " from StorageID: " + model.getStorageID());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return toPrint;
    }

    public ArrayList<String> removeSeasoningFromStorage(SeasoningStoredIn model) {
        ArrayList<String> toPrint = new ArrayList<String>();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Seasoning_Stored_In WHERE seasoning_name = '" + model.getSeasoningName() + "' AND storageID = " + model.getStorageID());
            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Seasoning_Stored_In seasoning_name: " + model.getSeasoningName() + " storageID: " + model.getStorageID() + " does not exist!");
            }
            connection.commit();

            ps.close();
            toPrint.add("Successfully removed " + model.getSeasoningName() + " from StorageID: " + model.getStorageID());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return toPrint;
    }

    public FoodType[] findFoodOnExpirationDate(String date) {
        ArrayList<FoodType> result = new ArrayList<FoodType>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Food_Name FROM FoodType WHERE expiryDate = '" + date + "'");
            while(rs.next()) {
                FoodType model = new FoodType(rs.getString("Food_Name"),
                        0, 0, 0, 0, 0, 0, "", "");
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new FoodType[result.size()]);
    }

    public ArrayList<String> showFoodOnExpirationDate(String date) {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("The following food item(s) expire on the date: " + date);
        FoodType[] foodTypes = findFoodOnExpirationDate(date);
        for (int i = 0; i < foodTypes.length; i++) {
            FoodType model = foodTypes[i];
            if (!toPrint.contains(model.getFoodName())) {
                toPrint.add(model.getFoodName());
            }
        }
        if (toPrint.size() == 1) {
            toPrint.clear();
            toPrint.add("No food expires on the date: " + date);
        }
        return toPrint;
    }

    public FoodType[] findNutritionalValueOfFood(String condition1, String condition2, String condition3) {
        ArrayList<FoodType> result = new ArrayList<FoodType>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Food_Name, " + condition1 + ", " + condition2 + ", " + condition3 + " FROM FoodType");
            while(rs.next()) {
                FoodType model = new FoodType(rs.getString("Food_Name"),
                        0, 0, 0, 0, 0, 0, "", "");
                if (condition1.equals("Calories") || condition2.equals("Calories") || condition3.equals("Calories")) {
                    model.setCalories(rs.getInt("Calories"));
                }
                if (condition1.equals("Fat") || condition2.equals("Fat") || condition3.equals("Fat")) {
                    model.setFat(rs.getInt("Fat"));
                }
                if (condition1.equals("Sodium") || condition2.equals("Sodium") || condition3.equals("Sodium")) {
                    model.setSodium(rs.getInt("Sodium"));
                }
                if (condition1.equals("Carbohydrate") || condition2.equals("Carbohydrate") || condition3.equals("Carbohydrate")) {
                    model.setCarbohydrate(rs.getInt("Carbohydrate"));
                }
                if (condition1.equals("Protein") || condition2.equals("Protein") || condition3.equals("Protein")) {
                    model.setProtein(rs.getInt("Protein"));
                }
                if (condition1.equals("Vitamin") || condition2.equals("Vitamin") || condition3.equals("Vitamin")) {
                    model.setVitamin(rs.getInt("Vitamin"));
                }
                if (condition1.equals("datePurchased") || condition2.equals("datePurchased") || condition3.equals("datePurchased")) {
                    model.setDatePurchased(rs.getString("datePurchased"));
                }
                if (condition1.equals("expiryDate") || condition2.equals("expiryDate") || condition3.equals("expiryDate")) {
                    model.setExpiryDate(rs.getString("expiryDate"));
                }
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new FoodType[result.size()]);
    }

    public ArrayList<String> showNutritionalValueOfFood(String condition1, String condition2, String condition3) {
        ArrayList<String> toPrint = new ArrayList<String>();
        String header = "Name";
        if (condition1.equals("Calories") || condition2.equals("Calories") || condition3.equals("Calories")) {
            header = header + ", Calories";
        }
        if (condition1.equals("Fat") || condition2.equals("Fat") || condition3.equals("Fat")) {
            header = header + ", Fat";
        }
        if (condition1.equals("Sodium") || condition2.equals("Sodium") || condition3.equals("Sodium")) {
            header = header + ", Sodium";
        }
        if (condition1.equals("Carbohydrate") || condition2.equals("Carbohydrate") || condition3.equals("Carbohydrate")) {
            header = header + ", Carbohydrate";
        }
        if (condition1.equals("Protein") || condition2.equals("Protein") || condition3.equals("Protein")) {
            header = header + ", Protein";
        }
        if (condition1.equals("Vitamin") || condition2.equals("Vitamin") || condition3.equals("Vitamin")) {
            header = header + ", Vitamin";
        }
        if (condition1.equals("datePurchased") || condition2.equals("datePurchased") || condition3.equals("datePurchased")) {
            header = header + ", datePurchased";
        }
        if (condition1.equals("expiryDate") || condition2.equals("expiryDate") || condition3.equals("expiryDate")) {
            header = header + ", expiryDate";
        }
        toPrint.add(header);
        FoodType[] foodTypes = findNutritionalValueOfFood(condition1, condition2, condition3);
        for (int i = 0; i < foodTypes.length; i++) {
            FoodType model = foodTypes[i];
            String add = model.getFoodName();
            if (condition1.equals("Calories") || condition2.equals("Calories") || condition3.equals("Calories")) {
                add = add + ", " + model.getCalories();
            }
            if (condition1.equals("Fat") || condition2.equals("Fat") || condition3.equals("Fat")) {
                add = add + ", " + model.getFat();
            }
            if (condition1.equals("Sodium") || condition2.equals("Sodium") || condition3.equals("Sodium")) {
                add = add + ", " + model.getSodium();
            }
            if (condition1.equals("Carbohydrate") || condition2.equals("Carbohydrate") || condition3.equals("Carbohydrate")) {
                add = add + ", " + model.getCarbohydrate();
            }
            if (condition1.equals("Protein") || condition2.equals("Protein") || condition3.equals("Protein")) {
                add = add + ", " + model.getProtein();
            }
            if (condition1.equals("Vitamin") || condition2.equals("Vitamin") || condition3.equals("Vitamin")) {
                add = add + ", " + model.getVitamin();
            }
            if (condition1.equals("datePurchased") || condition2.equals("datePurchased") || condition3.equals("datePurchased")) {
                add = add + ", " + model.getDatePurchased();
            }
            if (condition1.equals("expiryDate") || condition2.equals("expiryDate") || condition3.equals("expiryDate")) {
                add = add + ", " + model.getExpiryDate();
            }
            toPrint.add(add);
        }
        return(toPrint);
    }

    public CountFoodStorage[] getCountOfFoodInStorage() {
        ArrayList<CountFoodStorage> result = new ArrayList<CountFoodStorage>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT storageID, COUNT(*) FROM Food_Stored_In GROUP BY storageID");
            while(rs.next()) {
                CountFoodStorage model = new CountFoodStorage(rs.getInt("storageID"), rs.getInt("COUNT(*)"));
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new CountFoodStorage[result.size()]);
    }

    public ArrayList<String> showCountOfFoodInStorage() {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("storageID, Count of Food");
        CountFoodStorage[] countFoodStorages = getCountOfFoodInStorage();
        for (int i = 0; i < countFoodStorages.length; i++) {
            CountFoodStorage model = countFoodStorages[i];
            String add = model.getStorageID() + ", " + model.getCount();
            toPrint.add(add);
        }
        return toPrint;
    }

    public RecipeContains[] getRecipeWhichContainsFood(String foodName) {
        ArrayList<RecipeContains> result = new ArrayList<RecipeContains>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT recipe_name FROM Recipe_Contains WHERE Food_Name = '" + foodName + "'");
            while(rs.next()) {
                RecipeContains model = new RecipeContains(rs.getString("recipe_name"), "" , "");
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new RecipeContains[result.size()]);
    }

    public ArrayList<String> showRecipeWhichContainsFood(String foodName) {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("Names of recipes which contain " + foodName + ":");
        RecipeContains[] recipeContainsFood = getRecipeWhichContainsFood(foodName);
        for (int i = 0; i < recipeContainsFood.length; i++) {
            RecipeContains model = recipeContainsFood[i];
            String add = model.getRecipeName();
            toPrint.add(add);
        }
        if (toPrint.size() == 1) {
            toPrint.clear();
            toPrint.add("There are no recipes which contain " + foodName);
        }
        return toPrint;
    }

    public RecipeContains[] getRecipeWhichContainsSeasoning(String seasoningName) {
        ArrayList<RecipeContains> result = new ArrayList<RecipeContains>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT recipe_name FROM Recipe_Contains WHERE seasoning_name = '" + seasoningName + "'");
            while(rs.next()) {
                RecipeContains model = new RecipeContains(rs.getString("recipe_name"), "" , "");
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new RecipeContains[result.size()]);
    }

    public ArrayList<String> showRecipeWhichContainsSeasoning(String seasoningName) {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("Names of recipes which contain " + seasoningName + ":");
        RecipeContains[] recipeContainsFood = getRecipeWhichContainsSeasoning(seasoningName);
        for (int i = 0; i < recipeContainsFood.length; i++) {
            RecipeContains model = recipeContainsFood[i];
            String add = model.getRecipeName();
            toPrint.add(add);
        }
        if (toPrint.size() == 1) {
            toPrint.clear();
            toPrint.add("There are no recipes which contain " + seasoningName);
        }
        return toPrint;
    }

    public ArrayList<String> updateRecipe(String recipeName, String foodName) {
        ArrayList<String> toPrint = new ArrayList<String>();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Recipe_Contains SET Food_Name = '" + foodName + "' WHERE recipe_name = '" + recipeName + "'");

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                toPrint.add(WARNING_TAG + " Recipe called " + recipeName + " does not exist!");
            } else {
                toPrint.add("Successfully Updated Recipe: " + recipeName);
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return toPrint;
    }

    public RecipeNameCalories[] getRecipeWithLessCalories(int calories) {
        ArrayList<RecipeNameCalories> result = new ArrayList<RecipeNameCalories>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT recipe_name FROM Recipe_Contains r, FoodType f WHERE r.Food_Name = f.Food_Name AND f.calories < " + calories);
            while(rs.next()) {
                RecipeNameCalories model = new RecipeNameCalories(rs.getString("recipe_name"), calories);
                result.add(model);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new RecipeNameCalories[result.size()]);
    }

    public ArrayList<String> showRecipeWithLessCalories(int calories) {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("Names of recipes which contain less than " + calories + " calories:");
        RecipeNameCalories[] recipeNameCalories = getRecipeWithLessCalories(calories);
        for (int i = 0; i < recipeNameCalories.length; i++) {
            RecipeNameCalories model = recipeNameCalories[i];
            String add = model.getRecipeName();
            toPrint.add(add);
        }
        if (toPrint.size() == 1) {
            toPrint.clear();
            toPrint.add("There are no recipes which contain less than " + calories + " calories!");
        }
        return toPrint;
    }

    public ArrayList<String> peopleWhoLikesAllFood() {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("FamilyID, Name");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.name, p.familyID FROM Person_Belongs_To p WHERE NOT EXISTS (SELECT Food_Name FROM FoodType MINUS SELECT food_name FROM Likes l WHERE l.person_name = p.name AND l.familyID = p.familyID)");
            while(rs.next()) {
                toPrint.add(rs.getInt("familyID") + ", " + rs.getString("name"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return toPrint;
    }

    public ArrayList<String> peopleWhoDislikesAllFood() {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("FamilyID, Name");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.name, p.familyID FROM Person_Belongs_To p WHERE NOT EXISTS (SELECT Food_Name FROM FoodType MINUS SELECT food_name FROM Dislikes d WHERE d.person_name = p.name AND d.familyID = p.familyID)");
            while(rs.next()) {
                toPrint.add(rs.getInt("familyID") + ", " + rs.getString("name"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return toPrint;
    }

    public ArrayList<String> atLeastThreeAdults() {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("FamilyID, Number of adults");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT familyID, COUNT(*) FROM Person_Belongs_To WHERE age > 18 GROUP BY familyID HAVING COUNT(*) > 2");
            while(rs.next()) {
                toPrint.add(rs.getInt("familyID") + ", " + rs.getInt("COUNT(*)"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return toPrint;
    }

    public ArrayList<String> veryOld() {
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("Name, FamilyID, Age");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name, familyID, age FROM Person_Belongs_To p WHERE p.age > ALL (SELECT AVG (p2.age) FROM Person_Belongs_To p2 GROUP BY familyID)");
            while(rs.next()) {
                toPrint.add(rs.getString("name") + ", " + rs.getInt("familyID") + ", " + rs.getInt("age"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return toPrint;
    }

    private String readInfo() {
        String info = "";
        try {
            File file = new File("src/account/account.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                info = scanner.nextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

}
