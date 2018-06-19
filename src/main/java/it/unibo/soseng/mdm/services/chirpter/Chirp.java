package it.unibo.soseng.mdm.services.chirpter;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * The Class Chirp, used to post a Chirp in Chirpter.
 * @author Michele Contu
 * @author Mirko Zichichi
 */
@SuppressWarnings("deprecation")
public class Chirp {
	
	/** The chirp message. */
	private String chirpMessage;
		
	/** The user token. */
	private String token;
	
	/** The optional event ID. */
	private int eventID;
	
	/**
	 * Instantiates a new chirp.
	 *
	 * @param chirpMessage the chirp message
	 * @param token the token
	 */
	public Chirp(String chirpMessage, String token) {
		super();
		this.chirpMessage = chirpMessage;
		this.token = token;
		this.eventID = -1;
	}
	
	/**
	 * Instantiates a new chirp for an event.
	 *
	 * @param chirpMessage the chirp message
	 * @param token the token
	 * @param eventID the event ID
	 */
	public Chirp(String chirpMessage, String token, int eventID) {
		super();
		this.chirpMessage = chirpMessage;
		this.token = token;
		this.eventID = eventID;
	}

	/**
	 * Post.
	 *
	 * @throws Exception the exception
	 */
	public void post() throws Exception {
		@SuppressWarnings({ "resource" })
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest;
		if(eventID > 0)
			postRequest = new HttpPost("http://lamp/chirpter_api/events/" + eventID + "/chirps/");
		else
			postRequest = new HttpPost("http://lamp/chirpter_api/chirps/");
		
		StringEntity input = new StringEntity("{\"chirp\":\"" + chirpMessage + "\"}");
		input.setContentType("application/json");
		postRequest.setEntity(input);
		postRequest.setHeader(HttpHeaders.AUTHORIZATION, token);
		HttpResponse response = httpClient.execute(postRequest);

		//httpClient.getConnectionManager().shutdown();
		
		if (response.getStatusLine().getStatusCode() != 200 )
			throw new Exception();
	}
	
	/**
	 * Gets the chirp message.
	 *
	 * @return the chirp message
	 */
	public String getChirpMessage() {
		return chirpMessage;
	}

	/**
	 * Sets the chirp message.
	 *
	 * @param chirpMessage the new chirp message
	 */
	public void setChirpMessage(String chirpMessage) {
		this.chirpMessage = chirpMessage;
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
