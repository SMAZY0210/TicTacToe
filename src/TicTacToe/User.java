package TicTacToe;

public class User {
    private String username;
    private String password;

    // Constructor to create a User object with username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters for username and password
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{username='" + username + "', password='" + password + "'}";
    }
}