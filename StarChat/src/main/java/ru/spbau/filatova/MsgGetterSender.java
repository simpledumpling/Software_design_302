package ru.spbau.filatova;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for getting and sending messages between server and client
 */
public class MsgGetterSender {
    //There are two static constants for message byte and exit byte
    private static final int MESSAGE_BYTE = 1;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private MsgGetter msgGetter;

    //Chat log's variable for the MsgGetterSender class
    private Logger chatLog = Logger.getLogger(StarChatServer.class.getName());

    private boolean isRun = false;

    public MsgGetterSender(InputStream inputStream, OutputStream outputStream, MsgGetter msgGetter) {
        this.inputStream = new DataInputStream(inputStream);
        this.outputStream = new DataOutputStream(outputStream);
        this.msgGetter = msgGetter;
    }

    //Function to run message getter/sender
    public void run() {
        isRun = true;
        while (isRun) {
            try {
                Integer messageByte = inputStream.readInt();
                switch (messageByte) {
                    case MESSAGE_BYTE:
                        String nickname = inputStream.readUTF();
                        String message = inputStream.readUTF();
                        msgGetter.getMessage(message, nickname);
                        break;
                    default:
                        isRun = false;
                        break;
                }
            } catch (IOException e) {
                chatLog.log(Level.SEVERE, LogStrings.msgGetterSenderError + LogStrings.inputError, e);
            }
        }
    }

    //Function to send message
    public void sendMessage(String message, String nickname) {
        try {
            outputStream.writeInt(MESSAGE_BYTE);
            outputStream.writeUTF(nickname);
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            chatLog.log(Level.SEVERE, LogStrings.msgGetterSenderError + LogStrings.outputError, e);
        }
    }
}
