package com.detyrat_ai.detyra3;

public class Move {
    public int fromRow, fromCol, toRow, toCol;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    @Override
    public String toString() {
        return "Move from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")";
    }
}