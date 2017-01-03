package ru.spbau.filatova;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class StarChatWelcomeFrame {
    private static int PORT_NUMBER = 8081;
    private static boolean isServer = false;

    private JFrame welcomeChatFrame;
    private Container welcomeContentPane;

    private JLabel nicknameLbl;
    private JTextField nameTxt;

    private JLabel isServerLbl;
    private JCheckBox isServerChb;

    private JLabel ipLbl;
    private JTextField ipTxt;

    private JButton startBtn;

    private Controller controller;

    public StarChatWelcomeFrame(Controller controller) {
        this.controller = controller;
    }

    public void showWelcomeFrame() {
        welcomeChatFrame = new JFrame("Connection setup");
        welcomeChatFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        welcomeChatFrame.setResizable(false);

        welcomeContentPane = welcomeChatFrame.getContentPane();

        nicknameLbl = new JLabel("Enter your nickname");
        nicknameLbl.setFont(new Font("Dialog", Font.PLAIN, 25));

        nameTxt = new JTextField();
        nameTxt.setFont(new Font("Dialog", Font.PLAIN, 25));
        nameTxt.setText("Stacy");

        isServerLbl = new JLabel("Work as a server (yes/no)");
        isServerLbl.setFont(new Font("Dialog", Font.PLAIN, 25));

        ipLbl = new JLabel("Enter the IP address");
        ipLbl.setFont(new Font("Dialog", Font.PLAIN, 25));

        ipTxt = new JTextField();
        ipTxt.setFont(new Font("Dialog", Font.PLAIN, 25));

        //Create checkbox that shows if the user want to chat as a server or not. If the user choose server variant
        // then ip field become not editable
        isServerChb = new JCheckBox();
        isServerChb.setSize(10, 10);
        isServerChb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                isServer = !isServer;
                ipTxt.setEditable(!isServer);
            }
        });

        //Create start button. After clicking on this button main chat frame will be loaded with appropriate settings
        startBtn = new JButton("Start chat");
        startBtn.setFont(new Font("Dialog", Font.PLAIN, 25));

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get data from the fields of the welcome frame
                String ipStr = ipTxt.getText();
                String nickname = nameTxt.getText();

                //Check that ip and nickname fields
                if(((ipStr != null)&&(nickname != null)&&(!isServer)) || ((nickname != null)&&(isServer))){
                    //Load main frame with the appropriate settings in two different cases - when user want to chat
                    // as a server or as a client.
                    controller.createMainUI(isServerChb.isSelected(), nickname, ipStr, PORT_NUMBER);
                    welcomeChatFrame.setVisible(false);
                }

            }
        });

        welcomeContentPane.setLayout(new GridBagLayout());
        GridBagConstraints bagConstraints = new GridBagConstraints();

        bagConstraints.fill = GridBagConstraints.HORIZONTAL;
        bagConstraints.weightx = 0.5;
        bagConstraints.insets = new Insets(20, 50, 0, 0);
        bagConstraints.ipady = 20;
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 0;
        welcomeContentPane.add(isServerLbl, bagConstraints);

        bagConstraints.fill = GridBagConstraints.HORIZONTAL;
        bagConstraints.weightx = 0.5;
        bagConstraints.insets = new Insets(20, 0, 0, 50);
        bagConstraints.ipady = 20;
        bagConstraints.gridx = 1;
        bagConstraints.gridy = 0;
        welcomeContentPane.add(isServerChb, bagConstraints);

        bagConstraints.fill = GridBagConstraints.HORIZONTAL;
        bagConstraints.weightx = 0.5;
        bagConstraints.insets = new Insets(0, 50, 0, 0);
        bagConstraints.ipady = 20;
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 1;
        welcomeContentPane.add(nicknameLbl, bagConstraints);

        bagConstraints.fill = GridBagConstraints.HORIZONTAL;
        bagConstraints.weightx = 0.5;
        bagConstraints.insets = new Insets(0, 0, 0, 50);
        bagConstraints.ipady = 20;
        bagConstraints.gridx = 1;
        bagConstraints.gridy = 1;
        welcomeContentPane.add(nameTxt, bagConstraints);

        bagConstraints.fill = GridBagConstraints.HORIZONTAL;
        bagConstraints.weightx = 0.5;
        bagConstraints.insets = new Insets(0, 50, 0, 0);
        bagConstraints.ipady = 20;
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 3;
        welcomeContentPane.add(ipLbl, bagConstraints);

        bagConstraints.fill = GridBagConstraints.HORIZONTAL;
        bagConstraints.weightx = 0.5;
        bagConstraints.insets = new Insets(0, 0, 0, 50);
        bagConstraints.ipady = 20;
        bagConstraints.gridx = 1;
        bagConstraints.gridy = 3;
        welcomeContentPane.add(ipTxt, bagConstraints);

        bagConstraints.fill = GridBagConstraints.HORIZONTAL;
        bagConstraints.ipady = 20;
        bagConstraints.insets = new Insets(50, 0, 0, 0);
        bagConstraints.gridx = 0;
        bagConstraints.gridwidth = 2;
        bagConstraints.gridy = 4;
        welcomeContentPane.add(startBtn, bagConstraints);

        welcomeChatFrame.pack();
        welcomeChatFrame.setBounds(0, 0, 1000, 400);


        welcomeChatFrame.setVisible(true);
    }
}
