package ru.spbau.filatova;


import asciiPanel.AsciiPanel;

import java.awt.*;

public class AchievementsFactory {
    //Attribute for gameWorld
    private GameWorld gameWorld;

    public AchievementsFactory(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public Achievement newLemon() {
        return newFruit(AsciiPanel.brightYellow);
    }

    public Achievement newApple() {
        return newFruit(AsciiPanel.brightGreen);
    }

    public Achievement newTomato() {
        return newVegetable(AsciiPanel.brightRed);
    }

    public Achievement newBeet() {
        return newVegetable(AsciiPanel.brightMagenta);
    }

    //Create new achievement - fruit?
    private Achievement newFruit(Color color) {
        Achievement fruit = new Achievement((char) 3, color, 2);
        gameWorld.addAchievementAtEmptyLocation(fruit);
        return fruit;
    }

    //Vegetables is more valuable than fruits, so they give 4 achievements points
    private Achievement newVegetable(Color color) {
        Achievement vegetable = new Achievement((char) 3, color, 4);
        gameWorld.addAchievementAtEmptyLocation(vegetable);
        return vegetable;
    }

}
