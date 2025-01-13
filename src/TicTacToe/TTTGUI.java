package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static TicTacToe.DatabaseConnection.*;

public class TTTGUI {
    private final Image logo;

    public TTTGUI() {
        this.logo = new ImageIcon("E:\\Java\\Programs\\TicTacToe\\src\\logo.png").getImage();
    }

    public int displayWelcomeScreen() {
        JDialog welcomeDialog = new JDialog((Frame) null, "TicTacToe", true);
        welcomeDialog.setLayout(new GridLayout(2, 1));
        welcomeDialog.setSize(550, 500);

        JLabel welcomeLabel = new JLabel("Welcome to Tic Tac Toe!", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(18.0f));
        welcomeDialog.add(welcomeLabel);

        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("LOG IN");
        JButton registerButton = new JButton("REGISTER");

        final int[] returnVal = {0};

        loginButton.addActionListener(e -> {
            returnVal[0] = 1;
            welcomeDialog.dispose(); // Close dialog when a button is clicked
        });

        registerButton.addActionListener(e -> {
            returnVal[0] = 2;
            welcomeDialog.dispose();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        welcomeDialog.add(buttonPanel);

        welcomeDialog.setLocationRelativeTo(null); // Center on screen
        welcomeDialog.setVisible(true); // Blocks until dialog is closed

        return returnVal[0];
    }


    public User registration() {
        JDialog registerDialog = new JDialog((Frame) null, "Register to TIC TAC TOE", true);
        registerDialog.setLayout(new GridLayout(3, 2, 10, 10));
        registerDialog.setSize(300, 200);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton registerButton = new JButton("Register");
        final User[] user = {null};

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            user[0] = new User(username, password);
            registerDialog.dispose();
        });

        registerDialog.add(usernameLabel);
        registerDialog.add(usernameField);
        registerDialog.add(passwordLabel);
        registerDialog.add(passwordField);
        registerDialog.add(new JLabel());
        registerDialog.add(registerButton);

        registerDialog.setLocationRelativeTo(null);
        registerDialog.setVisible(true);

        return user[0];
    }


    public User login() {
        JDialog loginDialog = new JDialog((Frame) null, "Log in to TIC TAC TOE", true);
        loginDialog.setLayout(new GridLayout(3, 2, 10, 10));
        loginDialog.setSize(300, 200);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("LOG IN");
        final User[] user = {null};

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            user[0] = new User(username, password);
            loginDialog.dispose(); // Close dialog
        });

        loginDialog.add(usernameLabel);
        loginDialog.add(usernameField);
        loginDialog.add(passwordLabel);
        loginDialog.add(passwordField);
        loginDialog.add(new JLabel());
        loginDialog.add(loginButton);

        loginDialog.setLocationRelativeTo(null);
        loginDialog.setVisible(true);

        return user[0];
    }


    public User reLogin(String info) {
        JDialog reLoginDialog = new JDialog((Frame) null, "Log in again", true);
        reLoginDialog.setLayout(new GridLayout(4, 2, 10, 10)); // Adjusted layout to accommodate the info label
        reLoginDialog.setSize(300, 200);

        JLabel informationLabel = new JLabel(info, SwingConstants.CENTER);
        informationLabel.setForeground(Color.RED); // Highlight the message in red for better visibility

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton reLoginButton = new JButton("LOG IN");
        final User[] user = {null};

        reLoginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            user[0] = new User(username, password);
            reLoginDialog.dispose(); // Close dialog after capturing input
        });

        reLoginDialog.add(informationLabel);
        reLoginDialog.add(new JLabel()); // Empty label to align properly
        reLoginDialog.add(usernameLabel);
        reLoginDialog.add(usernameField);
        reLoginDialog.add(passwordLabel);
        reLoginDialog.add(passwordField);
        reLoginDialog.add(new JLabel()); // Empty label to align the button
        reLoginDialog.add(reLoginButton);

        reLoginDialog.setLocationRelativeTo(null); // Center dialog on the screen
        reLoginDialog.setVisible(true);

        return user[0];
    }


    public int modeSelect() {
        JDialog modeSelectDialog = new JDialog((Frame) null, "Select Game Mode", true);
        modeSelectDialog.setLayout(new GridLayout(3, 1, 10, 10));
        modeSelectDialog.setSize(400, 200);

        JLabel modeLabel = new JLabel("Which mode do you want to play?", SwingConstants.CENTER);
        modeLabel.setFont(modeLabel.getFont().deriveFont(18.0f));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton singlePlayerButton = new JButton("1. Single Player");
        JButton multiplayerButton = new JButton("2. Multiplayer");
        JButton onlineButton = new JButton("3. Online");

        final int[] selectedMode = {0};

        // Add action listeners for each button
        singlePlayerButton.addActionListener(e -> {
            selectedMode[0] = 1;
            modeSelectDialog.dispose();
        });

        multiplayerButton.addActionListener(e -> {
            selectedMode[0] = 2;
            modeSelectDialog.dispose();
        });

        onlineButton.addActionListener(e -> {
            selectedMode[0] = 3;
            modeSelectDialog.dispose();
        });

        // Add components to dialog
        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(multiplayerButton);
        buttonPanel.add(onlineButton);

        modeSelectDialog.add(modeLabel);
        modeSelectDialog.add(buttonPanel);

        modeSelectDialog.setLocationRelativeTo(null); // Center dialog on the screen
        modeSelectDialog.setVisible(true);

        return selectedMode[0];
    }



    JFrame createFrame(String title, int width, int height, LayoutManager layout) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(layout);
        frame.setIconImage(logo);
        return frame;
    }
}
