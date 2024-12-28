package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class Game {
    Board board = new Board();
    Player player1;
    Player player2;
    Player currentPlayer;
    JFrame frame;
    JButton[][] buttons;
    boolean isBot;
    User [] Users = new User[2];
    int user = 0;
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
        registerWindow.add(new JLabel()); // Empty cell to align the button
        registerWindow.add(registerButton);
        registerButton.addActionListener(e -> {
            // Retrieve user input
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars); // Convert char[] to String

            // Create a new User object with entered credentials
            User newUser = new User(username, password);
            Users[user++] = newUser;
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
            int i;
            boolean found = false;
            for(i = 0; i<user; i++) {
                if(Objects.equals(Users[i].getUsername(), username)) {
                    found = true;
                    if(Objects.equals(Users[i].getPassword(), password)) {
                        JOptionPane.showMessageDialog(loginWindow, "Log In successful for the user " + username);
                        loginWindow.dispose();
                        frame.dispose();
                        modeSelect();
                    } else {
                        JOptionPane.showMessageDialog(loginWindow, "Log In Unsuccessful.");
                        loginWindow.dispose();
                    }
                }
            }
            if(!found) {
                JOptionPane.showMessageDialog(loginWindow, "User not found");

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
            isBot = false;
            this.player2 = new Player('O');
            tictactoe();
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

        if (checkWin()) {
            JOptionPane.showMessageDialog(frame, "Player " + currentPlayer.getName() + " wins!");
            resetBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(frame, "It's a draw!");
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

    private boolean checkWin() {
        // Check rows and columns.
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer.character)) &&
                    buttons[i][1].getText().equals(String.valueOf(currentPlayer.character)) &&
                    buttons[i][2].getText().equals(String.valueOf(currentPlayer.character))) {
                return true;
            }
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer.character)) &&
                    buttons[1][i].getText().equals(String.valueOf(currentPlayer.character)) &&
                    buttons[2][i].getText().equals(String.valueOf(currentPlayer.character))) {
                return true;
            }
        }

        // Check diagonals.
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer.character)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer.character)) &&
                buttons[2][2].getText().equals(String.valueOf(currentPlayer.character))) {
            return true;
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer.character)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer.character)) &&
                buttons[2][0].getText().equals(String.valueOf(currentPlayer.character))) {
            return true;
        }

        return false;
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
        Users[user++] = newUser;
        player1 = new Player('X');
        currentPlayer = player1;

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