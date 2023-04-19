package com.sc.game.server;

public class ClientInformation {
    private final int id;
    private String name;
    private boolean isReady;

    public ClientInformation(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getState() {
        return isReady;
    }

    public void setState(boolean isReady) {
        this.isReady = isReady;
    }
}