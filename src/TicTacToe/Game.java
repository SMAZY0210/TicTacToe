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

    private TTTGUI gui = new TTTGUI();

    public Game() {
        startServer();
        addUser(new User("Abrar", "1234"));

    }
    public void start() {
        int val = gui.displayWelcomeScreen();


        if(val==1) {
            boolean verifiedUser = checkUser(gui.login());
            while(!verifiedUser) {
                verifiedUser = checkUser(gui.reLogin("Wrong Username or Password given, Try again!!"));
            }
        } else if(val==2) {
            User user = gui.registration();
            addUser(user);
            start();
        }
    }


    private boolean checkUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (usernameExists(username) && validPass(username, password)) {
            player1 = new Player(username, 'X');
            currentPlayer = player1;
            int mode = gui.modeSelect();
            if(mode == 1) {
                isBot = true;
                player2 = new Bot('O');
                startGame();
            } else if (mode == 2) {
                isBot = false;
                multiLogin();
            } else if (mode == 3) {
                startClient();
            }
            return true;
        } else {
           return false;
        }
    }

    private void multiLogin() {
        int verifiedUser = checkUser2(gui.login());
        while (verifiedUser!=1) {
            if(verifiedUser==2) {
                verifiedUser = checkUser2(gui.reLogin("The same user cannot play both sides."));
            } else if(verifiedUser==3) {
                verifiedUser = checkUser2(gui.reLogin("The same user cannot play both sides."));
            }
        }
        startGame();
    }

    private int checkUser2(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (usernameExists(username) && validPass(username, password)) {
            if (!Objects.equals(player1.getName(), username)) {
                player2 = new Player(username, 'O');
                return 1;
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }





    private void startGame() {
        frame = gui.createFrame("Tic Tac Toe", 500, 400, new GridLayout(3, 3));
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
