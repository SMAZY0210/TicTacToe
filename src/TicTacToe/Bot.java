package TicTacToe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Bot extends Player {
    Random rand = new Random();

    // Pick a random move from the available spots
    private int[] selectSpot(Board board) {
        List<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.checkBoard(i + 1, j + 1)) {
                    availableMoves.add(new int[] {i, j});
                }
            }
        }
        return availableMoves.get(rand.nextInt(availableMoves.size()));
    }

    public int[] move(Board board) {
        return selectSpot(board);  // Move to an available spot
    }

    public Bot(char character) {
        if(character=='X') this.name = "Bot";
        if(character=='O')  this.name = "Bot";
        if(character=='X'||character=='O') {
            this.character = character;
        }
        else {
            System.out.println("Invalid Character....\nUse 'X' or 'O'");
        }
    }
}