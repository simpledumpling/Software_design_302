package ru.spbau.filatova.Screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

//Interface for screen with two methods
public interface Screen {
    //displayOutputToUser get asciiPanel and display output to user
    public void displayOutputToUser(AsciiPanel terminal);

    //respondToUserInput get key event and return a new screen
    public Screen respondToUserInput(KeyEvent key);
}
