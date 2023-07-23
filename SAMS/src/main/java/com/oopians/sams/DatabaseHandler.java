package com.oopians.sams;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author fidel
 */
public class DatabaseHandler {
    // Required variables:
    private static DatabaseHandler instance = null;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_sams";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection conn;

    private DatabaseHandler(){
        // private constructor - prevents direct instantiation
    }

    // Function to create only one instance
    public static DatabaseHandler getInstance(){
        if(instance == null){
            instance = new DatabaseHandler();
        }
        return instance;
    }

    // DatabaseConnector methods:

    // Method for establishing a connection
    public void establishConnection() {
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connected successfully!");
        } catch (SQLException | NullPointerException | ClassNotFoundException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    // Method for closing a connection
    public void closeConnection(){
        try{
            conn.close();
            System.out.println("Connection closed successfully!");

        }catch(NullPointerException | SQLException e){
            System.out.println("Failed to close connection: "+e.getMessage());
        }
    }
    
    public boolean setData(String table, Map<String, Object> data) {
    String[] columns = data.keySet().toArray(new String[0]);
    Object[] values = data.values().toArray();
    String[] placeholders = new String[values.length];
    Arrays.fill(placeholders, "?");

    // Generating lists of columns and placeholders:
    String columnList = String.join(",", columns);
    String placeholderList = String.join(",", placeholders);

    String sql = "INSERT INTO " + table + " (" + columnList + ") VALUES (" + placeholderList + ")";
    this.establishConnection();
    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        for (int i = 0; i < values.length; i++) {
            stmt.setObject(i + 1, values[i]);
        }
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
