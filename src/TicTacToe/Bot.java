package TicTacToe;

import java.util.Random;
public class Bot extends Player {

    Random rand = new Random();
    private int[] selectSpot() {
        int x = Math.abs(rand.nextInt(3));
        int y = Math.abs(rand.nextInt(3));
        int[] res = {x,y};
        return res;
    }
    public int[] move(Board board) {
        int res[] = selectSpot();
        while(!board.checkBoard(res[0]+1,res[1]+1)) {
            res = selectSpot();
        }
        return res;
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
