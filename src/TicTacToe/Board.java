package TicTacToe;

public class Board {
    char[][] board = new char[3][3];

    public void draw(Player player, int x, int y) {

        if(x<4 && y<4 && checkBoard(x,y)) {
            board[x - 1][y - 1] = player.character;
        }
    }
    public boolean checkBoard(int x, int y) {
        return board[x - 1][y - 1] == ' ';
    }

    public boolean checkWin(Player currentPlayer) {
        // Check rows and columns.
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer.character &&
                    board[i][1] == currentPlayer.character &&
                    board[i][2] == currentPlayer.character) {
                return true;
            }
            if (board[0][i] == currentPlayer.character &&
                    board[1][i] == currentPlayer.character &&
                    board[2][i] == currentPlayer.character) {
                return true;
            }
        }

        // Check diagonals.
        if (board[0][0] == currentPlayer.character &&
                board[1][1] == currentPlayer.character &&
                board[2][2] == currentPlayer.character) {
            return true;
        }
        if (board[0][2] == currentPlayer.character &&
                board[1][1] == currentPlayer.character &&
                board[2][0] == currentPlayer.character) {
            return true;
        }

        return false;
    }
    public boolean isBoardFull() {
        for (int i = 0; i<3; i++) {
            for (int j = 0; j<3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    void clearBoard() {
        for(int i = 0;i < 3; i++) {
            for(int j = 0;j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }
    public Board() {
            for(int i = 0;i < 3; i++) {
                for(int j = 0;j < 3; j++) {
                    board[i][j] = ' ';
                }
            }
    }
//
//    public char[][] getBoard() {
//        return board;
//    }
}
