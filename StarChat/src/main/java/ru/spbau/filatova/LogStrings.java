package ru.spbau.filatova;

/**
 * Special class for string constant used in logging
 */
public class LogStrings {
    static final String acceptError = "StarChatServer getMessage exception. Accept failed: ";

    static final String successfulConnection = "StarChatServer. Connected successfully.";
    static final String serverSocketCreationFailed = "Server. Server socket creation failed.";

    static final String unableToConnectToServer = "Client send_message(). Unable to connect to server.";
    static final String unableToConnectToClient = "Server send_message(). Unable to connect to client.";

    static final String msgGetterSenderError = "MsgGetterSender. ";

    static final String clientIOStreamsError = "StarChatClient run. Creating input/output stream failed";

    static final String testOutputStreamsError = "MsgGetterSenderTest testGetAndSend. Creating output streams failed.";

    static final String creatingSocketError = "StarChatClient run. Creating socket failed: ";

    static final String inputError = " Work with input stream failed: ";
    static final String outputError = " Work with output stream failed: ";
}