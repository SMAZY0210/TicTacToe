package TicTacToe;

public class Player {
    String name;
    public char character;

    public String getName() {
        return name;
    }

    public Player () {}
    public Player(String name, char character) {
        this.name = name;
        if(character=='X'||character=='O') {
            this.character = character;
        }
    }

    public int[] move(Board board) {
        return new int[]{0,0};
    }
}
