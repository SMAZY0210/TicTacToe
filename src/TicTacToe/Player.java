package TicTacToe;

import java.util.Scanner;

public class Player {
    String name;
    public char character;

    public String getName() {
        return name;
    }

    Scanner read = new Scanner(System.in);

    public void winStatement(Board board) {
        System.out.println();
        System.out.println();
        board.display();
        System.out.println(this.name + " Wins");
    }

    public Player () {}
    public Player(char character) {
        if(character=='X') this.name = "Player One";
        if(character=='O')  this.name = "Player Two";
        if(character=='X'||character=='O') {
            this.character = character;
        }
        else {
            System.out.println("Invalid Character....\nUse 'X' or 'O'");
        }
    }
    public Player(String name, char character) {
        this.name = name;
        if(character=='X'||character=='O') {
            this.character = character;
        }
    }

    public int[] move(Board board) {
        int[] arr = {0,0};
        return arr;
    }
}
