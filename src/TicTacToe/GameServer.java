package TicTacToe;

import java.io.*;
import java.net.*;

public class GameServer {
    private static final int PORT = 8080;
    private static Socket player1Socket;
    private static Socket player2Socket;
    private static PrintWriter player1Out;
    private static PrintWriter player2Out;
    private static BufferedReader player1In;
    private static BufferedReader player2In;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for players...");

            // Wait for two players to connect
            player1Socket = serverSocket.accept();
            System.out.println("Player 1 connected.");
            player2Socket = serverSocket.accept();
            System.out.println("Player 2 connected.");

            player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
            player2Out = new PrintWriter(player2Socket.getOutputStream(), true);
            player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
            player2In = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));

            // Now both players are connected. Implement game loop logic.
            gameLoop();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gameLoop() {
        try {
            String move;
            while (true) {
                // Player 1 makes a move
                move = player1In.readLine();
                player2Out.println(move);  // Send the move to Player 2

                // Player 2 makes a move
                move = player2In.readLine();
                player1Out.println(move);  // Send the move to Player 1
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

