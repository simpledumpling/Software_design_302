package ru.spbau.filatova;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
    Client class for StarChat contains 3 methods:
        - void run(int serverPort, String address) - method to connect to the server with given parameters
        - void getMessage()
 */
class StarChatClient {
    private static final int MESSAGE_BYTE = 1;
    private static final int EXIT_BYTE = 2;
    //Chat log's variable for the StarChatServer class
    private Logger chatLog = Logger.getLogger(StarChatServer.class.getName());

    //Boolean is_run variable for indicating is our chat alive or we end discussion
    private boolean is_run = false;

    /*
        Two global variables for StarChatClient for socket and UI.
        Socket variable will be initialized in run method and UI variable will be initialized in constructor
     */
    private Socket server;
    private StarChatMainFrame clientUI;

    private DataInputStream clientInput;
    private DataOutputStream clientOutput;

    private String address;
    private int serverPort;




    public StarChatClient(StarChatMainFrame clientUI, int serverPort, String address) {
        this.clientUI = clientUI;
        this.address = address;
        this.serverPort = serverPort;
        //Try to create connection. If we get an exception - write about it to the chatLog
    }

    public void run() {
        try {
            server = new Socket(address, serverPort);
            is_run = true;

        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.creatingSocketError, e);
        }

        try {
            clientInput = new DataInputStream(server.getInputStream());
            clientOutput = new DataOutputStream(server.getOutputStream());
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, "Error with creating streams", e);
        }


    }

    /*
        getMessage() - method for getting messages from server
     */
    public void getMessage() {
        is_run = true;

        /*
            Try to get message from the socket input stream and show it to the user, using StarChatUI.
            If we get an exception - write about it to the chatLog
         */
        try {
            //Main cycle, where we get messages from server until we get exit code
            while (is_run) {
                Integer messageByte = clientInput.readInt();
                switch (messageByte) {
                    case MESSAGE_BYTE:
                        String nickname = clientInput.readUTF();
                        String message = clientInput.readUTF();
                        clientUI.showMessage(message, nickname);
                        break;
                    case EXIT_BYTE:
                        server.close();
                        is_run = false;
                        break;
                    default:
                        break;
                }
            }

        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.clientGetMessageError + LogStrings.inputError, e);
        }

        //Try to close socket. If we get an exception - write about it to the chatLog and throw exception
        try {
            server.close();
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.clientGetMessageError + LogStrings.closeError, e);
        }
    }

    /*
        sendMessage(String message) - method for sending message with given content to the server
     */
    public void sendMessage(String message, String nickname) {
        /*
            Try to write message content to the socket output stream and show it to the user, using StarChatUI.
            If we get an exception - write about it to the chatLog
         */
        try {
            clientOutput.writeInt(MESSAGE_BYTE);
            clientOutput.writeUTF(nickname);
            clientOutput.writeUTF(message);
            clientOutput.flush();
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.clientSendMessageError + LogStrings.outputError, e);
        }
    }

    public synchronized void sendExitCode() throws IOException {
        try (DataOutputStream serverOutput = new DataOutputStream(server.getOutputStream());) {
            serverOutput.writeInt(EXIT_BYTE);
            serverOutput.flush();
            server.close();
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.serverSendExitCode + LogStrings.outputError, e);
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }
}