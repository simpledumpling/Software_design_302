//Special class for string constant used in logging
class LogStrings {
    static final String acceptError = "StarChatServer getMessage exception. Accept failed: ";

    static final String successfulConnection = "StarChatServer. Connected successfully.";
    static final String serverSocketCreationFailed =  "Server. Server socket creation failed.";

    static final String unableToConnectToServer = "Client send_message(). Unable to connect to server.";
    static final String unableToConnectToClient = "Server send_message(). Unable to connect to client.";

    static final String grpcClientError = "Grpc client. Error in getting message.";
    static final String grpcServerError = "Grpc server. Error in getting message.";

    static final String msgGetterSenderError = "MsgGetterSender. ";
    static final String getterSenderNotificationError = "MsgGetterSender. Notification error.";
    static final String noClientsConnectedError = "Grpc server. No clients connected error.";

    static final String notificationsError = "Grps client. Notifications error.";
    static final String controllerNotificationsError = "Grps controller. Notifications error.";
    static final String serverNotificationsError = "Grpc server. Notifications errror.";

    static final String clientIOStreamsError = "StarChatClient run. Creating input/output stream failed";

    static final String testOutputStreamsError = "MsgGetterSenderTest testGetAndSend. Creating output streams failed.";

    static final String creatingSocketError = "StarChatClient run. Creating socket failed: ";

    static final String inputError = " Work with input stream failed: ";
    static final String outputError = " Work with output stream failed: ";
}