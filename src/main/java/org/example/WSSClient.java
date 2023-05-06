package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class WSSClient extends WebSocketClient {
    private int roomNumber;
    ObjectMapper objectMapper = new ObjectMapper();
    private final Scanner scanner = new Scanner(System.in);
    private volatile boolean opponentLeft = false;


    public WSSClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to the server");
    }

    @Override
    public void onMessage(String message) {
        try {
            GameInfo gameInfo = objectMapper.readValue(message, GameInfo.class);
            afterMessageAction(gameInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed, code: " + code + ", reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Error occurred: " + ex.getMessage());
        ex.printStackTrace();
    }

    public void sendMessage(String message) {
        if (this.isOpen()) {
            this.send(message);
        } else {
            System.out.println("WebSocket connection is not open.");
        }
    }

    public void joinRoom() {
        System.out.print("Enter room number: ");
        Scanner scanner = new Scanner(System.in);
        int roomNumber = scanner.nextInt();
        this.sendMessage("joinRoom:" + roomNumber);
    }

    private void drawBoard(Position[][] positions, List<String> additionalInfo){
        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.println();
        System.out.println("   A\u2003B\u2003C\u2003D\u2003E\u2003F\u2003G\u2003H");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + "  ");
            for (int j = 0; j < 8; j++) {
                if (positions[i][j].getChessman() == null) {
                    System.out.print("\u2003 ");
                } else {
                    System.out.print(positions[i][j].getChessman().getSymbol() + " ");
                }
            }
            System.out.printf(" |   ");
            if (additionalInfo !=null && i < additionalInfo.size()) {
                System.out.print(additionalInfo.get(i));
            }
            System.out.println();
        }
    }

    private String selectChessman(){
        System.out.print("Select chessman to move, for example 'A2': ");
        String positionStartCoords = scanner.nextLine();
        return positionStartCoords;
    }

    private String selectPawnToUpgrade(){
        System.out.print("Choose chessman to upgrade: (Queen | Rook | Bishop | Knight): ");
        return scanner.nextLine();
    }

    private String selectPositionToMove(){
        System.out.print("Select position to move, for example 'A2': ");
        String positionEndCoords = scanner.nextLine();
        return positionEndCoords;
    }

    private void afterMessageAction(GameInfo gameInfo){
        if(gameInfo.isWrongSelection() && gameInfo.isYourTurn()){
            gameInfo.getGameInfo().add("Wrong selection, try again");
            drawBoard(gameInfo.getPositions(), gameInfo.getGameInfo());
            String selectedChessman = startUserInteractionThread(this::selectChessman);
            sendSelectedChessman(selectedChessman);
        }else if(gameInfo.isOpponentLeft()){
            gameInfo.getGameInfo().add("Opponent left");
            gameInfo.getGameInfo().add("Congratulations, you won!");
            drawBoard(gameInfo.getPositions(), gameInfo.getGameInfo());
        } else if(gameInfo.isUpgradePawn()){
            gameInfo.getGameInfo().add("Upgrade pawn");
            drawBoard(gameInfo.getPositions(), gameInfo.getGameInfo());
            String upgradePawn = startUserInteractionThread(this::selectPawnToUpgrade);
            sendSelectedUpgrade(upgradePawn);
        }
        else if(gameInfo.isWrongMove()){
            gameInfo.getGameInfo().add("Wrong move, try again");
            drawBoard(gameInfo.getPositions(), gameInfo.getGameInfo());
            String selectedChessman = startUserInteractionThread(this::selectChessman);
            sendSelectedChessman(selectedChessman);
        }
        else if(gameInfo.isYourTurn() && !gameInfo.isSelectingPositionToMove()){
            drawBoard(gameInfo.getPositions(), gameInfo.getGameInfo());
            String selectedPositionToMove = startUserInteractionThread(this::selectPositionToMove);
            sendSelectedPositionToMove(selectedPositionToMove);
        } else if(gameInfo.isYourTurn() && gameInfo.isSelectingPositionToMove()){
            gameInfo.getGameInfo().add("Your turn");
            drawBoard(gameInfo.getPositions(), gameInfo.getGameInfo());
            String selectedChessman = startUserInteractionThread(this::selectChessman);
            sendSelectedChessman(selectedChessman);
        } else if(!gameInfo.isGameRunning()){
            gameInfo.getGameInfo().add("Waiting for opponent");
            drawBoard(gameInfo.getPositions(), gameInfo.getGameInfo());
        }  else {
            gameInfo.getGameInfo().add("Waiting for opponent move");
            drawBoard(gameInfo.getPositions(), gameInfo.getGameInfo());
        }
    }


    private void sendSelectedChessman(String selectedChessman){
        this.send("selectedChessman:"+selectedChessman);
    }
    private void sendSelectedUpgrade(String selectedChessman){
        this.send("upgradePawn:"+selectedChessman);
    }

    private void sendSelectedPositionToMove(String selectedPositionToMove){
        this.send("selectedPositionToMove:"+selectedPositionToMove);
    }


    private String startUserInteractionThread(Supplier<String> userAction) {
        final String[] result = new String[1];
        Thread userInteractionThread = new Thread(() -> {
            while (!opponentLeft) {
                result[0] = userAction.get();
                if (result[0] != null) {
                    break;
                }
                try {
                    Thread.sleep(100); // Czekaj 100ms przed kolejnym sprawdzeniem
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        userInteractionThread.start();
        try {
            userInteractionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result[0];
    }


}
