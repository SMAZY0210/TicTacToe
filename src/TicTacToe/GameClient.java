package TicTacToe;

import java.io.*;
import java.net.*;

public class GameClient {
    private static final String SERVER_ADDRESS = "localhost";  // Change this to the server's IP address
    private static final int SERVER_PORT = 12345;

    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static BufferedReader userInput;

    public static void main(String[] args) {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to the server.");

            // Game loop logic here
            gameLoop();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gameLoop() {
        try {
            String move;
            while (true) {
                // Wait for the server to send the opponent's move
                move = in.readLine();
                System.out.println("Opponent's move: " + move);

                // Now ask the user for their move
                System.out.print("Your move (row,col): ");
                move = userInput.readLine();
                out.println(move);  // Send the move to the server
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
