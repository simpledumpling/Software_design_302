import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

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

    private Server server;
    private StreamObserver<Message> responseObserver;
    private StreamObserver<Notification> notificationObserver;

    //Two global variables for UI and port, will be initialized in constructor
    private MsgGetter msgGetter;
    private Integer port;

    //Constructor from UI and port
    public StarChatServer(StarChatMainFrame serverUI, Integer port) {
        this.msgGetter = serverUI;
        this.port = port;
    }

    //Run function starts server work
    public void run() {
        new Thread(() -> {
            try {
                server = ServerBuilder.forPort(port)
                        .addService(new MessengerService())
                        .build()
                        .start();
                server.awaitTermination();
            } catch (IOException|InterruptedException e) {
                chatLog.log(Level.SEVERE, LogStrings.grpcServerError, e);
            }
        }).start();
    }

    //Method for sending message with given content to the client
    public void sendMessage(String message, String nickname) {
        if (responseObserver == null) {
            chatLog.log(Level.SEVERE, LogStrings.noClientsConnectedError, new IOException());
        }
        responseObserver.onNext(Message.newBuilder().setName(nickname).setContent(message).build());
    }

    //Method for sending notifications with given content to client
    public void sendNotification(String name) throws IOException {
        if (responseObserver == null) {
            chatLog.log(Level.SEVERE, LogStrings.noClientsConnectedError, new IOException());
        }
        notificationObserver.onNext(Notification.newBuilder().setMessage(name).build());
    }

    private class MessengerService extends StarChatGrpc.StarChatImplBase {
        private Logger messengerLog = Logger.getLogger(StarChatServer.class.getName());

        //Override sendMessages method
        @Override
        public StreamObserver<Message> sendMessages(final StreamObserver<Message> observer) {
            return new StreamObserver<Message>() {
                @Override
                public void onNext(Message message) {
                    msgGetter.getMessage(message.getName(), message.getContent());
                    responseObserver = observer;
                }

                @Override
                public void onError(Throwable exception) {
                    messengerLog.log(Level.WARNING, LogStrings.grpcServerError, exception);
                }

                @Override
                public void onCompleted() {
                    observer.onCompleted();
                }
            };
        }

        //Override sendNotification method
        @Override
        public StreamObserver<Notification> sendNotification(
                final StreamObserver<Notification> observer) {
            return new StreamObserver<Notification>() {
                @Override
                public void onNext(Notification notification) {
                    msgGetter.getNotification(notification.getMessage());
                    notificationObserver = observer;
                }

                @Override
                public void onError(Throwable e) {
                    chatLog.log(Level.SEVERE, LogStrings.serverNotificationsError, e);
                }

                @Override
                public void onCompleted() {
                    observer.onCompleted();
                }
            };
        }
    }
}
