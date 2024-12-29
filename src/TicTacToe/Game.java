package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

import static TicTacToe.DatabaseConnection.*;

public class Game {
    Board board = new Board();
    Player player1;
    Player player2;
    Player currentPlayer;
    JFrame frame;
    JButton[][] buttons;
    boolean isBot;
    ImageIcon icon = new ImageIcon("E:\\Java\\Programs\\TicTacToe\\src\\logo.png");
    Image logo = icon.getImage();

    private void registration() {
        JFrame registerWindow = new JFrame("Register to TIC TAC TOE");
        registerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerWindow.setSize(300, 200);
        registerWindow.setLayout(new GridLayout(3, 2, 10, 10));
        registerWindow.setIconImage(logo);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton registerButton = new JButton("Register");

        // Add components to the frame
        registerWindow.add(usernameLabel);
        registerWindow.add(usernameField);
        registerWindow.add(passwordLabel);
        registerWindow.add(passwordField);
        registerWindow.add(new JLabel());
        registerWindow.add(registerButton);
        registerButton.addActionListener(e -> {
            // Retrieve user input
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            User newUser = new User(username, password);
            addUser(newUser);
            // Display success message
            JOptionPane.showMessageDialog(registerWindow, "User registration successful for the user " + newUser.getUsername());
            registerWindow.dispose();
        });

        // Make the JFrame visible
        registerWindow.setVisible(true);
    }

    private void login(JFrame frame) {
        JFrame loginWindow = new JFrame("LOG IN to TIC TAC TOE");
        loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginWindow.setSize(300, 200);
        loginWindow.setLayout(new GridLayout(3, 2, 10, 10));
        loginWindow.setIconImage(logo);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("LOG IN");

        loginWindow.add(usernameLabel);
        loginWindow.add(usernameField);
        loginWindow.add(passwordLabel);
        loginWindow.add(passwordField);
        loginWindow.add(new JLabel()); // Empty cell to align the button
        loginWindow.add(loginButton);
        loginButton.addActionListener(e -> {
            // Retrieve user input
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars); // Convert char[] to String
            boolean userExists = usernameExists(username);
            boolean correctPass = false;
            if(userExists) {
                correctPass = validPass(username, password);
                if (correctPass) {
                    player1 = new Player(username, 'X');
                    currentPlayer = player1;

                    loginWindow.dispose();
                    frame.dispose();
                    modeSelect();
                } else {
                    JOptionPane.showMessageDialog(loginWindow, "Log In Unsuccessful.");
                    loginWindow.dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(loginWindow, "User not found");

            }
            loginWindow.dispose();
        });

        // Make the JFrame visible
        loginWindow.setVisible(true);
    }

    private void p2login() {
        JFrame loginWindow = new JFrame("LOG IN to TIC TAC TOE");
        loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginWindow.setSize(300, 200);
        loginWindow.setLayout(new GridLayout(3, 2, 10, 10));
        loginWindow.setIconImage(logo);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("LOG IN");

        loginWindow.add(usernameLabel);
        loginWindow.add(usernameField);
        loginWindow.add(passwordLabel);
        loginWindow.add(passwordField);
        loginWindow.add(new JLabel()); // Empty cell to align the button
        loginWindow.add(loginButton);
        loginButton.addActionListener(e -> {
            // Retrieve user input
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars); // Convert char[] to String
            boolean userExists = usernameExists(username);
            boolean correctPass = false;
            if(userExists) {
                correctPass = validPass(username, password);
                if (correctPass) {
                    if(!Objects.equals(player1.getName(), username)) {
                        player2 = new Player(username, 'O');
                        loginWindow.dispose();
                        tictactoe();
                    }
                    else {
                        JOptionPane.showMessageDialog(loginWindow, "Same user cannot play both sides");
                        loginWindow.dispose();
                        modeSelect();
                    }

                } else {
                    JOptionPane.showMessageDialog(loginWindow, "Log In Unsuccessful.");
                    loginWindow.dispose();
                    modeSelect();
                }
            }
            else {
                JOptionPane.showMessageDialog(loginWindow, "User not found");
                modeSelect();
            }
            loginWindow.dispose();
        });

