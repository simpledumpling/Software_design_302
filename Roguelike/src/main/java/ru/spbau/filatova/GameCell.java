package ru.spbau.filatova;

import asciiPanel.AsciiPanel;

import java.awt.*;

//Enum for a cell of the game field
public enum GameCell {
    FLOOR((char) 250, AsciiPanel.yellow),
    WALL((char) 177, AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack);

    private char glyph;
    private Color color;

    GameCell(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }

    public char glyph() {
        return glyph;
    }

    public Color color() {
        return color;
    }

    //Return true if it is a wall, so we can dig it
    public boolean isDiggable() {
        return this == GameCell.WALL;
    }

    //Return true if it is a FLOOR
    public boolean isGround() {
        return this != WALL && this != BOUNDS;
    }
}
