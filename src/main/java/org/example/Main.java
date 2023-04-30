package org.example;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        WSSClient client = new WSSClient(new URI("ws://localhost:8887"));
        client.connect();
        while(!client.isOpen()) {
            System.out.println("Connecting...");
            sleep(100);
        }
        client.joinRoom();
    }
}
