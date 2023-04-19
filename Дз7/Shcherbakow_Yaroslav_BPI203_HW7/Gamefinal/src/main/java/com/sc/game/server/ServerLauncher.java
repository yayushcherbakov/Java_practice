package com.sc.game.server;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.sc.game.core.Constants.*;

public class ServerLauncher {
    public static void main(String[] args) {
        var usersCount = DEFAULT_USERS_COUNT;
        var gameTime = DEFAULT_GAME_TIME;
        var serverPortNumber = DEFAULT_SERVER_PORT;

        if (args.length > 0) {
            usersCount = Integer.parseInt(args[0]);
            if (usersCount < 1 || usersCount > 2) {
                System.out.println("The number of users must be one or two");
                return;
            }
        }

        if (args.length > 1) {
            gameTime = Integer.parseInt(args[1]);
            if (gameTime < 10) {
                System.out.println("The max game time must from 10 to " + Integer.MAX_VALUE);
                return;
            }
        }

        if (args.length > 2) {
            serverPortNumber = Integer.parseInt(args[2]);
        }
        Server server;
        try {
            server = new Server(serverPortNumber, usersCount, gameTime);
            server.start();

            try (Scanner input = new Scanner(System.in)) {
                System.out.println("Enter 'exit' to close server");
                while (input.hasNextLine()) {
                    var res = input.nextLine();
                    if (res.equals("exit")) {
                        break;
                    }
                }
            } catch (NoSuchElementException ex) {
            }

            server.close();
        } catch (IOException ex) {
            System.out.println("Can't create Server socket. Try use another port");
        }
    }
}
