package org.example;

import org.java_websocket.WebSocket;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private int roomNumber;
    private Set<WebSocket> clients;
    private String whitePlayer;
    private String blackPlayer;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.clients = new HashSet<>();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Set<WebSocket> getClients() {
        return clients;
    }

    public void addClient(WebSocket client) {
        clients.add(client);
    }

    public void removeClient(WebSocket client) {
        clients.remove(client);
    }

    public String getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(String whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public String getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(String blackPlayer) {
        this.blackPlayer = blackPlayer;
    }
}
