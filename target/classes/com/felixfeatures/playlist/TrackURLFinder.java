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
 * Provides static methods to perform search of the URL of tracks location at
 * vk.com using API. See VK API documentation
 * https://vk.com/pages?oid=-1&p=audio.search
 */
public class TrackURLFinder {

	// Constant variable that holds search method value
	private final static String VK_API_METHOD = "audio.search";
	private final static String VK_API_TOKEN = "827aa5b99cf0efd1965b7deb751276c5010ad2ca301307e2f49ac5c404f0a3cdf6d9bd4dfc0fa30011a13";

	/**
	 * Builds and returns URL searching request of the given track to vk.com
	 * API.
	 * 
	 * @param searchQuery
	 *            - full name of the track in "Artist - Name" String format
	 * @return searching URL request to vk.com
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 *             from JSONUtil.parse()
	 */
	private static URL getVKRequestURL(String searchQuery)
			throws UnsupportedEncodingException, MalformedURLException {
		// average maximum length of vk.com request
		int averageRequestLength = 400;
		// Building request URL to vk.com API service
		StringBuilder sb = new StringBuilder(averageRequestLength);
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

	/**
	 * Perform a request to vk.com, receive response in JSON format and parse it
	 * to get URL of the first track in JSON document. Have default access
	 * modifier to access it only within package
	 * 
	 * @param fullTrackName
	 *            - String representation of track: "Artist - Name"
	 * @return URL(in String) of the location of the given track
	 * @throws IOException
	 */
	static String getURLfromVK(String fullTrackName) throws IOException {

		URL requestURL = getVKRequestURL(fullTrackName);

		JSONArray mp3list = (JSONArray) JSONUtil.parse(requestURL);

		JSONObject mp3 = (JSONObject) mp3list.get(1); // first track from VK response
		return new String(mp3.get("url").toString());
	}

	/**
	 * Perform a request to vk.com, receive response in JSON format and parse it
	 * to get URL of the first track with given duration. If track duration was
	 * set to 0, method gets first track from vk.com API. Method have default
	 * access modifier to access it only within package
	 * 
	 * @param fullTrackName
	 *            - String representation of track: "Artist - Name"
	 * @param duration
	 *            - duration of track
	 * @return URL(in String) of the location of the given track with given
	 *         duration
	 * @throws IOException
	 *             from JSONUtil.parse()
	 */
	@Deprecated
	static String getURLfromVK(String fullTrackName, int duration)
			throws IOException {

		URL requestURL = getVKRequestURL(fullTrackName);

		// Wait 1/3 second, because vk.com API has a limit of 3 requests in second
		try {
			Thread.sleep(350);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONArray mp3list = (JSONArray) JSONUtil.parse(requestURL);

		String result = null;
		for (int i = 1; i < mp3list.size(); i++) {
			JSONObject mp3 = (JSONObject) mp3list.get(i);
			int vkDuration = Integer.parseInt(mp3.get("duration").toString());
			if (duration != 0) {
				if ((duration >= vkDuration - 1) && (duration <= vkDuration + 1)) { // (+- 1 second)
					result = mp3.get("url").toString();
					break;
				}
			} else {
				result = mp3.get("url").toString();
				break;
			}
		}
		return result;
	}

	/**
	 * Perform a request to vk.com, receive response in JSON format and parse it
	 * to get URL of the first track with given duration. If track duration was
	 * set to 0, method get first track from vk.com API and set it's duration
	 * from this response. Method have default access modifier to access it only
	 * within package
	 * 
	 * @param track
	 *            - the Track instance
	 * @param duration
	 *            - duration of track
	 * @return URL(in String format) of the location of the given track with given
	 *         duration
	 * @throws IOException
	 *             from JSONUtil.parse()
	 */
	static String getURLfromVK(Track track) throws IOException {

		URL requestURL = getVKRequestURL(track.toString());

		// Wait 1/3 second, because vk.com API has a limit of 3 requests in second
		try {
			Thread.sleep(350);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONArray mp3list = (JSONArray) JSONUtil.parse(requestURL);

		String result = null;
		for (int i = 1; i < mp3list.size(); i++) {
			JSONObject mp3 = (JSONObject) mp3list.get(i);
			int duration = track.getDuration();
			int vkDuration = Integer.parseInt(mp3.get("duration").toString());
			if (duration != 0) {
				if ((duration >= vkDuration - 1) && (duration <= vkDuration + 1)) { // (+- 1 second)
					result = mp3.get("url").toString();
					break;
				}
			} else {
				result = mp3.get("url").toString();
				track.setDuration(vkDuration);
				// Updating track info from search results
				track.setArtistAndName(mp3.get("artist").toString(), mp3.get("title").toString());
				break;
			}
		}
		return result;
	}

}
