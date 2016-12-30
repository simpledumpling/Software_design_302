package ru.spbau.filatova;

import java.awt.*;

public class Achievement {
    //Achievement's position in two dimensions x and y on our map
    private int x;
    private int y;

    //Parameters of the achievement's visualization
    private char glyph;
    private Color color;

    //How much will get player if he get this achievement
    private int achievementPoints;

    public Achievement(char glyph, Color color, int achievementPoints) {
        this.glyph = glyph;
        this.color = color;
        this.achievementPoints = achievementPoints;
    }

    //Setters for map coordinates of the achievement
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //Getters for map coordinates of the achievement
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    //Getters for the other parameters
    public Color getColor() {
        return this.color;
    }

    public char getGlyph(){
        return this.glyph;
    }

    public int getAchievementPoints() {
        return this.achievementPoints;
    }

}
