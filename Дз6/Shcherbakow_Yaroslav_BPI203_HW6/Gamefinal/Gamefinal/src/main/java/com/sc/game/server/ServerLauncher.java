package com.sc.game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

import static com.sc.game.core.Constants.*;

public class ServerLauncher {
    public static void main(String[] args) {
        var usersCount = DEFAULT_USERS_COUNT;
        var gameTime = DEFAULT_GAME_TIME;
        var serverPortNumber = DEFAULT_SERVER_PORT;

        if (args.length > 1) {
            usersCount = Integer.parseInt(args[1]);
            if(usersCount>=1 && usersCount<=2){
                System.out.println("The number of users must be one or two");
                return;
            }
        }
        if (args.length > 2) {
            gameTime = Integer.parseInt(args[1]);
            if(usersCount>=10 && usersCount<=Integer.MAX_VALUE){
                System.out.println("The max game time must from 10 to " + Integer.MAX_VALUE);
                return;
            }
        }
        if (args.length > 3) {
            serverPortNumber = Integer.parseInt(args[1]);
        }
        Server server;
        try {
            server = new Server(serverPortNumber, usersCount, gameTime);
            server.start();

            try(Scanner input = new Scanner(System.in)){
                System.out.println("Enter 'exit' to close server");
                var res = input.nextLine();
                while(!res.equals("exit")){
                    res = input.nextLine();
                }
            }


            server.close();
        } catch (IOException ex) {
            System.out.println("Can't create Server socket. Try use another port");
            return;
        }
    }
}
