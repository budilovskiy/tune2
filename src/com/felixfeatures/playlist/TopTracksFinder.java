/**
 * Receives top tracks from last.fm API service
 */
package com.felixfeatures.playlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Maxim
 *
 */
public class TopTracksFinder {
	
	private static final int LAST_FM_LIMIT_OF_TRACKS = 100;
	private static final String LAST_FM_API_KEY = "c***b";


	private static URL getLastFmRequestURL(String searchString, String searchMethod) throws MalformedURLException, UnsupportedEncodingException {
		int averageRequestLength = 400;
		searchString = URLEncoder.encode(searchString, "UTF-8");
        StringBuilder sb = new StringBuilder(averageRequestLength);
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
        }
        sb.append("&limit=");
        sb.append(LAST_FM_LIMIT_OF_TRACKS);
        sb.append("&api_key=");
        sb.append(LAST_FM_API_KEY);
        sb.append("&format=json");
        System.out.println(sb);
        return new URL(sb.toString());
	}
	
	private static JSONArray parse(URL url) throws IOException, ParseException {

		// Open connection
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// Receive JSON as String with search results
		InputStream responseIS = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				responseIS, "UTF-8"));
		StringBuilder sb = new StringBuilder();

		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		String result = sb.toString();

		// Parse JSON to get track URL according to track duration
		JSONParser parser = new JSONParser();
		JSONObject jsonResponse = (JSONObject) parser.parse(result);
		JSONObject toptracks = (JSONObject) jsonResponse.get("toptracks");
		return (JSONArray) toptracks.get("track");
	}

	public static Track[] getTopTracksFromLastFm(String searchString, String searchMethod) throws IOException, ParseException {
		URL requestURL = getLastFmRequestURL(searchString, searchMethod);
		JSONArray tracklist = (JSONArray) parse(requestURL);
		Track[] topTracks = new Track[LAST_FM_LIMIT_OF_TRACKS];
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
			String imageURL = (image == null) ? "" : image.get("#text").toString();
			topTracks[i] = new Track(artist.get("name").toString(), track.get("name").toString(), track.get("duration").toString(), imageURL);
		}
		return topTracks;
	}
	
}
