package ru.spbau.filatova;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Server class for StarChat contains 2 methods:
        - void getMessage() - for getting messages from client
        - void sendMessage(String message) - for sending messages with given content to client
 */
class StarChatServer {

    private static final int MESSAGE_BYTE = 1;
    private static final int EXIT_BYTE = 2;
    //Chat log's variable for the StarChatServer class
    private Logger chatLog = Logger.getLogger(StarChatServer.class.getName());

    //Boolean is_run variable for indicating is our chat alive or we end discussion
    private boolean is_run = false;

    //Two global variables for StarChatServer for server socket and UI, will be initialized in constructor
    private ServerSocket mainServer;
    private StarChatMainFrame serverUI;
    private Integer port;

    private DataInputStream serverInput;
    private DataOutputStream serverOutput;

    private Socket client;

    public StarChatServer(StarChatMainFrame serverUI, Integer port) {
        this.serverUI = serverUI;
        this.port = port;
    }

    /*
        Variable for client socket. Global for the StarChatServer class because we need this socket in both methods.
        Will be initialized in the getMessage() method.
     */


    public void run() {
        boolean is_accept = false;

        try {
            mainServer = new ServerSocket(port);
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, "Fail to create socket", e);
        }
        //Try to create a connection with a client. If we get an exception - write about it to the chatLog
        while (!is_accept) {
            try {
                client = mainServer.accept();
                is_accept = true;
                chatLog.log(Level.SEVERE, "Connect!!");
            } catch (IOException e) {
                chatLog.log(Level.SEVERE, LogStrings.acceptError, e);
            }
        }

        try {
            serverInput = new DataInputStream(client.getInputStream());
            serverOutput = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, "Error in creating server input/output streams", e);
        }

    }

    /*
        getMessage() - method for getting messages from client
     */
    public void getMessage() {
//        //Try to create a connection with a client. If we get an exception - write about it to the chatLog
         /*
            Try to get message from the socket input stream and show it to the user, using StarChatUI.
            If we get an exception - write about it to the chatLog
         */
        try {
            //Main cycle, where we get messages from client until we get exit code
            while (is_run) {
                switch (serverInput.readInt()) {
                    case MESSAGE_BYTE:
                        String nickname = serverInput.readUTF();
                        String message = serverInput.readUTF();

                        serverUI.showMessage(message, nickname);
                        break;
                    case EXIT_BYTE:
                        client.close();
                        is_run = false;
                        break;
                    default:
                        break;
                }
            }

        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.serverGetMessageError + LogStrings.inputError, e);
        }

        //Try to close socket. If we get an exception - write about it to the chatLog and throw exception
        try {
            client.close();
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.serverGetMessageError + LogStrings.closeError, e);
            //throw e;
        }

    }

    /*
        sendMessage(String message) - method for sending message with given content to the client
     */
    public synchronized void sendMessage(String message, String nickname) {
        /*
            Try to write message content to the socket output stream and show it to the user, using StarChatUI.
            If we get an exception - write about it to the chatLog
         */
        try {
            serverOutput.writeInt(MESSAGE_BYTE);
            serverOutput.writeUTF(nickname);
            serverOutput.writeUTF(message);
            serverOutput.flush();
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.serverSendMessageError + LogStrings.outputError, e);
        }
    }

    public synchronized void sendExitCode() throws IOException {
        try (DataOutputStream serverOutput = new DataOutputStream(client.getOutputStream());) {
            serverOutput.writeInt(EXIT_BYTE);
            serverOutput.flush();
            client.close();
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.serverSendExitCode + LogStrings.outputError, e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}
