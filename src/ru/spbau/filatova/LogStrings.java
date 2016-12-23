package ru.spbau.filatova;

/*
    Special class for string constant used in logging
 */
class LogStrings {
    static final String serverSendExitCode = "StarChatServer sendExitCode. ";
    static final String clientSendExitCode = "StarChatClient sendExitCode. ";

    static final String acceptError = "StarChatServer getMessage exception. Accept failed: ";

    static final String serverGetMessageError = "StarChatServer getMessage.";
    static final String serverSendMessageError = "StarChatServer sendMessage.";

    static final String clientGetMessageError = "StarChatClient getMessage.";
    static final String clientSendMessageError = "StarChatClient sendMessage.";

    static final String sendExitCodeError = "sendExitCode. Work with output stream f";

    static final String creatingSocketError = "StarChatClient run. Creating socket failed: ";

    static final String inputError = " Work with input stream failed: ";
    static final String outputError = " Work with output stream failed: ";

    static final String closeError = " Closing socket failed: ";
}
