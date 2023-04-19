package com.sc.game.server;

import com.sc.game.repository.ResultsRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server extends Thread {
        // Vector to store clients
    private final Vector<ClientHandler> Clients = new Vector<>();
    // counter for clients
    private int clientsCounter = 0;
    private final int maxUsersCount;
    private final int maxGameTime;
    private final ServerSocket serverSocket;
    private GameSession gameSession;
    private ResultsRepository repository;

    public Server(int port, int maxUsersCount, int maxGameTime) throws IOException {
        serverSocket = new ServerSocket(port);
        this.maxUsersCount = maxUsersCount;
        this.maxGameTime = maxGameTime;
    }

    public Vector<ClientHandler> getClients() {
        return Clients;
    }
    public GameSession getGameSession() {
        return gameSession;
    }
    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }
    public int getMaxUsersCount() {
        return maxUsersCount;
    }
    public int getMaxGameTime() {
        return maxGameTime;
    }
    public ResultsRepository getRepository() {
        return repository;
    }

    public void close(){
        try {
            this.repository.close();
            this.interrupt();
            serverSocket.close();
        } catch (IOException ignored) {
        }

    }

    @Override
    public void run() {
        repository = new ResultsRepository();
        repository.connect();

        try {
            while (!Thread.interrupted()) {
                try {
                    Socket socket = serverSocket.accept();
                    // Create a new handler object for handling this request.
                    var clientHandler = new ClientHandler(socket, ++clientsCounter, this);
                    if (Clients.size() + 1 > maxUsersCount) {
                        clientHandler.cancelConnection();
                        continue;
                    }
                    clientHandler.requestNameOfUser();

                    // Start a new handler.
                    clientHandler.start();
                    Clients.add(clientHandler);
                    System.out.println("Created a new handler for client " + clientsCounter);

                } catch (IOException ex) {
                }
            }
            // Close the server once done.
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
            for (var clientHandler :Clients) {
                clientHandler.interrupt();
                clientHandler.close();
            }
        } catch (IOException ex) {
            System.out.println("Close server socket error");
        }
    }
}

