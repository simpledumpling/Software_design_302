import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

//Class for server
class StarChatServer {
    //Chat log's variable for the StarChatServer class
    private Logger chatLog = Logger.getLogger(StarChatServer.class.getName());

    //Boolean is_run variable for indicating is our chat alive or we end discussion
    private boolean is_run = false;

    //Server socket for our StarChatServer
    private ServerSocket mainServer;

    //Two global variables for UI and port, will be initialized in constructor
    private MsgGetter msgGetter;
    private Integer port;

    //Variable for MsgGetterSetter to get messages
    private MsgGetterSender msgGetterSender = null;

    //Input and output streams for our server
    private DataInputStream serverInput;
    private DataOutputStream serverOutput;

    //Client socket to create connection with it
    private Socket client;

    //Constructor from UI and port
    public StarChatServer(StarChatMainFrame serverUI, Integer port) {
        this.msgGetter = serverUI;
        this.port = port;
    }

    //Run function starts server work
    public void run() {
        new Thread(() -> {
            is_run = false;

            //Try to create server socket. If we get an exception - write about it to the chatLog
            try {
                mainServer = new ServerSocket(port);
            } catch (IOException e) {
                chatLog.log(Level.SEVERE, LogStrings.serverSocketCreationFailed, e);
            }

            //Try to create a connection with a client. If we get an exception - write about it to the chatLog
            while (!is_run) {
                try {
                    client = mainServer.accept();
                    is_run = true;
                    //If we accept successfully - write about it to the log
                    chatLog.log(Level.SEVERE, LogStrings.successfulConnection);
                } catch (IOException e) {
                    //If we have an exception - write about it to the log
                    chatLog.log(Level.SEVERE, LogStrings.acceptError, e);
                }
            }

            //Try to get input and output streams. If we get an exception - write about it to the chatLog
            try {
                serverInput = new DataInputStream(client.getInputStream());
                serverOutput = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                chatLog.log(Level.SEVERE, "Error in creating server input/output streams", e);
            }

            //Create new MsgGetterSetter and run it to get messages
            msgGetterSender = new MsgGetterSender(serverInput, serverOutput, msgGetter);
            msgGetterSender.run();
        }).start();
    }

    //Method for sending message with given content to the client
    public void sendMessage(String message, String nickname) {
        if (msgGetterSender == null) {
            chatLog.log(Level.SEVERE, LogStrings.unableToConnectToClient, new IOException());
        }
        //To send message we use msgGetterSetter and it's method sendMessage
        msgGetterSender.sendMessage(message, nickname);
    }
}
