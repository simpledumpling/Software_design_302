import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class MsgGetterSenderTest {
    //Chat log's variable for the MsgGetterSender class
    private Logger chatLog = Logger.getLogger(StarChatServer.class.getName());

    @Test
    public void testGetAndSend() {
        //Create input and output streams for client and server
        PipedInputStream clientInput = new PipedInputStream();
        PipedInputStream serverInput = new PipedInputStream();
        PipedOutputStream clientOutput = null;
        PipedOutputStream serverOutput = null;
        try {
            serverOutput = new PipedOutputStream(clientInput);
            clientOutput = new PipedOutputStream(serverInput);
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.testOutputStreamsError, e);
        }

        //Create messages queues for client and server
        List<String> clientMessageQueue = new ArrayList<>();
        List<String> serverMessageQueue = new ArrayList<>();

        //Create MsgGetter for client and server
        // and override getMessage method to add messages to the client's and server's messages queue
        MsgGetter clientMsgGetter = (nickname, message) -> clientMessageQueue.add(nickname + "; " + message);

        MsgGetter serverMsgGetter = (nickname, message) -> serverMessageQueue.add(nickname + "; " + message);

        //Create MsgGetterSender for client and server
        MsgGetterSender clientMsgGetterSender = new MsgGetterSender(clientInput, clientOutput, clientMsgGetter);
        MsgGetterSender serverMsgGetterSender = new MsgGetterSender(serverInput, serverOutput, serverMsgGetter);

        //Start server
        new Thread(serverMsgGetterSender::run
        ).start();

        //Start client
        new Thread(clientMsgGetterSender::run
        ).start();

        //Send messages
        serverMsgGetterSender.sendMessage("Server", "Hello, client!");
        clientMsgGetterSender.sendMessage("PrettyKitty", "Purr!");
        serverMsgGetterSender.sendMessage("Server2", "How are you?");
        clientMsgGetterSender.sendMessage("PrettyKitty", "Meow!!");

        //Wait a little (100 is a random constant, that works)
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }

        //Check if we get all messages (they should be in the client's or server's messages queue)
        //Check sizes of the queues
        assertSame(2, clientMessageQueue.size());
        assertSame(2, serverMessageQueue.size());

        //Check messages in the queues
        assertEquals("Server; Hello, client!", clientMessageQueue.get(0));
        assertEquals("PrettyKitty; Purr!", serverMessageQueue.get(0));
        assertEquals("Server2; How are you?", clientMessageQueue.get(1));
        assertEquals("PrettyKitty; Meow!!", serverMessageQueue.get(1));

    }

}
