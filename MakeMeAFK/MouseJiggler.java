package GameTool;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;

public class MouseJiggler {
    private static volatile boolean running = false; // Flag to control the loop
    private static Thread mouseMovementThread; // Thread for mouse movement
    private static JButton toggleButton;
    private static JLabel label;

    public static void main(String[] args) {
        // Set the look and feel to Nimbus
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create the GUI frame
        JFrame frame = new JFrame("MAKE ME AFK");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // Set the frame size as fixed
        frame.setSize(300, 250);
        
        // setting icon in the program
        // Load the icon image
        Image icon = null;
        try {
//            icon = ImageIO.read(new File("./res/make_me_afk.jpg"));
                icon = ImageIO.read(MouseJiggler.class.getResource("/res/make_me_afk.jpg"));
        } catch (IOException e){
            e.printStackTrace();
        }
        frame.setIconImage(icon);
        
        // Create a panel to hold the components
        JPanel panel = new JPanel(new BorderLayout());

        // Add a label
        label = new JLabel("Wanna Take a Break?");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        // Add a toggle button to initiate/start/stop mouse cursor movement
        toggleButton = new JButton("AFK Mode");
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setBackground(Color.BLACK);
        toggleButton.setFocusPainted(false);
        toggleButton.setPreferredSize(new Dimension(80, 35));
        toggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleMouseMovement();
            }
        });
        panel.add(toggleButton, BorderLayout.SOUTH);

        // Create a label for "Created By: PunkyZerk"
        JLabel createdByLabel = new JLabel("Created By: PunkyZerk");
        createdByLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        createdByLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        createdByLabel.setForeground(Color.GRAY);
        createdByLabel.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 8)); // Add left padding
        panel.add(createdByLabel, BorderLayout.NORTH);



        // Add the panel to the frame
        frame.getContentPane().add(panel);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Show the frame
        frame.setVisible(true);
    }

    private static void toggleMouseMovement() {
        if (running) {
            stopMouseMovement();
            label.setText("Wanna Take a Break?");
        } else {
            startMouseMovement();
            label.setText("Chilling Being AFK");
        }
    }

    private static void startMouseMovement() {
        running = true;
        toggleButton.setText("Stop");
        toggleButton.setBackground(Color.RED);
        mouseMovementThread = new Thread(new Runnable() {
            public void run() {
                // Move the mouse cursor slightly every few seconds
                while (running) {
                    try {
                        Robot robot = new Robot();
                        Random random = new Random();
                        int dx = random.nextInt(3) - 1; // Random value between -1 and 1
                        int dy = random.nextInt(3) - 1; // Random value between -1 and 1
                        Point currentPos = MouseInfo.getPointerInfo().getLocation();
                        robot.mouseMove(currentPos.x + dx, currentPos.y + dy);
                        Thread.sleep(3000); // Adjust the sleep duration as needed
                    } catch (InterruptedException ex) {
                        // Thread interrupted, stop the mouse movement
                        break;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                toggleButton.setText("AFK Mode");
                toggleButton.setBackground(Color.BLACK);
            }
        });
        mouseMovementThread.start();
    }

    private static void stopMouseMovement() {
        running = false;
        mouseMovementThread.interrupt(); // Interrupt the mouse movement thread to stop it immediately
    }
}
