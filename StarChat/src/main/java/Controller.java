import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//This class is a controller, connecting UI with client and server
//To start chat run main() function in this file
public class Controller {
    //Controller includes server, client, mainFrame as ui and welcome frame
    private StarChatServer server;
    private StarChatClient client;
    private StarChatMainFrame ui;
    private StarChatWelcomeFrame welcomeFrame;

    //This variable is true when we work with chat as server and false - when as client
    private boolean isServer;

    //Chat log's variable for the Controller class
    private Logger chatLog = Logger.getLogger(StarChatServer.class.getName());

    //Main function, starts chat
    public static void main(String[] args) throws IOException {
        new Controller().run();
    }

    //To start chat we need to create and show welcome frame to user
    public void run() {
        welcomeFrame = new StarChatWelcomeFrame(this);
        welcomeFrame.showWelcomeFrame();
    }

    //This function create main frame with given parameters
    public synchronized void createMainUI(final boolean isServer, String nickname, String ipStr, Integer PORT_NUMBER) {
        //We need to know if we're working as a server or not
        this.isServer = isServer;

        //Create main frame and show it to user
        ui = new StarChatMainFrame(this, nickname);
        ui.showMainFrame();

        //If server variable is true, we should run server, otherwise we should run client
        if (isServer) {
            server = new StarChatServer(ui, PORT_NUMBER);
            server.run();

        } else {
            client = new StarChatClient(ui, PORT_NUMBER, ipStr);
            client.run();
        }
    }

    //To send message we call sendMessage function from server/client
    public void sendMessage(String message, String nickname) {
        if (isServer) {
            server.sendMessage(message, nickname);
        } else {
            client.sendMessage(message, nickname);
        }
    }

    //To send notification, we call sendNotification function from StarChatMainFrame
    public void sendNotification(String name) {
        if (isServer) {
            try {
                server.sendNotification(name);
            } catch (IOException e) {
                chatLog.log(Level.SEVERE, LogStrings.serverNotificationsError, e);
            }
        } else {
            if (client != null) {
                try {
                    client.sendNotification(name);
                } catch (IOException e) {
                    chatLog.log(Level.SEVERE, LogStrings.notificationsError, e);
                }
            }
        }
    }
}
