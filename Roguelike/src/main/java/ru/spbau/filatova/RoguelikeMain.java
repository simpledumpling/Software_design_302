package ru.spbau.filatova;

import asciiPanel.AsciiPanel;
import ru.spbau.filatova.Screens.Screen;
import ru.spbau.filatova.Screens.StartScreen;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RoguelikeMain extends JFrame implements KeyListener {

    private static final long serialVersionUID = 1060623638149583738L;

    private AsciiPanel terminal;
    private Screen screen;

    public RoguelikeMain(){
        super();

        //Set up our game terminal
        terminal = new AsciiPanel();
        terminal.write("Simple roguelike", 1, 1);
        add(terminal);
        pack();

        //Set up our StartScreen
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public void repaint(){
        terminal.clear();
        screen.displayOutputToUser(terminal);
        super.repaint();
    }

    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        RoguelikeMain roguelike = new RoguelikeMain();
        roguelike.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        roguelike.setVisible(true);
    }

}