        // Make the JFrame visible
        loginWindow.setVisible(true);
    }

    private void modeSelect() {
        JFrame modeSelect = new JFrame("TicTacToe");
        modeSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        modeSelect.setSize(550, 500);
        modeSelect.setLayout(new GridLayout(2,1));
        modeSelect.setIconImage(logo);

        JLabel label1 = new JLabel("Which mode do you want to play on?", SwingConstants.CENTER);
        label1.setFont(label1.getFont().deriveFont(18.0f));
        modeSelect.add(label1);
        JPanel panel = new JPanel();

        JButton single = new JButton("1. Single player");
        JButton multiplayer  = new JButton("2. Multiplayer player");
        JButton online  = new JButton("3. Online");

        single.addActionListener((ActionEvent e) -> {
            modeSelect.dispose();
            isBot = true;
            this.player2 = new Bot('O');
            tictactoe();
        });
        multiplayer.addActionListener((ActionEvent e) -> {
            modeSelect.dispose();
            p2login();
            isBot = false;
        });
        online.addActionListener((ActionEvent e) -> {
            modeSelect.dispose();
        });
        panel.add(single);
        panel.add(multiplayer);
        panel.add(online);

        modeSelect.add(panel);

        modeSelect.setVisible(true);
    }

    private void tictactoe() {
        frame = new JFrame("Tic Tac Toe");
        currentPlayer = player1;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(3, 3));
        frame.setIconImage(logo);
        buttons = new JButton[3][3];

        initializeBoard();

        frame.setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[row][col].setFocusPainted(false);

                int finalRow = row;
                int finalCol = col;
                buttons[row][col].addActionListener(e -> handleButtonClick(finalRow, finalCol));

                frame.add(buttons[row][col]);
            }
        }
    }

    private void handleButtonClick(int row, int col) {
        if (!buttons[row][col].getText().isEmpty()) {
            return;
        }

        buttons[row][col].setText(String.valueOf(currentPlayer.character));
        board.draw(currentPlayer, row+1, col+1);

        if (board.checkWin(currentPlayer)) {
            JOptionPane.showMessageDialog(frame, "Player " + currentPlayer.getName() + " wins!");
            incrementMatches(player1.getName());
            incrementMatches(player2.getName());
            incrementWin(currentPlayer.getName());
            incrementLoss((currentPlayer == player2)? player1.getName():player2.getName());
            resetBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(frame, "It's a draw!");
            incrementMatches(player1.getName());
            incrementMatches(player2.getName());
            incrementDraws(currentPlayer.getName());
            incrementDraws((currentPlayer == player2)? player1.getName():player2.getName());
            resetBoard();
        } else {
            if(currentPlayer == player1) {
                currentPlayer = player2;
                if(isBot) {
                    int[] arr = player2.move(board);
                    row = arr[0];
                    col = arr[1];
                    buttons[row][col].setText(String.valueOf(player2.character));
                    board.draw(currentPlayer, row+1, col+1);
                    currentPlayer = player1;
                }
            } else {
                currentPlayer = player1;
            }
        }
    }



    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        board.clearBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        currentPlayer = player1;
    }

    public Game() {
        User newUser = new User("Abrar", "1234");
        addUser(newUser);
        player1 = new Player("PLAYER", 'X');

        JFrame loginWindow = new JFrame("TicTacToe");
        loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginWindow.setSize(550, 500);
        loginWindow.setLayout(new GridLayout(2, 1));
        loginWindow.setIconImage(logo);

        JLabel label1 = new JLabel("Welcome to Tic Tac Toe!", SwingConstants.CENTER);
        label1.setFont(label1.getFont().deriveFont(18.0f));
        loginWindow.add(label1);

        JPanel panel = new JPanel();
        JButton login = new JButton("LOG IN");
        JButton register = new JButton("REGISTER");

        register.addActionListener(e -> registration());
        login.addActionListener(e -> login(loginWindow));

        panel.add(login);
        panel.add(register);
        loginWindow.add(panel);

        loginWindow.setVisible(true);
    }
}
