import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;


//Class for client
class StarChatClient {
    //Chat log's variable for the StarChatServer class
    private Logger chatLog = Logger.getLogger(StarChatServer.class.getName());

    //Boolean is_run variable for indicating is our chat alive or we end discussion
    private boolean is_run = false;

    //Three global variables for UI, IP-address and server port, will be initialized in constructor
    private MsgGetter msgGetter;
    private String address;
    private int serverPort;

    //Grpc channel
    private ManagedChannel channel;

    //Create two observers for messages and notifications
    private StreamObserver<Message> requestObserver;
    private StreamObserver<Notification> notificationObserver;

    private StarChatGrpc.StarChatStub asyncStub;


    //Constructor from UI, port and IP-address
    public StarChatClient(StarChatMainFrame clientUI, int serverPort, String address) {
        this.msgGetter = clientUI;
        this.address = address;
        this.serverPort = serverPort;
    }

    //Run function starts client work
    public synchronized void run() {
        new Thread(() -> {
            channel = ManagedChannelBuilder.forAddress(address, serverPort)
                    .usePlaintext(true)
                    .build();

            asyncStub = StarChatGrpc.newStub(channel);

            requestObserver = asyncStub.sendMessages(new StreamObserver<Message>() {
                @Override
                public void onNext(Message message) {
                    msgGetter.getMessage(message.getName(), message.getContent());
                }

                @Override
                public void onError(Throwable e) {
                    chatLog.log(Level.SEVERE, LogStrings.grpcClientError, e);
                }

                @Override
                public void onCompleted() {

                }
            });
            notificationObserver =
                    asyncStub.sendNotification(new StreamObserver<Notification>() {
                        @Override
                        public void onNext(Notification message) {
                            msgGetter.getNotification(message.getMessage());
                        }

                        @Override
                        public void onError(Throwable e) {
                            chatLog.log(Level.SEVERE, LogStrings.notificationsError, e);
                        }

                        @Override
                        public void onCompleted() {
                        }
                    });

        }).start();


    }

    //Method for sending message with given content to the server
    public void sendMessage(String message, String nickname) {
        requestObserver.onNext(Message.newBuilder().setName(nickname).setContent(message).build());
    }

    //Method for sending notification with given name to the server
    public synchronized void sendNotification(String name) throws IOException {
        notificationObserver.onNext(Notification.newBuilder().setMessage(name).build());
    }
}