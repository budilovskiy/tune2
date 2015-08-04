package com.felixfeatures.playlist;

import java.util.Arrays;
import java.util.Random;

/**
 * A TopTracks represents a simple collection of tracks
 * based on the array of elements of Track type
 * 
 */
public class TopTracks {

	// class constant for default size
	private static final int DEFAULT_CAPACITY = 100;

	// instance variables
	// tracks store the elements of the list and
	// may have extra capacity
	private Track[] tracks;
	private int size;

	/**
	 * Default constructor initialize capacity of array to default value. Have 
	 * default access modifier to access it only within package.
	 */
	TopTracks() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor to allow user to specify initial capacity. Creates an empty 
	 * list. Have default access modifier to access it only within package.
	 * 
	 * @param capacity > 0
	 */
	TopTracks(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException(
					"Violation of precondition. TopTracks(int capacity):"
						+ "capacity must be greater than 0. Value of capacity: "
						+ capacity);
		}
		tracks = new Track[capacity];
		size = 0;
	}

	/**
	 * Retrieve an element from the collection based on index.
	 * 
	 * @param index (0 <= index < size)
	 * @return element of collection by given index
	 */
	public Track getTrack(int index) {
		if (0 > index && index > size - 1) {
			throw new IllegalArgumentException("Failed precondition get. "
					+ "index is out of bounds. Value of index: " + index);
		}
		return tracks[index];
	}

	/**
	 * Retrieve a random element from the collection.
	 * 
	 * @return random element of collection
	 */
	public Track getRandomTrack() {
		int index = new Random().nextInt(size);
		return tracks[index];
	}

	/**
	 * Add track to the end of this collection. Size of the collection increases
	 * by 1.
	 * 
	 * @param track
	 *            - The value to add to the end of this collection.
	 * @return true if element is added successfully
	 */
	public boolean add(Track track) {
		if (size > tracks.length) {
			System.out.println("Overflow. TopTracks.add(Track track):"
						+ "Size  of collection exceeded it's capacity. Value of collection: "
						+ tracks.length + "Size: " + size);
			return false;
		}
		tracks[size++] = track;
		return true;
	}

	/**
	 * Remove an element from the list based on position. Elements with a
	 * position greater than index are shifted to the left. (One subtracted from
	 * their position.)
	 * 
	 * @param index - (0 <= index < size)
	 * @return true if element is removed successfully
	 */
	public Track remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		Track removed = tracks[index];

		for (int i = index; i < size - 1; i++) {
			tracks[i] = tracks[i + 1];
		}

		tracks[size - 1] = null;
		size--;
		return removed;
	}

	/**
	 * Returns the size of the collection.
	 * 
	 * @return the size of the collection
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks if the list is empty.
	 * 
	 * @return true if collection is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("size: " + size + ", tracks: [");
		for (int i = 0; i < size; i++) {
			sb.append((i == size - 1) ? tracks[i] : (tracks[i] + ", "));
		}
		sb.append("]");
		return new String(sb);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + size;
		result = prime * result + Arrays.hashCode(tracks);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TopTracks)) {
			return false;
		}
		TopTracks other = (TopTracks) obj;
		
		boolean result = (this.size == other.size);
		int i = 0;
		while (i < size && result) {
			result = (this.tracks[i].equals(other.tracks[i]));
		}

		return result;
	}

}
