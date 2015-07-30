/**
 * Analog of the ArrayList
 */
package com.felixfeatures.playlist;

import java.io.IOException;
import java.util.Random;

import org.json.simple.parser.ParseException;

/**
 * @author Maxim
 *
 */
public class TopTracks {
	
	private int size;
	private Track[] array;
	private int current = 0;
	
	public TopTracks(String searchString, String searchMethod) throws IOException, ParseException {
		array = TopTracksFinder.getTopTracksFromLastFm(searchString, searchMethod);
	}
	
	public Track getTrack(int index) {
		return array[index];
	}
	
	public Track getRandomTrack() {
		int index = new Random().nextInt(array.length - 1);
		return array[index];
	}
	
	public void add(Track track) {
		array[current++] = track;
		size++;
	}
	
	public int size() {
		return size;
	}
	
}
