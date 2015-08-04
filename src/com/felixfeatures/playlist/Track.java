package com.felixfeatures.playlist;

import java.io.IOException;

/**
 * Class provides simple bean of Track representation
 */
public class Track {
	private String artist; // track artist
	private String name; // track name
	private int duration; // track duration in seconds
	private String imageURL; // URL of track image location
	private String URL; // URL of track location

	/**
	 * Constructor have default access modifier to access it only within package
	 * 
	 * @param artist
	 * @param name
	 * @param duration
	 * @param imageURL
	 */
	Track(String artist, String name, String duration, String imageURL) {
		this.artist = artist;
		this.name = name;
		this.duration = Integer.parseInt(duration);
		this.imageURL = imageURL;
	}

	// Getters and setters
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setArtistAndName(String artist, String name) {
		this.artist = artist;
		this.name = name;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * Automatically set URL of Track instance using the static method of
	 * TrackURLFinder.getURLfromVK(String fullTrackName, double duration)
	 * and returns this value.
	 * 
	 * @return URL of Track instance
	 * @throws IOException
	 *             if it is not possible to get track URL
	 */
	public String getURL() throws IOException {
		if (URL == null) {
			this.URL = TrackURLFinder.getURLfromVK(this.toString(), duration);
		}
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	@Override
	public String toString() {
		return artist + " - " + name;
	}

	public String fullInfoToString() {
		return artist + " - " + name + " : " + duration + " seconds"
				+ "\nURL: " + URL + "\nimage: " + imageURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artist == null) ? 0 : artist.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(duration);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (!(obj instanceof Track)) {
			return false;
		}
		Track other = (Track) obj;
		if (artist == null) {
			if (other.artist != null) {
				return false;
			}
		} else if (!artist.equals(other.artist)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (Double.doubleToLongBits(duration) != Double
				.doubleToLongBits(other.duration)) {
			return false;
		}
		return true;
	}

}
