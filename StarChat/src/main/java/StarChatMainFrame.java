import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class StarChatMainFrame implements MsgGetter {
    private JFrame mainChatFrame;
    private Container mainContentPane;

    private JLabel welcomeLbl;
    private JLabel infoLbl;
    private JTextArea showMsgArea;
    private JScrollPane showMsgPane;
    private JTextArea writeMsgArea;
    private JScrollPane writeMsgPane;
    private Controller controller;

    private JButton sendBtn;

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

        welcomeLbl = new JLabel("Welcome to the StarChat, " + nickname + "!");
        welcomeLbl.setFont(new Font("Dialog", Font.PLAIN, 25));

        infoLbl = new JLabel("To send message enter text to " +
                "the bottom field and press \"Send message\" button.");
        infoLbl.setFont(new Font("Dialog", Font.PLAIN, 25));

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

        c.fill = GridBagConstraints.CENTER;
        c.ipady = 20;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        mainContentPane.add(infoLbl, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 500;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
        mainContentPane.add(showMsgPane, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 180;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 3;
        mainContentPane.add(writeMsgPane, c);

        sendBtn = new JButton("Send message");
        sendBtn.setFont(new Font("Dialog", Font.PLAIN, 20));
        boolean is_enabled = true;

        sendBtn.setEnabled(is_enabled);
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = writeMsgArea.getText();
                controller.sendMessage(message, nickname);
                showMsgArea.append("\n" + nickname + ": " + message);
            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 4;
        mainContentPane.add(sendBtn, c);

        mainChatFrame.pack();
        mainChatFrame.setBounds(0, 0, 1600, 1200);

        mainChatFrame.setVisible(true);


        mainChatFrame.setVisible(true);
    }

    @Override
    public void getMessage(String message, String nickname) {
        showMsgArea.append("\n" + nickname + ": " + message);
    }
}
