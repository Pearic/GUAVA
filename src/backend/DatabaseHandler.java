package backend;

import model.Hi;

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

    public void createTable() throws Exception {
        dropTableIfExists();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE hi (id int PRIMARY KEY, first varchar(20), last varchar(20))");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            System.out.println("CreateTable Complete!");

            Hi hi1 = new Hi(1, "Charlie", "Li");
            insertHi(hi1);
            Hi hi2 = new Hi(2, "Sean", "Lee");
            insertHi(hi2);
            showHi();
            deleteHi(1);
            showHi();
            updateTable(2, "Charlie");
            showHi();
        }
    }

    public Hi[] getHiInfo() {
        ArrayList<Hi> result = new ArrayList<Hi>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM hi");

            while(rs.next()) {
                Hi model = new Hi(rs.getInt("id"),
                        rs.getString("first"),
                        rs.getString("last"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Hi[result.size()]);
    }

    public void showHi() {
        Hi[] his = getHiInfo();

        for (int i = 0; i < his.length; i++) {
            Hi model = his[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getId());
            System.out.printf("%-20.20s", model.getFirst());
            System.out.printf("%-20.20s", model.getLast());
            System.out.println();
        }
    }

    public void insertHi(Hi model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO hi VALUES (?,?,?)");
            ps.setInt(1, model.getId());
            ps.setString(2, model.getFirst());
            ps.setString(3, model.getLast());

            ps.executeUpdate();
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
