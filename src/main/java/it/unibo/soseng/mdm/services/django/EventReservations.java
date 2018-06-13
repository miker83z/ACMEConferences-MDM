package it.unibo.soseng.mdm.services.django;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.camunda.bpm.engine.impl.util.json.JSONArray;
import org.camunda.bpm.engine.impl.util.json.JSONObject;


/**
 * The Class EventReservations, used to manage an EventReservations in RegistrationPlatform.
 * @author Michele Contu
 * @author Mirko Zichichi
 */
@SuppressWarnings("deprecation")
public class EventReservations {
	
	/** The token. */
	private String token;
	
	/** The event ID. */
	private int eventID;
	
	/**  The list of reservations. */
	private List<EventReservation> eventReservations;
	
	/**
	 * Instantiates a new event reservations.
	 *
	 * @param token the token
	 * @param eventID the event ID
	 */
	public EventReservations(String token, int eventID) {
		super();
		this.token = token;
		this.eventID = eventID;
	}

	/**
	 * Get.
	 *
	 * @throws Exception the exception
	 */
	public void get() throws Exception {
		@SuppressWarnings("resource")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet putRequest = new HttpGet("http://django/events/" + eventID + "/user_reservations/");
		
		putRequest.setHeader(HttpHeaders.AUTHORIZATION, token);
		HttpResponse response = httpClient.execute(putRequest);
		
		//httpClient.getConnectionManager().shutdown();
		
		if (response.getStatusLine().getStatusCode() != 200 )
			throw new Exception();

		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		JSONArray o = new JSONArray(br.readLine());
		eventReservations = new ArrayList<EventReservation>();
		for (int i = 0; i < o.length(); i++) {
			JSONObject tmp = o.getJSONObject(i);
			eventReservations.add( new EventReservation(tmp.getInt("id"), tmp.getInt("event"), tmp.getInt("user"), tmp.getString("bank_user"), tmp.getBoolean("is_staff")) );
		}
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

	/**
	 * Gets the event reservations.
	 *
	 * @return the event reservations
	 */
	public List<EventReservation> getEventReservations() {
		return eventReservations;
	}

	/**
	 * Sets the event reservations.
	 *
	 * @param eventReservations the new event reservations
	 */
	public void setEventReservations(List<EventReservation> eventReservations) {
		this.eventReservations = eventReservations;
	}
		
}
