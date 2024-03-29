package meerkat;

import meerkat.ui.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class App {
    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(200, 25));
        button.setMaximumSize(new Dimension(200, 25));
        button.setMargin(new Insets(0, 10, 0, 0));
        return button;
    }
    private static boolean musicOn = false;
    private static Clip clip;
    private static void playMusic(String filePath) {
        try {
            // load the audio file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(App.class.getResourceAsStream("/20180512.wav"));
            clip = AudioSystem.getClip();

            // play the audio
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    //stop music
    private static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel();
        BoardPanel boardPanel = new BoardPanel();

        AppActionListener actionHandler = new AppActionListener(frame, mainPanel, boardPanel);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Add the FinalUI.png image on the left side
        ImageIcon finalUIImage = new ImageIcon(App.class.getResource("/FinalStartUI.png"));
        JLabel finalUIImageView = new JLabel(finalUIImage);
        mainPanel.add(finalUIImageView, BorderLayout.WEST);

        JButton startGameAIButton = createButton("Start game with AI");
        startGameAIButton.setActionCommand("StartAI");
        startGameAIButton.addActionListener(actionHandler);

        JButton startGameFriendButton = createButton("Start game with your friend");
        startGameFriendButton.setActionCommand("StartFriend");
        startGameFriendButton.addActionListener(actionHandler);

        JButton loadGameButton = createButton("Load game");
        loadGameButton.setActionCommand("Load");
        loadGameButton.addActionListener(actionHandler);

        JButton helpButton = createButton("Help / Tutorial");
        helpButton.setActionCommand("Tutorial");
        helpButton.addActionListener(actionHandler);

        JButton exitButton = createButton("Exit");
        exitButton.setActionCommand("Exit");
        exitButton.addActionListener(actionHandler);

        // Add event handlers for the buttons

        // Add the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 30));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(startGameAIButton);
        buttonPanel.add(startGameFriendButton);
        buttonPanel.add(loadGameButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the music button
        ImageIcon startMusicImage = new ImageIcon(App.class.getResource("/startMusic.png"));
        JButton musicButton = new JButton(startMusicImage);
        musicButton.setActionCommand("Music");
        musicButton.setBackground(Color.WHITE);
        musicButton.setPreferredSize(new Dimension(75, 75));
        musicButton.setBorderPainted(false);
        musicButton.addActionListener(actionHandler);
        
        musicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the music button click
                musicOn = !musicOn;
                if (musicOn) {
                    // Turn on the music
                    System.out.println("Music On");
                    playMusic("/20180512.wav");
                } else {
                    // Turn off the music
                    System.out.println("Music Off");
                    stopMusic();
                }
            }
        });

        JPanel musicButtonPanel = new JPanel();
        musicButtonPanel.setBackground(Color.WHITE);
        musicButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        musicButtonPanel.add(musicButton);

        mainPanel.add(musicButtonPanel, BorderLayout.SOUTH);

        // Set up the frame
        frame.setTitle("Hive");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        while (true) {
            frame.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private static class AppActionListener implements ActionListener {
        JFrame frame;
        JPanel menu;
        BoardPanel gamePanel;

        AppActionListener(JFrame frame, JPanel menuPanel, BoardPanel gamePanel) {
            this.menu = menuPanel;
            this.gamePanel = gamePanel;
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            switch (event.getActionCommand()) {
                case "StartAI":
                    frame.setContentPane(gamePanel);
                    frame.setVisible(true);
                    gamePanel.initGame(true);
                    break;
                case "StartFriend":
                    break;
                case "Load":
                    break;
                case "Tutorial":
                    break;
                case "Exit":
                	System.exit(0);
                    break;
                case "Music":
                    break;
            }
        }

    }
}
