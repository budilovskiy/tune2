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
import java.util.logging.Level;
import java.util.logging.Logger;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import com.felixfeatures.playlist.TopTracks;
import com.felixfeatures.playlist.Track;

public class SoundJLayer {
	
	private static List<PlayListener> listeners = new ArrayList<>();
    private TopTracks playlist;
    private Track currentTrack;
    private URL url;
    
    private static HttpURLConnection connection;
    private static String stringTrackURL;
    private static InputStream is;
    private static BufferedInputStream bis;
    private static Thread audioThread;
    private static Player player;
    private static boolean playing = true;

    /**
     * Constructor of SoundJLayer with new playlist
     *
     * @param topTracks
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
     * Plays random track of playlist
     *
     * @param playlist - collection of Tracks to play
     */
    public void play() {
    	System.out.println(playlist);
        stop();
		try {
			currentTrack = playlist.getRandomTrack();
			stringTrackURL = currentTrack.getURL();
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			// Wait 1 second
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		playTrack(stringTrackURL);
    }

    /**
     * Plays single track from playlist
     *
     * @param trackURL - URL of Track
     */
    private void playTrack(final String trackURL) {
        if (trackURL != null) {
            try {
                url = new URL(trackURL);
                connection = (HttpURLConnection) url.openConnection();
                is = connection.getInputStream();
                bis = new BufferedInputStream(is);
            } catch (MalformedURLException | ConnectException ex) {
                Logger.getLogger(SoundJLayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SoundJLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        audioThread = new Thread("audioThread") {
            @Override
            public void run() {
                try {
                    playing = true;
                    if (trackURL != null) {
                        for (PlayListener listener : listeners) {
                            listener.PlayerStarts();
                        }
                        player = new Player(bis);
                        player.play();
                    }
                } catch (JavaLayerException ex) {
                    // skip track if JavaLayerException
                    Logger.getLogger(SoundJLayer.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (playing) {
                    play();
                }
            }
        };
        audioThread.start();
        playing = false;
    }
}
