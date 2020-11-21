package backend;

import model.FoodStoredIn;
import model.FoodType;
import model.SeasoningStoredIn;
import model.StoredIn;

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
        if (toPrint.size() == 0) {
            System.out.println("StorageID: "  + storageID + " is empty!");
        } else {
            System.out.println("StorageID: " + storageID + " has the following items:");
            for (int i = 0; i < toPrint.size(); i++) {
                System.out.println(toPrint.get(i));
            }
        }
        return toPrint;
    }

    public void insertFoodToStorage(FoodStoredIn model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Food_Stored_In VALUES (?,?)");
            ps.setString(1, model.getFoodName());
            ps.setInt(2, model.getStorageID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
            System.out.println("Successfully added " + model.getFoodName() + " to StorageID: " + model.getStorageID());
            System.out.println();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertSeasoningToStorage(SeasoningStoredIn model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Seasoning_Stored_In VALUES (?,?)");
            ps.setString(1, model.getSeasoningName());
            ps.setInt(2, model.getStorageID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
            System.out.println("Successfully added " + model.getSeasoningName() + " to StorageID: " + model.getStorageID());
            System.out.println();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void removeFoodFromStorage(FoodStoredIn model) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Food_Stored_In WHERE Food_Name = '" + model.getFoodName() + "' AND storageID = " + model.getStorageID());
            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Food_Stored_In Food_Name: " + model.getFoodName() + " storageID: " + model.getStorageID() + " does not exist!");
            }
            connection.commit();

            ps.close();
            System.out.println("Successfully removed " + model.getFoodName() + " from StorageID: " + model.getStorageID());
            System.out.println();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void removeSeasoningFromStorage(SeasoningStoredIn model) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Seasoning_Stored_In WHERE seasoning_name = '" + model.getSeasoningName() + "' AND storageID = " + model.getStorageID());
            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Seasoning_Stored_In seasoning_name: " + model.getSeasoningName() + " storageID: " + model.getStorageID() + " does not exist!");
            }
            connection.commit();

            ps.close();
            System.out.println("Successfully removed " + model.getSeasoningName() + " from StorageID: " + model.getStorageID());
            System.out.println();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
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

    public void showFoodOnExpirationDate(String date) {
        ArrayList<String> toPrint = new ArrayList<String>();
        FoodType[] foodTypes = findFoodOnExpirationDate(date);
        for (int i = 0; i < foodTypes.length; i++) {
            FoodType model = foodTypes[i];
            if (!toPrint.contains(model.getFoodName())) {
                toPrint.add(model.getFoodName());
            }
        }
        if (toPrint.size() == 0) {
            System.out.println("No food expires on the date: " + date);
        } else {
            System.out.println("The following food items expires on the date of " + date + ":");
            for (int i = 0; i < toPrint.size(); i++) {
                System.out.println(toPrint.get(i));
            }
        }
        System.out.println();
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
        if (toPrint.size() == 0) {
            System.out.println("You don't own any food!");
        } else {
            System.out.println("Information of food items:");
            for (int i = 0; i < toPrint.size(); i++) {
                System.out.println(toPrint.get(i));
            }
        }
        System.out.println();
        return(toPrint);
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

    public void deleteHi(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM hi WHERE id = ?");
            ps.setInt(1, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Hi " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }



    public void updateTable(int id, String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE hi SET first = ? WHERE id = ?");
            ps.setString(1, name);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Hi " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void dropTableIfExists() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("hi")) {
                    stmt.execute("DROP TABLE hi");
                    break;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
