package ru.spbau.filatova.Screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class CheatingScreen implements Screen {
    public void displayOutputToUser(AsciiPanel terminal) {
        terminal.write("Congratulation, brave hero! You won!!", 1, 1);
        terminal.write("You're very smart and resourceful!! You find cheat!", 1, 2);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return null;
    }
}
