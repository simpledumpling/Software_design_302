import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

//Class for client
class StarChatClient {
    //Chat log's variable for the StarChatServer class
    private Logger chatLog = Logger.getLogger(StarChatServer.class.getName());

    //Boolean is_run variable for indicating is our chat alive or we end discussion
    private boolean is_run = false;

    //Two global variables for StarChatClient for socket and UI.

    //Socket for our client
    private Socket server;

    //Three global variables for UI, IP-address and server port, will be initialized in constructor
    private MsgGetter msgGetter;
    private String address;
    private int serverPort;

    //Message getter
    private MsgGetterSender msgGetterSender = null;

    //Input and output streams for sending and receiving messages
    private DataInputStream clientInput;
    private DataOutputStream clientOutput;

    //Constructor from UI, port and IP-address
    public StarChatClient(StarChatMainFrame clientUI, int serverPort, String address) {
        this.msgGetter = clientUI;
        this.address = address;
        this.serverPort = serverPort;
    }

    //Run function starts client work
    public synchronized void run() {
        new Thread(() -> {
            //Try to create Socket with given IP-address and port
            try {
                server = new Socket(address, serverPort);
                //If we succeed, set true to is_run variable - it means our client starts his work
                is_run = true;

            } catch (IOException e) {
                //If we have an exception - write about it to the log
                chatLog.log(Level.SEVERE, LogStrings.creatingSocketError, e);
            }

            //Try to get input and output streams. If we get an exception - write about it to the chatLog
            try {
                clientInput = new DataInputStream(server.getInputStream());
                clientOutput = new DataOutputStream(server.getOutputStream());
            } catch (IOException e) {
                //If we have an exception - write about it to the log
                chatLog.log(Level.SEVERE, LogStrings.clientIOStreamsError, e);
            }

            //Create new MsgGetterSetter and run it to get messages
            msgGetterSender = new MsgGetterSender(clientInput, clientOutput, msgGetter);
            msgGetterSender.run();
        }).start();
    }

    //Method for sending message with given content to the server
    public void sendMessage(String message, String nickname) {
        if (msgGetterSender == null) {
            chatLog.log(Level.SEVERE, LogStrings.unableToConnectToServer, new IOException());
        }
        //To send message we use msgGetterSetter and it's method sendMessage
        msgGetterSender.sendMessage(message, nickname);
    }
}