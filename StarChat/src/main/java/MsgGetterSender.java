import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MsgGetterSender {
    //There are two static constants for message byte and exit byte
    private static final int MESSAGE_BYTE = 1;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private MsgGetter msgGetter;

    //Chat log's variable for the MsgGetterSender class
    private Logger chatLog = Logger.getLogger(StarChatServer.class.getName());

    private boolean is_run = false;

    public MsgGetterSender(InputStream inputStream, OutputStream outputStream, MsgGetter msgGetter) {
        this.inputStream = new DataInputStream(inputStream);
        this.outputStream = new DataOutputStream(outputStream);
        this.msgGetter = msgGetter;
    }

    public void run() {
        is_run = true;
        while (is_run) {
            try {
                Integer messageByte = inputStream.readInt();
                switch (messageByte) {
                    case MESSAGE_BYTE:
                        String nickname = inputStream.readUTF();
                        String message = inputStream.readUTF();
                        msgGetter.getMessage(message, nickname);
                        break;
                    default:
                        is_run = false;
                        break;
                }
            } catch (IOException e) {
                chatLog.log(Level.SEVERE, LogStrings.msgGetterSenderError + LogStrings.inputError, e);
            }
        }
    }

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
