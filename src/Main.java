import TicTacToe.DatabaseConnection;
import TicTacToe.Game;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        db.dbconnect();
        Game game = new Game();
        game.start();
    }
}