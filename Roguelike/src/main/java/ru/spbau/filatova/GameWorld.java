package ru.spbau.filatova;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameWorld {
    //World is a number of cells
    private GameCell[][] gameCells;

    //Parameters of the world
    private int width;
    private int height;

    //List for all creatures in our world
    private List<Creature> creatures;

    //List for all achievements in our world
    private List<Achievement> achievements;

    public GameWorld(GameCell[][] gameCells) {
        this.gameCells = gameCells;
        this.width = gameCells.length;
        this.height = gameCells[0].length;
        this.creatures = new ArrayList<Creature>();
        this.achievements = new ArrayList<Achievement>();
    }

    //Getters for the world parameters
    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    //Getters for lists of creatures and achievements
    public List<Creature> getCreatures() {
        return creatures;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    //Getter, that return creature at this point of our world (or null if there aren't creation at this point)
    public Creature getCreatureAtPosition(int x, int y) {
        for (Creature c : creatures) {
            if (c.getX() == x && c.getY() == y)
                return c;
        }
        return null;
    }


    //Return gameCell at the current position (or BOUNDS if this position is out of our world)
    public GameCell gameCell(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return GameCell.BOUNDS;
        else
            return gameCells[x][y];
    }

    public Achievement getAchievementAtPosition(int x, int y) {
        for (Achievement a : achievements) {
            if (a.getX() == x && a.getY() == y) {
                return a;
            }
        }
        return null;
    }

    //Getters for parameters
    public char glyph(int x, int y) {
        return gameCell(x, y).glyph();
    }

    public Color color(int x, int y) {
        return gameCell(x, y).color();
    }

    //Method to dig the cave
    public void digTheCave(int x, int y) {
        if (gameCell(x, y).isDiggable())
            gameCells[x][y] = GameCell.FLOOR;
    }

    public void remove(Creature other) {
        creatures.remove(other);
    }

    public void remove(Achievement achievement) {
        achievements.remove(achievement);
    }

    public void addCreatureAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        }
        while (!gameCell(x, y).isGround() || getCreatureAtPosition(x, y) != null);

        creature.setX(x);
        creature.setY(y);
        creatures.add(creature);
    }

    public void addAchievementAtEmptyLocation(Achievement achievement) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        }
        while (!gameCell(x, y).isGround() || getCreatureAtPosition(x, y) != null);

        achievement.setX(x);
        achievement.setY(y);
        achievements.add(achievement);
    }

    public void update() {
        for (Creature creature : creatures) {
            creature.update();
        }
    }
}
