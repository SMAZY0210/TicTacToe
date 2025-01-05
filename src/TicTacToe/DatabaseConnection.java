package TicTacToe;

import java.sql.*;

public class DatabaseConnection {
    private static String URL = "jdbc:mysql://localhost:3306/tictactoe";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private void createDB() {
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS tictactoe;";
        String URL = "jdbc:mysql://localhost:3306";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Execute the SQL query
            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Database 'tictactoe' created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void dbconnect() {
        createDB();
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                userId INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(50) NOT NULL,
                password VARCHAR(255) NOT NULL,
                total_matches INT DEFAULT 0,
                win INT DEFAULT 0,
                loss INT DEFAULT 0,
                draw INT DEFAULT 0
            )
            """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'users' created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean addUser(User user) {
        String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        String insertUserQuery = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Check if the user already exists
            try (PreparedStatement checkStmt = connection.prepareStatement(checkUserQuery)) {
                checkStmt.setString(1, user.getUsername());
                ResultSet resultSet = checkStmt.executeQuery();

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    System.out.println("User already exists!");
                    return false; // User already exists
                }
            }

            // Insert the user into the table
            try (PreparedStatement insertStmt = connection.prepareStatement(insertUserQuery)) {
                insertStmt.setString(1, user.getUsername());
                insertStmt.setString(2, user.getPassword());
                int rowsInserted = insertStmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("User added successfully!");
                    return true; // User added successfully
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Failed to add user
    }

    public static boolean usernameExists(String username) {
        String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement checkStmt = connection.prepareStatement(checkUserQuery)) {

            // Set the parameter in the query
            checkStmt.setString(1, username);

            // Execute the query
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true; // Username exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Username does not exist
    }

    public static boolean validPass(String username, String password) {
        String checkPasswordQuery = "SELECT password FROM users WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement checkStmt = connection.prepareStatement(checkPasswordQuery)) {

            // Set the parameter in the query
            checkStmt.setString(1, username);

            // Execute the query
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                // Retrieve the stored password
                String storedPassword = resultSet.getString("password");

                // Compare the stored password with the provided password
                return storedPassword.equals(password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Password does not match or username does not exist
    }

    public static void incrementMatches(String username) {
        String updateQuery = "UPDATE users SET total_matches = total_matches + 1 WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            // Set the parameter
            updateStmt.setString(1, username);
            updateStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void incrementWin(String username) {
        String updateQuery = "UPDATE users SET win = win + 1 WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            updateStmt.setString(1, username);
            updateStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void incrementLoss(String username) {
        String updateQuery = "UPDATE users SET loss = loss + 1 WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            // Set the parameter
            updateStmt.setString(1, username);
            updateStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void incrementDraws(String username) {
        String updateQuery = "UPDATE users SET draw = draw + 1 WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            // Set the parameter
            updateStmt.setString(1, username);
            updateStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
