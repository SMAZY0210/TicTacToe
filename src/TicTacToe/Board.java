package TicTacToe;

public class Board {
    char[][] board = new char[3][3];

    public void display() {
        for(int i = 0;i < 3; i++) {
            System.out.println("---------");
            for(int y = 0;y < 3; y++) {
                System.out.print(board[i][y] + "   ");
            }
            System.out.println();
        }
        System.out.println("---------");
    }

    public boolean draw(Player player, int x, int y) {

        if(x<4 && y<4 && checkBoard(x,y)) {
            board[x - 1][y - 1] = player.character;
            return false;
        }
        else {
            System.out.println("invalid location");
            return true;
        }
    }

    public boolean checkBoard(int x, int y) {
        return board[x - 1][y - 1] == ' ';
    }

    public char checkWin() {
        char val = board[0][0];
        if(board[0][1]==board[0][2] && board[0][1]==val) return val;
        if(board[1][0]==board[2][0] && board[1][0]==val) return val;

        val = board[1][1];
        if(board[0][1]==board[2][1] && board[0][1]==val) return val;
        if(board[1][0]==board[1][2] && board[1][0]==val) return val;
        if(board[0][0]==board[2][2] && board[0][0]==val) return val;
        if(board[0][2]==board[2][0] && board[0][2]==val) return val;

        val = board[2][2];
        if(board[2][1]==board[2][0] && board[2][1]==val) return val;
        if(board[1][2]==board[0][2] && board[1][2]==val) return val;

        return 0;
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

    public char[][] getBoard() {
        return board;
    }
}
