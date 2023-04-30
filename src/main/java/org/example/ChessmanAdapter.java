package org.example;

public class ChessmanAdapter {
    private String symbol;
    private boolean white;
    private String name;

    public ChessmanAdapter(String symbol, boolean white, String name) {
        this.symbol = symbol;
        this.white = white;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChessmanAdapter() {
    }
}
