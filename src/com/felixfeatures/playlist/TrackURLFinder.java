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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Provides static methods that perform search of the URL of tracks location at
 * vk.com
 */
public class TrackURLFinder {

	private final static String VK_API_METHOD = "audio.search";
	private final static String VK_API_TOKEN = "8***3";

	/**
	 * Builds and returns URL searching request of the given track to vk.com.
	 * See VK API documentation https://vk.com/pages?oid=-1&p=audio.search
	 * 
	 * @param searchQuery
	 *            - full name of the track in "Artist - Name" String format
	 * @return searching URL request to vk.com
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 */
	private static URL getVKRequestURL(String searchQuery)
			throws UnsupportedEncodingException, MalformedURLException {
		int averageRequestLength = 400;
		// Building request URL to vk.com API service
		StringBuilder sb = new StringBuilder(averageRequestLength); // 
		sb.append("https://api.vk.com/method/");
		sb.append(VK_API_METHOD);
		sb.append("?q=");
		// converting search query to UTF-8
		sb.append(URLEncoder.encode(searchQuery, "UTF-8"));
		sb.append("&access_token=");
		sb.append(VK_API_TOKEN);
		// Return request URL
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
		return (JSONArray) jsonResponse.get("response");
	}

	/**
	 * Perform a request to vk.com, receive response in JSON format and parse it
	 * to get URL of the first track in JSON document.
	 * 
	 * @param fullTrackName
	 *            - String representation of track: "Artist - Name"
	 * @return URL of the location of the given track
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String getURLfromVK(String fullTrackName) throws IOException,
			ParseException {

		URL requestURL = getVKRequestURL(fullTrackName);
		JSONArray mp3list = (JSONArray) parse(requestURL);

		JSONObject mp3 = (JSONObject) mp3list.get(1); // first track from VK response
		return new String(mp3.get("url").toString());
	}

	/**
	 * Perform a request to vk.com, receive response in JSON format and parse it
	 * to get URL of the first track with given duration.
	 * 
	 * @param fullTrackName
	 *            - String representation of track: "Artist - Name"
	 * @param duration
	 *            - duration of track
	 * @return URL of the location of the given track
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String getURLfromVK(String fullTrackName, int duration)
			throws IOException, ParseException {

		URL requestURL = getVKRequestURL(fullTrackName);
		JSONArray mp3list = (JSONArray) parse(requestURL);

		String result = null;
		for (int i = 1; i < mp3list.size(); i++) {
			JSONObject mp3 = (JSONObject) mp3list.get(i);
			int vkDuration = Integer.parseInt(mp3.get("duration").toString());
			if ((duration >= vkDuration - 1) && (duration <= vkDuration + 1)) { // (+- 1 second)
				result = mp3.get("url").toString();
				break;
			}
		}
		return result;
	}

}
