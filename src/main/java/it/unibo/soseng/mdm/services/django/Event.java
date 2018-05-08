package it.unibo.soseng.mdm.services.django;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

import it.unibo.soseng.mdm.model.Address;

/**
 * The Class Event, used to manage an Event in Django.
 * @author Michele Contu
 * @author Mirko Zichichi
 */
@SuppressWarnings("deprecation")
public class Event {
	
	/** The token. */
	private String token;
	
	/** The event ID. */
	private int eventID;
	
	/**
	 * Instantiates a new event.
	 *
	 * @param token the token
	 */
	public Event(String token) {
		//
	}

	/**
	 * Post the event.
	 *
	 * @param name the name
	 * @param dates the dates
	 * @param subsStart the subs start
	 * @param contDeadline the cont deadline
	 * @param subsDeadline the subs deadline
	 * @param address the address
	 * @param locationName the location name
	 * @param maxSeats the max seats
	 * @param ticketParticipant the ticket participant
	 * @param ticketContributor the ticket contributor
	 * @param isOpen the is open
	 * @param isOpenContr the is open contr
	 * @return the int
	 * @throws Exception the exception
	 */
	public int post(String name, String dates, String subsStart, String contDeadline, String subsDeadline, Address address,
			String locationName, int maxSeats, double ticketParticipant, double ticketContributor, boolean isOpen,
			boolean isOpenContr) throws Exception {
		@SuppressWarnings("resource")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost("http://django/events/");
		
		StringEntity input = new StringEntity("{" + 
				"\"name\": \"" + name + "\"," + 
				"\"dates\": " + dates + 
				"\"subsStart\": \"" + subsStart + "\"," + 
				"\"contDeadline\": \"" + contDeadline + "\"," + 
				"\"subsDeadline\": \"" + subsDeadline + "\"," + 					
				"\"city\": \"" + address.getCity() + "\"," + 
				"\"address\": \"" + address.getStreet() + "\"," + 
				"\"cap\": \"" + address.getPostalCode() + "\"," + 
				"\"location\": \"" + locationName + "\"," + 
				"\"max_seats\": \"" + maxSeats + "\"," + 
				"\"ticket_price\": \"" + ticketParticipant + "\"," + 
				"\"staff_ticket_price\": \"" + ticketContributor + "\"," + 
				"\"is_open\": " + isOpen + " ," +
				"\"is_open_contr\": " + isOpenContr +
				"}");
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
	 * Put.
	 *
	 * @param json the json
	 * @throws Exception the exception
	 */
	public void put(String json) throws Exception {
		@SuppressWarnings("resource")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPut putRequest = new HttpPut("http://django/events/" + eventID + "/");
		
		StringEntity input = new StringEntity(json);
		input.setContentType("application/json");
		putRequest.setEntity(input);
		putRequest.setHeader(HttpHeaders.AUTHORIZATION, token);
		HttpResponse response = httpClient.execute(putRequest);
		
		httpClient.getConnectionManager().shutdown();
		
		if (response.getStatusLine().getStatusCode() != 200 )
			throw new Exception();
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
