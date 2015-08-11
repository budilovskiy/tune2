package com.felixfeatures.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;

import com.felixfeatures.player.*;
import com.felixfeatures.playlist.*;
import com.felixfeatures.util.TimeUtility;

@SuppressWarnings("serial")
public class AppGUI extends JFrame implements PlayListener {

    public AppGUI() {
        initComponents();
    }

    private void initComponents() {
        //
        // Create close label and set behavior
        //
        closeLabel = new JLabel();
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // set cursor
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(0);
            }
        });
        closeLabel.setBounds(580, 5, 15, 15); // set absolute position and size
        getContentPane().add(closeLabel); // add to main frame

        //
        // Create search JTextField and set behavior
        //
        searchField = new JTextField();
        searchField.setFont(new java.awt.Font("Calibri", 0, 12)); // set font
        searchField.setForeground(new Color(153, 153, 153)); // set font color
        searchField.setText(DEFAULT_SEARCH_TEXT); // set default text
        searchField.setCursor(new Cursor(Cursor.TEXT_CURSOR)); // set cursor
        searchField.setOpaque(false); // transparent
        searchField.setBorder(BorderFactory.createEmptyBorder()); // set empty border
		/* focus */
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // remove default text and set black foreground color
                if (searchField.getText().equals(DEFAULT_SEARCH_TEXT)) {
                    searchField.setForeground(Color.BLACK);
                    searchField.setText("");
                }
            }
        });
        /* action */
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchField.getText(), method);
            }
        });
        searchField.setBounds(374, 31, 178, 22); // set absolute position and
        // size
        getContentPane().add(searchField); // add to main frame

        //
        // Create search label and set behavior
        //
        searchLabel = new JLabel();
        searchLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // set cursor
        searchLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                search(searchField.getText(), method);
            }
        });
        searchLabel.setBounds(557, 31, 20, 20); // set absolute position and size
        getContentPane().add(searchLabel); // add to main frame

        //
        // Create artist JTextField
        //
        artistField = new JTextField();
        artistField.setEditable(false); // non-editable
        artistField.setFont(new java.awt.Font("Calibri", java.awt.Font.BOLD, 14)); // set font style
        artistField.setForeground(new Color(105, 105, 105)); // set font color
        artistField.setOpaque(false); // transparent
        artistField.setBackground(new Color(0, 0, 0, 0));
        artistField.setBorder(BorderFactory.createEmptyBorder()); // set empty border
        artistField.setCursor(new Cursor(Cursor.TEXT_CURSOR)); // set cursor style
        artistField.setHorizontalAlignment(JTextField.CENTER); // centered alignment
        artistField.setBounds(305, 90, 290, 20); // set absolute position and size
        getContentPane().add(artistField); // add to main frame

        //
        // Create track name JTextArea
        //
        nameField = new JTextField();
        nameField.setEditable(false); // non-editable
        nameField.setFont(new java.awt.Font("Calibri", 0, 12)); // set font style
        nameField.setForeground(new Color(105, 105, 105)); // set font color
        nameField.setOpaque(false); // transparent
        nameField.setBackground(new Color(0, 0, 0, 0));
        nameField.setBorder(BorderFactory.createEmptyBorder()); // set empty border
        nameField.setCursor(new Cursor(Cursor.TEXT_CURSOR)); // set cursor style
        nameField.setHorizontalAlignment(JTextField.CENTER); // centered alignment
        nameField.setBounds(305, 110, 290, 40); // set absolute position and size
        getContentPane().add(nameField); // add to main frame

        //
        // Create duration JTextField
        //
        durationField = new JTextField();
        durationField.setEditable(false); // non-editable
        durationField.setFont(new java.awt.Font("Calibri", 0, 12)); // set font style
        durationField.setForeground(new Color(105, 105, 105)); // set font color
        durationField.setOpaque(false); // transparent
        durationField.setBackground(new Color(0, 0, 0, 0));
        durationField.setBorder(BorderFactory.createEmptyBorder()); // set empty border
        durationField.setCursor(new Cursor(Cursor.TEXT_CURSOR)); // set cursor style
        durationField.setHorizontalAlignment(JTextField.CENTER); // centered alignment
        durationField.setBounds(305, 150, 290, 20); // set absolute position and size
        getContentPane().add(durationField); // add to main frame

        //
        // Create stop, play and next labels and set behavior
        //
        // Create labels
        stopLabel = new JLabel();
        playLabel = new JLabel();
        nextLabel = new JLabel();
        // Set image on play icon
        playLabel.setIcon(playIcon);
        // Set labels cursors
        stopLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Create listener and use it to labels
        LabelListener listener = new LabelListener();
        stopLabel.addMouseListener(listener);
        playLabel.addMouseListener(listener);
        nextLabel.addMouseListener(listener);
        // Set absolute position and size
        stopLabel.setBounds(377, 193, 30, 30);
        playLabel.setBounds(432, 187, 40, 40);
        nextLabel.setBounds(492, 193, 30, 30);
        // Add labels to main frame
        getContentPane().add(stopLabel);
        getContentPane().add(playLabel);
        getContentPane().add(nextLabel);

        //
        // Create volume slider and set behavior
        //
        volumeSlider = new JSlider(0, 100, 100);
        volumeSlider.setBounds(350, 254, 224, 15);
        volumeSlider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        volumeSlider.setForeground(Color.BLUE);
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (isMuted) {
                    muteLabel.setIcon(unmuteIcon);
                }
                volumeControl.setValue(volumeSlider.getValue() / 100f);
                isMuted = false;
            }
        });
        getContentPane().add(volumeSlider);

        //
        // Create mute label and set behavior
        //
        muteLabel = new JLabel();
        muteLabel.setBounds(327, 251, 20, 20);
        muteLabel.setIcon(unmuteIcon);
        muteLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        muteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isMuted) {
                    muteLabel.setIcon(unmuteIcon);
                    volumeSlider.setValue(previousVolume);
                    isMuted = false;
                } else {
                    previousVolume = volumeSlider.getValue();
                    muteLabel.setIcon(muteIcon);
                    volumeSlider.setValue(0);
                    isMuted = true;
                }
            }
        });
        getContentPane().add(muteLabel);

        //
        // Create radio buttons and set behavior
        //
        // Create buttons
        tagButton = new JRadioButton();
        artistButton = new JRadioButton();
        // Set tag button selected by default
        tagButton.setSelected(true);
        // Create button group
        buttonGroup = new ButtonGroup();
        // Set absolute position and size
        tagButton.setBounds(315, 23, 40, 20);
        artistButton.setBounds(315, 37, 40, 20);
        // Set "mini" style
        tagButton.putClientProperty("JComponent.sizeVariant", "mini");
        artistButton.putClientProperty("JComponent.sizeVariant", "mini");
        // Add listeners
        tagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                method = "tag";
            }
        });
        artistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                method = "artist";
            }
        });
        // Add buttons to group
        buttonGroup.add(tagButton);
        buttonGroup.add(artistButton);
        // Add to main frame
        getContentPane().add(tagButton);
        getContentPane().add(artistButton);

        //
        // Create image label
        //
        imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, 300, 300);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        getContentPane().add(imageLabel);

        //
        // Create background label
        //
        background = new JLabel();
        background.setBounds(0, 0, 600, 300);
        background.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                "/newUI.png")));
        getContentPane().add(background);

        //
        // Set properties of main JFrame
        //
        setUndecorated(true); // frame is undecorated
        // The mouse listener and mouse motion listener to make frame dragable.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });
        setIconImage(new ImageIcon(getClass().getResource("/windowIcon.png")).getImage());
        setSize(600, 300); // set size of frame
        setTitle("tune!");
        setLocationRelativeTo(null); // center frame on screen
        setResizable(false);
        getContentPane().setLayout(null);
    }

    /**
     * Perform search using TopTracksFinder.getTopTracksFromLastFm(query,
     * method) and create playlist with search results
     *
     * @param query - search query
     * @param method - search method ("tag" or "artist")
     */
    private void search(String query, String method) {
        try {
            playlist = TopTracksFinder.getTopTracksFromLastFm(query, method);
        } catch (IOException | IllegalStateException | IllegalArgumentException e) {
            e.printStackTrace();
            displayError(e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            displayError("there is no such tag or artist");
        }
        // Stop current player
        if (player != null) {
            stop();
        }
        // Create new JLayer instance
        player = new SoundJLayer(playlist);
        // Clear info
        artistField.setText("");
        nameField.setText("Searching complete");
        durationField.setText("");
        imageLabel.setIcon(new ImageIcon()); // empty label
    }

    /**
     * Play playlist
     */
    private void play() {
        if (playlist == null) {
        	displayError("nothing to play");
        } else {
        	player.play(); // Play current playlist
        }
    }

    /**
     * Stop playing and clear info on frame
     */
    private void stop() {
    	player.stop();
        artistField.setText("");
        nameField.setText("");
        durationField.setText("");
        imageLabel.setIcon(new ImageIcon()); // empty label
    }

    /**
     * Display information of current track on frame
     */
    private void displayTrackInfo(Track currentTrack) {
        // Display info
        artistField.setText(currentTrack.getArtist());
        // Cut track name if it is longer than 45 symbols
        if (currentTrack.getName().length() > 45) {
            nameField.setText(currentTrack.getName().substring(0, 45) + "...");
            nameField.setToolTipText(currentTrack.getName());
            UIManager.put("ToolTip.font", new FontUIResource("Calibri", Font.PLAIN, 11));
        } else {
            nameField.setText(currentTrack.getName());
        }
        durationField.setText(TimeUtility.convert(currentTrack.getDuration()));
        // Get and display track image
        try {
            BufferedImage image = ImageIO.read(new URL(currentTrack.getImageURL()));
            imageLabel.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            imageLabel.setIcon(new ImageIcon()); // empty label
        }
    }

    /**
     * Display error message
     *
     * @param error - string with error to display
     */
    private void displayError(String error) {
        artistField.setText("Oops!");
        nameField.setText(error);
        durationField.setText("");
    }

    /**
     * Implement PlayerListener method
     */
    @Override
    public void PlayerStarts() {
        currentTrack = player.getCurrentTack();
        displayTrackInfo(currentTrack);
        isPlaying = true;
    }

    /**
     * Implement PlayerListener method
     */
    @Override
    public void PlayerStops() {
        isPlaying = false;
    }

    public static void main(String... args) {
        // enable Nimbus Look and Feel
        try {
            // Check if Nimbus is supported and get its classname
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            try {
                // If Nimbus is not available, set to the default Java (metal)
                // look and feel
                UIManager.setLookAndFeel(UIManager
                        .getCrossPlatformLookAndFeelClassName());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        final AppGUI frame = new AppGUI();
        SoundJLayer.addListener(frame); // Frame listens to JLayer player
		/* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });

    }

    /**
     * Listener of play, stop and next labels
     *
     * @author Maxim
     *
     */
    private class LabelListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            // determine which button has fired the event
            JLabel source = (JLabel) e.getSource();
            if (source == stopLabel) {
                if (isPlaying) {
                	stop();
                }
            } else if (source == playLabel) {
                if (!isPlaying) {
                	play();
                }
            } else if (source == nextLabel) {
                if (isPlaying) {
                	play();
                }
            }
        }
    }

    private JLabel closeLabel; // close label
    private JTextField searchField; // search Field
    private JLabel searchLabel; // search icon
    private JTextField artistField; // track artist info field
    private JTextField nameField; // track name info field
    private JTextField durationField; // track duration info field
    private JLabel stopLabel; // stop button
    private JLabel playLabel; // play button
    private JLabel nextLabel; // next button
    private JRadioButton tagButton;
    private JRadioButton artistButton;
    private ButtonGroup buttonGroup;
    private JLabel imageLabel; // track image label
    private JLabel background; // background
    private JSlider volumeSlider; // volume slider
    private JLabel muteLabel; // muteLabel

    public final static String DEFAULT_SEARCH_TEXT = "enter tag or artist to search";
    private final Icon unmuteIcon = new ImageIcon(getClass().getResource("/unmute.png"));
    private final Icon muteIcon = new ImageIcon(getClass().getResource("/mute.png"));
    private final Icon playIcon = new ImageIcon(getClass().getResource("/play.png"));
    private VolumeControl volumeControl = new VolumeControl();
    private int previousVolume; // hold volume level when muted
    private boolean isMuted = false;
    private boolean isPlaying;
    private TopTracks playlist;
    private SoundJLayer player;
    private Track currentTrack;
    private String method = "tag"; //tag by default
    private Point point = new Point(); // point to get position of frame when drag

}
