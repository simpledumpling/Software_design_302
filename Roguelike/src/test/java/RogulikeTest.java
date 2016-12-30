import asciiPanel.AsciiPanel;
import org.junit.Test;
import ru.spbau.filatova.Creature;
import ru.spbau.filatova.GameWorld;
import ru.spbau.filatova.PlayerActor;
import ru.spbau.filatova.Screens.*;
import ru.spbau.filatova.WorldBuilder;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RogulikeTest {
    @Test
    public void testCheatWin() {
        KeyEvent enterBtn = new KeyEvent(new Button("enter"),KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        KeyEvent cheatWin = new KeyEvent(new Button("cheat btn"), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_F10, KeyEvent.CHAR_UNDEFINED);
        //Check that after start screen we'll get main
        StartScreen startScreen = new StartScreen();
        Screen main = startScreen.respondToUserInput(enterBtn);
        assertEquals(main.getClass(), MainScreen.class);

        //Check that after pressing F10 btn we will go to the cheatWin screen
        Screen cheatWinScreen = main.respondToUserInput(cheatWin);
        assertEquals(cheatWinScreen.getClass(), CheatingScreen.class);
    }
}
