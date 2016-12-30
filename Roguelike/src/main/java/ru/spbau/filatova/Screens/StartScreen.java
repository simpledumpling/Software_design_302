package ru.spbau.filatova.Screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

//StartScreen implements Screen interface - it is a first screen, that we'll show to user
public class StartScreen implements Screen {
    public void displayOutputToUser(AsciiPanel terminal) {
        terminal.write("Welcome to the roguelike!!", 1, 1);
        terminal.write("You are very hungry hero!", 1, 2);
        terminal.write("Find fruits and vegetables and eat them!", 1, 3);
        terminal.write("But be aware of awful monsters!", 1, 4);
        terminal.writeCenter("-- press [enter] to start --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new MainScreen() : this;
    }
}
