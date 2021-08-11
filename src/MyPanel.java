import javafx.scene.layout.Border;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import static java.awt.BorderLayout.CENTER;

public class MyPanel {

    private JLabel label;
    private JLabel title;
    private JFrame frame;
    private JPanel titlePanel;
    private JPanel serverNamePanel;
    private JPanel serverStatusPanel;
    private JLabel[] serverName;
    private JLabel[] serverStatus;

    String[] serversName = {"Apple.com","127.0.0.1","192.168.2.81","192.168.2.19","127.0.0.1","127.0.0.1","127.0.0.1","127.0.0.1","127.0.0.1"};
    Timer timer = new Timer();
    private String ipAddress;
    private Boolean isRunning = true;

    // initialize the GUI
    public MyPanel() throws IOException {
        JFrame frame = new JFrame();

        title = new JLabel("Network Monitoring Application");

        titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20,100,100,100));
        titlePanel.setLayout(new GridLayout(0,1));

        serverNamePanel = new JPanel();
        serverNamePanel.setLayout(new GridLayout(0,1));

        serverStatusPanel = new JPanel();
        serverStatusPanel.setLayout(new GridLayout(0,1));


        serverName = createLabelsName();
        for (int i=0 ;i < serversName.length; i++) {
            serverNamePanel.add(serverName[i]);
        }

        serverStatus = createLabelsStatus();
        for (int i=0 ;i < serverStatus.length; i++) {
            serverStatusPanel.add(serverStatus[i]);
        }

        //Frame UI
        frame.add(title, BorderLayout.NORTH);
        frame.add(titlePanel, CENTER);
        frame.add(serverNamePanel, BorderLayout.WEST);
        frame.add(serverStatusPanel, BorderLayout.EAST);
        frame.setSize(400,400);
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        frame.setTitle("GUI");
        frame.pack();
        frame.setVisible(true);

        // run timer To be delete


    }

    // array of label that contains the name of each servers
    private JLabel[] createLabelsName() {
        JLabel[] labels = new JLabel[serversName.length];
        for (int i = 0; i < serversName.length; i++) {
            labels[i] = new JLabel(serversName[i]);
        }
        return labels;
    }

    // array of label that contains the status of each servers
    private JLabel[] createLabelsStatus() throws IOException {
        JLabel[] labels = new JLabel[serversName.length];
        for (int i = 0;i < serversName.length;i++) {
            // initialize UI of the labels
            labels[i] = new JLabel("Not Started");
            labels[i].setForeground(Color.white);
            labels[i].setBackground(Color.black);
            labels[i].setOpaque(true);

            // logic of the labels
            int finalI = i;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        labels[finalI].setText(sendPingRequest(serversName[finalI]) ? "Running" : "Down");
                        labels[finalI].setBackground(sendPingRequest(serversName[finalI]) ? Color.green : Color.red);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }, 0, 5000);

        }
        return labels;
    }

    public Boolean sendPingRequest(String ipAddress) throws IOException {
        InetAddress serverName = InetAddress.getByName(ipAddress);
        System.out.println("Sending Ping request to " + ipAddress);
        if (serverName.isReachable(5000)) {
            System.out.println("Host is reachable");
            return isRunning = true;
        } else {
            System.out.println("Sorry, we can't reach the host");
            return isRunning = false;
        }
    }

    public String setServerName(String ipAddress) throws  IOException {
        return ipAddress;
    }

    // main
    public static void main(String[] args) throws IOException {
        new MyPanel();


    }


}

