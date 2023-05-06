package org.example;

import java.util.Arrays;
import java.util.List;

public class GameInfo {
    private Position[][] positions;
    private List<String> gameInfo;
    private boolean isYourTurn;
    private boolean isEnd = false;
    private boolean wrongSelection = false;
    private boolean wrongMove = false;
    private boolean gameRunning = false;
    private boolean selectingPositionToMove = false;

    private boolean upgradePawn = false;

    private boolean opponentLeft = false;


    public GameInfo(Position[][] positions, List<String> gameInfo, boolean isYourTurn, boolean isEnd) {
        this.positions = positions;
        this.gameInfo = gameInfo;
        this.isYourTurn = isYourTurn;
        this.isEnd = isEnd;
    }

    public GameInfo(Position[][] positions, List<String> gameInfo, boolean isYourTurn, boolean isEnd, boolean wrongSelection, boolean wrongMove, boolean gameRunning) {
        this.positions = positions;
        this.gameInfo = gameInfo;
        this.isYourTurn = isYourTurn;
        this.isEnd = isEnd;
        this.wrongSelection = wrongSelection;
        this.wrongMove = wrongMove;
        this.gameRunning = gameRunning;
    }

    public GameInfo() {
    }

    public Position[][] getPositions() {
        return positions;
    }

    public void setPositions(Position[][] positions) {
        this.positions = positions;
    }

    public List<String> getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(List<String> gameInfo) {
        this.gameInfo = gameInfo;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "positions=" + Arrays.toString(positions) +
                ", gameInfo=" + gameInfo +
                ", isYourTurn=" + isYourTurn +
                ", isEnd=" + isEnd +
                '}';
    }

    public boolean isWrongSelection() {
        return wrongSelection;
    }

    public void setWrongSelection(boolean wrongSelection) {
        this.wrongSelection = wrongSelection;
    }

    public boolean isWrongMove() {
        return wrongMove;
    }

    public void setWrongMove(boolean wrongMove) {
        this.wrongMove = wrongMove;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public boolean isSelectingPositionToMove() {
        return selectingPositionToMove;
    }

    public void setSelectingPositionToMove(boolean selectingPositionToMove) {
        this.selectingPositionToMove = selectingPositionToMove;
    }

    public boolean isOpponentLeft() {
        return opponentLeft;
    }

    public boolean isUpgradePawn() {
        return upgradePawn;
    }

    public void setUpgradePawn(boolean upgradePawn) {
        this.upgradePawn = upgradePawn;
    }

    public void setOpponentLeft(boolean opponentLeft) {
        this.opponentLeft = opponentLeft;
    }
}
