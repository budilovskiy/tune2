package com.felixfeatures.playlist;
import java.util.Random;

/**
 * A TopTracks represents a group of tracks
 * 
 * @author Maxim
 *
 */
public class TopTracks {
	
	private static final int DEFAULT_CAPACITY = 100;
	
	private int size = 0;
	private Track[] array;
	
	public TopTracks() {
		array = new Track[DEFAULT_CAPACITY];
	}
	
	public TopTracks(int capacity) {
		array = new Track[capacity];
	}
	
	public Track getTrack(int index) {
		return array[index];
	}
	
	public Track getRandomTrack() {
		int index = new Random().nextInt(array.length - 1);
		return array[index];
	}
	
	// make boolean
	public void add(Track track) {
		array[size++] = track;
	}
	
	public int size() {
		return size;
	}
	
	// написать итератор, toString, Generics
	// имплементить Collection? или написать свой вариант списка на основе массива
	
}
