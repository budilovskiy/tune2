package com.felixfeatures.player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.felixfeatures.gui.AppGUI;
import com.felixfeatures.playlist.TopTracks;
import com.felixfeatures.playlist.Track;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * Manage playback with javazoom.jl.player.Player;
 */
public class SoundJLayer {
	
    private static final Logger LOGGER = Logger.getLogger(AppGUI.class.getName());
    private FileHandler fh;

    private static List<PlayListener> listeners = new ArrayList<>();
    private TopTracks playlist;
    private Track currentTrack;

    private static HttpURLConnection connection;
    private static String stringTrackURL;
    private static InputStream is;
    private static BufferedInputStream bis;
    private static Thread audioThread;
    private static AdvancedPlayer player;
    private static boolean playing = true;

    /**
     * Constructor of SoundJLayer with new playlist
     *
     * @param playlist
     */
    public SoundJLayer(TopTracks playlist) {
        this.playlist = playlist;
        
        try {
		fh = new FileHandler("log.txt");
		fh.setLevel(Level.ALL);
	    	fh.setFormatter(new SimpleFormatter());
	    	LOGGER.addHandler(fh);
	} catch (SecurityException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }

    /**
     * Registering listeners
     *
     * @param listenerToAdd
     */
    public static void addListener(PlayListener listenerToAdd) {
        listeners.add(listenerToAdd);
    }

    /**
     * Return current playing track
     *
     * @return current playing Track
     */
    public Track getCurrentTack() {
        return currentTrack;
    }

    /**
     * Stop playing current track and close connection
     */
    public void stop() {
        if (player != null) {
            try {
                playing = false;
                player.close();
                bis.close();
                is.close();
                connection.disconnect();
                player = null;
                listeners.stream().forEach((playListener) -> playListener.playerStops());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Play current playlist
     */
    public void play() {
        stop();
        try {
            currentTrack = playlist.getRandomTrack(); // Get random track
            stringTrackURL = currentTrack.getURL();
            LOGGER.log(Level.INFO, "> {0}", currentTrack.fullInfoToString() + "\r\n");
            playTrack(stringTrackURL);
        } catch (IOException | NullPointerException e) {	// Can not get track URL or playlist is null
        	LOGGER.log(Level.WARNING, "Exception at track {0}", currentTrack + " : " + e);
        	LOGGER.log(Level.WARNING, "Exception :", e);
        	if (!playlist.isEmpty()) {
        		play(); // Try to get URL of next track
        	} else {
        		LOGGER.log(Level.WARNING, "Playlist is empty {0}", playlist);
        	}
        }
    }

    /**
     * Play single track from playlist
     *
     * @param trackURL - String URL of Track
     */
    private void playTrack(final String trackURL) {
        // Open connection and create BufferedInputStream with audio data
        if (trackURL != null) {
            try {
                connection = (HttpURLConnection) new URL(trackURL).openConnection();
                is = connection.getInputStream();
                bis = new BufferedInputStream(is);
            } catch (MalformedURLException | ConnectException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            // Wait ~0.3 second because
            // vk.com API limits 3 requests in second
            try {
            	System.out.println("Waiting 350 ms...");
                Thread.sleep(350);
                play(); // Play next track
                return; // Exit from method
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                play(); // Play next track without waiting
                return; // Exit from method
            }
        }

        // Run playing in separate thread 
        audioThread = new Thread("audioThread") {
            @Override
            public void run() {
                try {
                	playing = true;
                    if (trackURL != null) {
                        // Notify listeners
                        listeners.stream().forEach((playListener) -> playListener.playerStarts());
                        // create player and start playback
                        player = new AdvancedPlayer(bis);
                        player.play();
                    }
                } catch (JavaLayerException ex) {
                    // Skip track
                    ex.printStackTrace();
                }
                // Play next track from playlist
                if (playing) {
                    play();
                }
            }
        };
        audioThread.start();
        playing = false;
    }
}
