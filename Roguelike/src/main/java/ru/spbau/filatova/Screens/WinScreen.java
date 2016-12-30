package ru.spbau.filatova.Screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

//Screen, displayed to user when he win
public class WinScreen implements Screen{
    public void displayOutputToUser(AsciiPanel terminal) {
        terminal.write("Congratulation, brave hero! You won!!", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new MainScreen() : this;
    }
}
