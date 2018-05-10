package it.unibo.soseng.mdm.services.chirpter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

/**
 * The Class Event, used to create an Event in Chirpter.
 * @author Michele Contu
 * @author Mirko Zichichi
 */
@SuppressWarnings("deprecation")
public class Event {
	
	/** The title. */
	private String title;
	
	/** The user token. */
	private String token;
	
	/** The event ID. */
	private int eventID;

	/**
	 * Instantiates a new event.
	 *
	 * @param title the title
	 * @param token the token
	 */
	public Event(String title, String token) {
		super();
		this.title = title;
		this.token = token;
		this.eventID = -1;
	}

	/**
	 * Post a new event.
	 *
	 * @return the eventID
	 * @throws Exception the exception
	 */
	public int post() throws Exception {
		@SuppressWarnings("resource")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost("http://lamp/chirpter_api/events/");
		
		StringEntity input = new StringEntity("{\"eventTitle\":\"" + title + "\"}");
		input.setContentType("application/json");
		postRequest.setEntity(input);
		postRequest.setHeader(HttpHeaders.AUTHORIZATION, token);
		HttpResponse response = httpClient.execute(postRequest);
		
		if (response.getStatusLine().getStatusCode() != 200 )
			throw new Exception();
		
		int eventID = -1;
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		while ((output = br.readLine()) != null) {
			if (output.contains("eventID")) {
				JSONObject o = new JSONObject(output);
				eventID = Integer.parseInt((String) o.get("eventID"));
			}
		}

		httpClient.getConnectionManager().shutdown();
		
		this.eventID = eventID;
		return eventID;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the event ID.
	 *
	 * @return the event ID
	 */
	public int getEventID() {
		return eventID;
	}

	/**
	 * Sets the event ID.
	 *
	 * @param eventID the new event ID
	 */
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	
	
}
