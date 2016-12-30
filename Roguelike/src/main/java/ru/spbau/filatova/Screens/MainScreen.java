package ru.spbau.filatova.Screens;

import asciiPanel.AsciiPanel;
import ru.spbau.filatova.*;

import java.awt.event.KeyEvent;

//Main screen is the screen, where we display the game
public class MainScreen implements Screen {
    private GameWorld gameWorld;
    private Creature player;
    private int screenWidth;
    private int screenHeight;

    public MainScreen() {
        screenWidth = 80;
        screenHeight = 21;
        createWorld();

        CreatureFactory creatureFactory = new CreatureFactory(gameWorld);
        AchievementsFactory achievementsFactory = new AchievementsFactory(gameWorld);
        createCreatures(creatureFactory);
        createAchievements(achievementsFactory);

    }

    private void createCreatures(CreatureFactory creatureFactory) {
        player = creatureFactory.newPlayer();

        for (int i = 0; i < 8; i++) {
            creatureFactory.newStaticMonster();
        }

        for (int i = 0; i < 10; i++) {
            creatureFactory.newDynamicMonster();
        }
    }

    private void createAchievements(AchievementsFactory achievementsFactory) {
        for (int i = 0; i < 4; i++) {
            achievementsFactory.newApple();
            achievementsFactory.newBeet();
            achievementsFactory.newLemon();
            achievementsFactory.newTomato();
        }
    }

    private void createWorld() {
        gameWorld = new WorldBuilder(90, 31)
                .makeCaves()
                .build();
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.getX() - screenWidth / 2, gameWorld.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.getY() - screenHeight / 2, gameWorld.height() - screenHeight));
    }

    private void displayGameCells(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int dx = x + left;
                int dy = y + top;

                Creature creature = gameWorld.getCreatureAtPosition(dx, dy);
                if (creature != null)
                    terminal.write(creature.glyph(), creature.getX() - left, creature.getY() - top, creature.color());
                else {
                    Achievement achievement = gameWorld.getAchievementAtPosition(dx, dy);
                    if (achievement != null)
                        terminal.write(achievement.getGlyph(), achievement.getX() - left, achievement.getY() - top,
                                achievement.getColor());
                    else
                        terminal.write(gameWorld.glyph(dx, dy), x, y, gameWorld.color(dx, dy));
                }


            }
        }

        String stats = String.format("Only %3d hp left!", player.hp());
        terminal.write(stats, 1, 23);
    }

    public void displayOutputToUser(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayGameCells(terminal, left, top);

        terminal.write(player.glyph(), player.getX() - left, player.getY() - top, player.color());
        terminal.write("Health: " + Integer.toString(player.hp()), 1, 23);
        terminal.writeCenter("-- eat fruits and vegetables on 10 points to win --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                player.makeTheStep(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                player.makeTheStep(1, 0);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K:
                player.makeTheStep(0, -1);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J:
                player.makeTheStep(0, 1);
                break;
            case KeyEvent.VK_Y:
                player.makeTheStep(-1, -1);
                break;
            case KeyEvent.VK_U:
                player.makeTheStep(1, -1);
                break;
            case KeyEvent.VK_B:
                player.makeTheStep(-1, 1);
                break;
            case KeyEvent.VK_N:
                player.makeTheStep(1, 1);
                break;
            //Add little cheat ;)
            case KeyEvent.VK_F10:
                return new CheatingScreen();
        }

        gameWorld.update();

        Screen result = this;
        if (player.isDead()) {
            result = new LoseScreen();
        } else if (player.isWinner()) {
            result = new WinScreen();
        }
        return result;
    }
}
