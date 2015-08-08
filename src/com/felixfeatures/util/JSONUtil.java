package com.felixfeatures.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Provides useful static utilities to work with JSON format
 *
 */
public class JSONUtil {

	/**
	 * Parse JSON data from given URL
	 * 
	 * @param url
	 *            - URL, where the JSON data is located
	 * @return JSONArray parsed from JSON response from given URL
	 * @throws IOException
	 *             if method is unable to open or close connection to the given
	 *             URL
	 * @throws IllegalArgumentException
	 *             if the parameter URL is not linked to last.fm or vk.com
	 */
	public static JSONArray parse(URL url) throws IOException,
			IllegalArgumentException {

		// get JSON data
		String response = null;
		try {
			response = getJSON(url);
		} catch (IOException e) {
			throw new IOException("Unable to open connection to " + url);
		}
		
		// Parse JSON data to get JSONArray according to URL (last.fm or vk.com
		// JSON response)
		JSONParser parser = new JSONParser();

		JSONObject jsonResponse = null;
		JSONObject toptracks = null;
		JSONArray res = null;
		try {
			jsonResponse = (JSONObject) parser.parse(response);
			if (url.toString().contains("ws.audioscrobbler.com/2.0/?method")) { // last.fm API
				toptracks = (JSONObject) jsonResponse.get("toptracks");
				try {
				res = (JSONArray) toptracks.get("track");
				} catch (NullPointerException e) {
					throw new IllegalStateException("Problem while searching");
				}
			} else if (url.toString().contains("api.vk.com/method/audio.search")) { // vk.com API
				res = (JSONArray) jsonResponse.get("response");
			} else {
				throw new IllegalArgumentException("Illegal URL: " + url
						+ "\nURL must contain JSON data form vk.com or last.fm"
						+ "\nSee API documentation of vk.com or last.fm");
			}
		} catch (NullPointerException | ParseException e) {
			System.out.println("Problem while parsing JSON response from "
					+ url);
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Get JSON data from specified URL
	 * 
	 * @param url
	 *            - an URL of JSON data
	 * @return String representation of JSON from given URL
	 * @throws IOException
	 */
	private static String getJSON(URL url) throws IOException {
		// Open connection
		HttpURLConnection connection;
		
		connection = (HttpURLConnection) url.openConnection();

		// Receive JSON data with search results
		InputStream responseIS = null;
		InputStreamReader is = null;
		BufferedReader reader = null;
		StringBuilder sb = null;
		try {
			responseIS = connection.getInputStream();
			is = new InputStreamReader(responseIS, "UTF-8");
			reader = new BufferedReader(is);
			sb = new StringBuilder();
	
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} finally {
			// Close InputStream and connection
			if (is != null) {
				is.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}

		return sb.toString();
	}

}
