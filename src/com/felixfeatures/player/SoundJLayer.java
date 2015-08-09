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

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import com.felixfeatures.playlist.TopTracks;
import com.felixfeatures.playlist.Track;

/**
 * Manage playback with javazoom.jl.player.Player;
 */
public class SoundJLayer {

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
                for (PlayListener listener : listeners) {
                    listener.PlayerStops();
                }
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
            System.out.println(currentTrack.fullInfoToString());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            // Wait 0.3 second
            // vk.com limits 3 requests in second
            try {
                Thread.sleep(350);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        playTrack(stringTrackURL);
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
        }

        // Run playing in separate thread 
        audioThread = new Thread("audioThread") {
            @Override
            public void run() {
                try {
                    playing = true;
                    if (trackURL != null) {
                        // Notify listeners
                        for (PlayListener listener : listeners) {
                            listener.PlayerStarts();
                        }
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
