package ru.spbau.filatova;


import java.awt.*;
import java.util.*;
import java.util.List;

public class Creature {
    //Our game world
    private GameWorld gameWorld;

    //Set how many achievements we need to get to win
    private static int ACHIEVEMENT_POINTS_NEEDED = 10;

    //Creature's position in two dimensions x and y
    private int x;
    private int y;

    private List<Achievement> baggage = new ArrayList<Achievement>();

    //Parameters of the creature's visualization
    private char glyph;
    private Color color;

    //How much achievement point get this creature
    private int achievementPoints;

    //Health and Attack parameters of the creature
    private int hp;
    private int attackValue;

    //Creature actor
    private CreatureActor actor;

    public Creature(GameWorld world, char glyph, Color color, int hp, int attackValue) {
        this.gameWorld = world;
        this.glyph = glyph;
        this.color = color;
        this.hp = hp;
        this.attackValue = attackValue;
    }

    //Getters and setters for class attributes
    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public int hp() {
        return hp;
    }

    public int attackValue() {
        return attackValue;
    }

    public char glyph() {
        return glyph;
    }

    public Color color() {
        return color;
    }

    public void setCreatureActor(CreatureActor actor) {
        this.actor = actor;
    }

    //Setters for X and Y coordinates of the creature
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //Getters for X and Y coordinates of the creature
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    //Method that allows creature gig the caves
    public void digTheCave(int dx, int dy) {
        gameWorld.digTheCave(dx, dy);
    }

    //Method to make the step
    public void makeTheStep(int dx, int dy) {
        Creature enemyCreature = gameWorld.getCreatureAtPosition(x + dx, y + dy);
        if (enemyCreature == null) {
            Achievement achievement = gameWorld.getAchievementAtPosition(x + dx, y + dy);
            if (achievement != null) {
                pick(achievement);
            } else {
                actor.onEnter(x + dx, y + dy, gameWorld.gameCell(x + dx, y + dy));
            }
        } else {
            attack(enemyCreature);
        }

    }

    public void pick(Achievement achievement) {
        actor.onPick(achievement);
        baggage.add(achievement);
        achievementPoints += achievement.getAchievementPoints();
    }

    public void attack(Creature enemyCreature) {
        actor.onAttack(enemyCreature);
        enemyCreature.attacked();
    }

    public void die() {
        gameWorld.remove(this);
        actor.onDeath();
    }

    public void modifyHp(int amount) {
        hp += amount;
        if (isDead()) {
            die();
        }
    }

    public boolean isDead() {
        return hp < 1;
    }

    public boolean isWinner() {
        return achievementPoints >= ACHIEVEMENT_POINTS_NEEDED;
    }

    public void attacked() {
        actor.onAttacked();
    }

    public void update() {
        actor.onUpdate();
    }


}
