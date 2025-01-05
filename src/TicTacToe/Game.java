package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static TicTacToe.DatabaseConnection.*;

public class Game {
    private final Board board = new Board();
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private JFrame frame;
    private JButton[][] buttons;
    private boolean isBot;
    private final Image logo;

    public Game() {
        startServer();
        this.logo = new ImageIcon("E:\\Java\\Programs\\TicTacToe\\src\\logo.png").getImage();
        addUser(new User("Abrar", "1234"));
        player1 = new Player("PLAYER", 'X');
        displayWelcomeScreen();
    }

    private void displayWelcomeScreen() {
        JFrame welcomeFrame = createFrame("TicTacToe", 550, 500, new GridLayout(2, 1));

        JLabel welcomeLabel = new JLabel("Welcome to Tic Tac Toe!", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(18.0f));
        welcomeFrame.add(welcomeLabel);

        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("LOG IN");
        JButton registerButton = new JButton("REGISTER");

        loginButton.addActionListener(e -> login(welcomeFrame));
        registerButton.addActionListener(e -> registration());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        welcomeFrame.add(buttonPanel);

        welcomeFrame.setVisible(true);
    }

    private JFrame createFrame(String title, int width, int height, LayoutManager layout) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(layout);
        frame.setIconImage(logo);
        return frame;
    }

    private void registration() {
        JFrame registerWindow = createFrame("Register to TIC TAC TOE", 300, 200, new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            addUser(new User(username, password));

            JOptionPane.showMessageDialog(registerWindow, "User registration successful for the user " + username);
            registerWindow.dispose();
        });

        registerWindow.add(usernameLabel);
        registerWindow.add(usernameField);
        registerWindow.add(passwordLabel);
        registerWindow.add(passwordField);
        registerWindow.add(new JLabel());
        registerWindow.add(registerButton);

        registerWindow.setVisible(true);
    }

    private void login(JFrame previousFrame) {
        JFrame loginWindow = createFrame("Log in to TIC TAC TOE", 300, 200, new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("LOG IN");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (usernameExists(username) && validPass(username, password)) {
                player1 = new Player(username, 'X');
                currentPlayer = player1;
                loginWindow.dispose();
                previousFrame.dispose();
                modeSelect();
            } else {
                JOptionPane.showMessageDialog(loginWindow, "Invalid username or password.");
            }
        });

        loginWindow.add(usernameLabel);
        loginWindow.add(usernameField);
        loginWindow.add(passwordLabel);
        loginWindow.add(passwordField);
        loginWindow.add(new JLabel());
        loginWindow.add(loginButton);

        loginWindow.setVisible(true);
    }

    private void modeSelect() {
        JFrame modeSelectFrame = createFrame("TicTacToe", 550, 500, new GridLayout(2, 1));

        JLabel modeLabel = new JLabel("Which mode do you want to play?", SwingConstants.CENTER);
        modeLabel.setFont(modeLabel.getFont().deriveFont(18.0f));
        modeSelectFrame.add(modeLabel);

        JPanel modePanel = new JPanel();
        JButton singlePlayerButton = new JButton("1. Single player");
        JButton multiplayerButton = new JButton("2. Multiplayer");
        JButton onlineButton = new JButton("3. Online");

        singlePlayerButton.addActionListener(e -> {
            modeSelectFrame.dispose();
            isBot = true;
            player2 = new Bot('O');
            startGame();
        });

        multiplayerButton.addActionListener(e -> {
            modeSelectFrame.dispose();
            isBot = false;
            multiplayerLogin();
        });

        onlineButton.addActionListener(e -> {
            modeSelectFrame.dispose();
            startClient();
        });

        modePanel.add(singlePlayerButton);
        modePanel.add(multiplayerButton);
        modePanel.add(onlineButton);
        modeSelectFrame.add(modePanel);

        modeSelectFrame.setVisible(true);
    }

    private void multiplayerLogin() {
        JFrame loginWindow = createFrame("Player 2 Login", 300, 200, new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("LOG IN");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (usernameExists(username) && validPass(username, password)) {
                if (!Objects.equals(player1.getName(), username)) {
                    player2 = new Player(username, 'O');
                    loginWindow.dispose();
                    startGame();
                } else {
                    JOptionPane.showMessageDialog(loginWindow, "The same user cannot play both sides.");
                }
            } else {
                JOptionPane.showMessageDialog(loginWindow, "Invalid username or password.");
            }
        });

        loginWindow.add(usernameLabel);
        loginWindow.add(usernameField);
        loginWindow.add(passwordLabel);
        loginWindow.add(passwordField);
        loginWindow.add(new JLabel());
        loginWindow.add(loginButton);

        loginWindow.setVisible(true);
    }

    private void startGame() {
        frame = createFrame("Tic Tac Toe", 500, 400, new GridLayout(3, 3));
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

        buttons[row][col].setText(String.valueOf(currentPlayer.getCharacter()));
        board.draw(currentPlayer, row + 1, col + 1);

        if (board.checkWin(currentPlayer)) {
            endGame(currentPlayer.getName() + " wins!");
        } else if (board.isBoardFull()) {
            endGame("It's a draw!");
        } else {
            switchPlayer();
            if (isBot && currentPlayer == player2) {
                botMove();
            }
        }
    }
    private void startServer() {
        new Thread(() -> {
            try {
                GameServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void startClient() {
        new Thread(() -> {
            try {
                GameClient.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void botMove() {
        int[] move = player2.move(board);
        handleButtonClick(move[0], move[1]);
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void endGame(String message) {
        JOptionPane.showMessageDialog(frame, message);
        updatePlayerStats();
        resetBoard();
    }

    private void updatePlayerStats() {
        incrementMatches(player1.getName());
        incrementMatches(player2.getName());
        if (board.checkWin(player1)) {
            incrementWin(player1.getName());
            incrementLoss(player2.getName());
        } else if (board.checkWin(player2)) {
            incrementWin(player2.getName());
            incrementLoss(player1.getName());
        } else {
            incrementDraws(player1.getName());
            incrementDraws(player2.getName());
        }
    }

    private void resetBoard() {
        board.clearBoard();
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setText("");
            }
        }
        currentPlayer = player1;
    }
}
