package ru.spbau.filatova;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

public class StarChatMainFrame {
    private JFrame mainChatFrame;
    private Container mainContentPane;

    private JMenuBar menuBar;
    private JMenu fileMenu;

    private JMenuItem changeNickMenu;
    private JMenuItem changeMenu;
    private JMenuItem exitMenu;

    private JLabel welcomeLbl;
    private JTextArea showMsgArea;
    private JScrollPane showMsgPane;
    private JTextArea writeMsgArea;
    private JScrollPane writeMsgPane;
    private Controller controller;

    private String nickname;

    public StarChatMainFrame(Controller controller, String nickname) {
        this.controller = controller;
        this.nickname = nickname;
    }

    public void showMainFrame() {

        mainChatFrame = new JFrame("StarChat");
        mainChatFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainChatFrame.setResizable(false);

        mainContentPane = mainChatFrame.getContentPane();

        menuBar = new JMenuBar();

        fileMenu = new JMenu("Chat actions");
        fileMenu.setFont(new Font("Dialog", Font.PLAIN, 30));

        changeNickMenu = new JMenuItem("Change nickname");
        changeNickMenu.setFont(new Font("Dialog", Font.PLAIN, 25));
        changeMenu = new JMenuItem("Change conversation partner");
        changeMenu.setFont(new Font("Dialog", Font.PLAIN, 25));
        exitMenu = new JMenuItem("End conversation and exit");
        exitMenu.setFont(new Font("Dialog", Font.PLAIN, 25));

        fileMenu.add(changeNickMenu);
        fileMenu.add(changeMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenu);

        menuBar.add(fileMenu);
        mainChatFrame.setJMenuBar(menuBar);

        welcomeLbl = new JLabel("Welcome to the StarChat, " + nickname + "! To send message enter text to " +
                "the bottom field and press \"Send message\" button.");
        welcomeLbl.setFont(new Font("Dialog", Font.PLAIN, 25));

        //Text area where our messages wil be shown
        showMsgArea = new JTextArea(5, 20);
        showMsgArea.setFont(new Font("Dialog", Font.PLAIN, 30));
        showMsgPane = new JScrollPane(showMsgArea);
        showMsgArea.setEditable(false);

        //Text area where we can write messages to our discussion partner
        writeMsgArea = new JTextArea();
        writeMsgArea.setFont(new Font("Dialog", Font.PLAIN, 30));
        writeMsgPane = new JScrollPane(writeMsgArea);

        mainContentPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.CENTER;
        c.ipady = 20;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        mainContentPane.add(welcomeLbl, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 500;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        mainContentPane.add(showMsgPane, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 180;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
        mainContentPane.add(writeMsgPane, c);

        JButton sendBtn = new JButton("Send message");
        sendBtn.setFont(new Font("Dialog", Font.PLAIN, 20));
        boolean is_enabled = true;

        sendBtn.setEnabled(is_enabled);
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = writeMsgArea.getText();
                controller.sendMessage(message, nickname);
            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 3;
        mainContentPane.add(sendBtn, c);

        mainChatFrame.pack();
        mainChatFrame.setBounds(0, 0, 1500, 1200);

        mainChatFrame.setVisible(true);


        mainChatFrame.setVisible(true);
    }

    public void showMessage(String message, String nickname) {
        String messages = showMsgArea.getText();
        showMsgArea.append("\n" + nickname + ": " + message);
    }
}
