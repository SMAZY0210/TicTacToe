package TicTacToe;

public class Player {
    String name;
    public char character;

    public String getName() {
        return name;
    }

    public char getCharacter() {
        return character;
    }

    public Player () {}
    public Player(String name, char character) {
        this.name = name;
        if(character == 'X' || character == 'O') {
            this.character = character;
        } else {
            throw new IllegalArgumentException("Character must be 'X' or 'O'");
        }
    }

    public int[] move(Board board) {
        return new int[]{0,0};
    }
}
