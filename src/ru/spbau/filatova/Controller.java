package ru.spbau.filatova;


import java.io.IOException;

public class Controller {
    private StarChatServer server;
    private StarChatClient client;
    private StarChatMainFrame ui;
    private StarChatWelcomeFrame welcomeFrame;

    private boolean isServer;

    public static void main(String[] args) throws IOException {
        new Controller().run();
    }

    public void run() {
        welcomeFrame = new StarChatWelcomeFrame(this);
        welcomeFrame.showWelcomeFrame();
    }

    public synchronized void createMainUI(boolean isServer, String nickname, String ipStr, Integer PORT_NUMBER) {
        this.isServer = isServer;
        System.err.println(isServer + " " + nickname + " " + ipStr + " " + PORT_NUMBER);
        ui = new StarChatMainFrame(this, nickname);
        ui.showMainFrame();

        if (isServer) {
            server = new StarChatServer(ui, PORT_NUMBER);
        } else {
            client = new StarChatClient(ui, PORT_NUMBER, ipStr);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                getMessage();
            }
        }).start();
    }

    public synchronized void sendMessage(String message, String nickname) {
        if (isServer) {
            server.sendMessage(message, nickname);
        } else {
            client.sendMessage(message, nickname);
        }
    }

    public synchronized void getMessage() {
        if (isServer) {
            server.run();
            server.getMessage();
        } else {
            client.run();
            client.getMessage();
        }
    }
}
