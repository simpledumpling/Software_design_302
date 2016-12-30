package ru.spbau.filatova.Screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

//Screen, displayed to user when he lost
public class LoseScreen implements Screen {
    public void displayOutputToUser(AsciiPanel terminal) {
        terminal.write("Oooh, what a pity! You lost.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new MainScreen() : this;
    }
}
