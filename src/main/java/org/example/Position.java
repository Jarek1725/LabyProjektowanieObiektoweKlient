package org.example;


public class Position {
    private int x;
    private int y;
    private ChessmanAdapter chessman;

    public Position(int x, int y, ChessmanAdapter chessman) {
        this.x = x;
        this.y = y;
        this.chessman = chessman;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ChessmanAdapter getChessman() {
        return chessman;
    }

    public void setChessman(ChessmanAdapter chessman) {
        this.chessman = chessman;
    }

    public Position() {
    }
}
