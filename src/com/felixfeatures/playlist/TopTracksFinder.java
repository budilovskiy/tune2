package com.felixfeatures.playlist;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.felixfeatures.util.JSONUtil;

/**
 * Class provides static methods to search top tracks according to last.fm charts.
 * This is the main creator of playlists in application
 * See last.fm API documentation: http://www.lastfm.ru/api/intro
 *
 */
public class TopTracksFinder {
	
	// Constant variable for the size of last.fm charts. Must be greater than 1;
	private static final int LAST_FM_LIMIT_OF_TRACKS = 100;
	private static final String LAST_FM_API_KEY = "ce021b6cb5cb325de823959093b8854b";
	
	/**
	 * Build request URL to last.fm
	 * See last.fm API documentation
	 * 
	 * @param searchString - search query (e.g. artist name or tag)
	 * @param searchMethod - search method. "tag" or "artist"
	 * @return request URL to last.fm search API 
	 * @throws IllegalArgumentException
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	private static URL getLastFmRequestURL(String searchString, String searchMethod)
			throws IllegalArgumentException, MalformedURLException, UnsupportedEncodingException {
		
		if (searchString.equals("") || searchString == null) {
			throw new IllegalArgumentException("Search query is empty or null");
		}
		
		int averageRequestLength = 400;	// Maximum average request length
		
		searchString = URLEncoder.encode(searchString, "UTF-8");
	        StringBuilder sb = new StringBuilder(averageRequestLength);	// initial capacity of StringBuilder specified by the capacity argument
	        sb.append("http://ws.audioscrobbler.com/2.0/");
	        sb.append("?method=");
	        sb.append(searchMethod + ".gettoptracks");
	        switch (searchMethod) {
	            case "tag":
	                sb.append("&tag=");
	                sb.append(searchString);
	                break;
	            case "artist":
	                sb.append("&artist=");
	                sb.append(searchString);
	                break;
	            default :
	            	throw new IllegalArgumentException("Search method must be \"artist\" or \"tag\"");
	        }
	        sb.append("&limit=");
	        sb.append(LAST_FM_LIMIT_OF_TRACKS);
	        sb.append("&api_key=");
	        sb.append(LAST_FM_API_KEY);
	        sb.append("&format=json");
	        return new URL(sb.toString());
	}
	
	/**
	 * Get top tracks from last.fm by music tag or artist name using API
	 * 
	 * @param searchString - search query (e.g. artist name or tag)
	 * @param searchMethod - search method. "tag" or "artist"
	 * @return TopTracks collection of Track instances
	 * @throws IOException
	 *             from JSONUtil.parse()
	 */
	public static TopTracks getTopTracksFromLastFm(String searchString, String searchMethod) throws IOException {
		
		TopTracks topTracks = new TopTracks(LAST_FM_LIMIT_OF_TRACKS);
		URL requestURL = getLastFmRequestURL(searchString, searchMethod);
		JSONArray tracklist = (JSONArray) JSONUtil.parse(requestURL);

		for (int i = 0; i < tracklist.size(); i++) {
			JSONObject track = (JSONObject) tracklist.get(i);
			JSONObject artist = (JSONObject) track.get("artist");
			JSONObject image = null;
			if (track.get("image") != null) {
				JSONArray images = (JSONArray) track.get("image");
				for (int j = 0; j < images.size(); j++) {
					image = (JSONObject) images.get(j);
					if (image.get("size").equals("extralarge")) {
						break;
					}
				}
			}
			String artistName = artist.get("name").toString();
			String trackName = track.get("name").toString();
			String trackduration = (track.get("duration").toString().equals("")) ? "0" : track.get("duration").toString();
			String imageURL = (image == null) ? null : image.get("#text").toString();
			topTracks.add(new Track(artistName, trackName, trackduration, imageURL));
		}
		return topTracks;
		
	}
}
