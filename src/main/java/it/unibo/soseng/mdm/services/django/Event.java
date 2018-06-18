package it.unibo.soseng.mdm.services.django;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

import it.unibo.soseng.mdm.model.Address;

/**
 * The Class Event, used to manage an Event in RegistrationPlatform.
 * @author Michele Contu
 * @author Mirko Zichichi
 */
@SuppressWarnings("deprecation")
public class Event {
	
	/** The token. */
	private String token;
	
	/** The event ID. */
	private int eventID;
	
	/** The name. */
	private String name;

	/** The dates. */
	private String dates;

	/** The subs start. */
	private String subsStart;

	/** The cont deadline. */
	private String contDeadline;

	/** The subs deadline. */
	private String subsDeadline;

	/** The address. */
	private Address address;

	/** The location name. */
	private String locationName;

	/** The max seats. */
	private int maxSeats;

	/** The ticket participant. */
	private double ticketParticipant;

	/** The ticket contributor. */
	private double ticketContributor;

	/** The is open. */
	private boolean isOpen;

	/** The is open contr. */
	private boolean isOpenContr;
	
	/** The available money. */
	private double availableMoney;
	
	/** The available seats. */
	private int availableSeats;

	/**
	 * Instantiates a new event.
	 *
	 * @param token the token
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
	 */
	public Event(String token, String name, String dates, String subsStart, String contDeadline, String subsDeadline,
			Address address, String locationName, int maxSeats, double ticketParticipant, double ticketContributor,
			boolean isOpen, boolean isOpenContr) {
		super();
		this.token = token;
		this.name = name;
		this.dates = dates;
		this.subsStart = subsStart;
		this.contDeadline = contDeadline;
		this.subsDeadline = subsDeadline;
		this.address = address;
		this.locationName = locationName;
		this.maxSeats = maxSeats;
		this.ticketParticipant = ticketParticipant;
		this.ticketContributor = ticketContributor;
		this.isOpen = isOpen;
		this.isOpenContr = isOpenContr;
		
		this.eventID = -1;
		this.availableMoney = 0.0;
	}
	
	/**
	 * Instantiates a new event.
	 *
	 * @param token the token
	 * @param eventID the event ID
	 */
	public Event( String token, int eventID ) {
		super();
		this.token = token;
		this.eventID = eventID;

		this.availableMoney = 0.0;
	}

	/**
	 * Post the event.
	 *
	 * @return the int
	 * @throws Exception the exception
	 */
	public int post() throws Exception {
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
		
		if (response.getStatusLine().getStatusCode() != 201 )
			throw new Exception();
		
		int eventID = -1;
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		JSONObject o = new JSONObject(br.readLine());
		eventID = o.getInt("id");
		
		//httpClient.getConnectionManager().shutdown();
		
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
		
		//httpClient.getConnectionManager().shutdown();
		
		if (response.getStatusLine().getStatusCode() != 200 )
			throw new Exception();
	}
	
	/**
	 * Gets the.
	 *
	 * @throws Exception the exception
	 */
	public void get() throws Exception {
		@SuppressWarnings("resource")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet putRequest = new HttpGet("http://django/events/" + eventID + "/");
		
		putRequest.setHeader(HttpHeaders.AUTHORIZATION, token);
		HttpResponse response = httpClient.execute(putRequest);
		
		//httpClient.getConnectionManager().shutdown();
		
		if (response.getStatusLine().getStatusCode() != 200 )
			throw new Exception();

		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		JSONObject o = new JSONObject(br.readLine());
		
		name = o.getString("name");
		dates = o.getString("dates");
		subsStart = o.getString("subsStart");
		contDeadline = o.getString("contDeadline");
		subsDeadline = o.getString("subsDeadline");
		address = new Address();
		address.setCity(o.getString("city"));
		address.setStreet(o.getString("address"));
		address.setPostalCode(o.getString("cap"));
		locationName = o.getString("location");
		maxSeats = (int) o.getInt("max_seats") ;
		ticketParticipant = o.getDouble("ticket_price");
		ticketContributor = o.getDouble("staff_ticket_price");
		isOpen = o.getBoolean("is_open");
		isOpenContr = o.getBoolean("is_open_contr");
		availableMoney = o.getDouble("available_money");
		availableSeats = o.getInt("available_seats");
	}
	
	/**
	 * Delete.
	 *
	 * @throws Exception the exception
	 */
	public void delete() throws Exception {
		@SuppressWarnings("resource")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpDelete putRequest = new HttpDelete("http://django/events/" + eventID + "/");
		
		putRequest.setHeader(HttpHeaders.AUTHORIZATION, token);
		HttpResponse response = httpClient.execute(putRequest);
		
		//httpClient.getConnectionManager().shutdown();
		
		if (response.getStatusLine().getStatusCode() != 204 )
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

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the dates.
	 *
	 * @return the dates
	 */
	public String getDates() {
		return dates;
	}

	/**
	 * Sets the dates.
	 *
	 * @param dates the new dates
	 */
	public void setDates(String dates) {
		this.dates = dates;
	}

	/**
	 * Gets the subs start.
	 *
	 * @return the subs start
	 */
	public String getSubsStart() {
		return subsStart;
	}

	/**
	 * Sets the subs start.
	 *
	 * @param subsStart the new subs start
	 */
	public void setSubsStart(String subsStart) {
		this.subsStart = subsStart;
	}

	/**
	 * Gets the cont deadline.
	 *
	 * @return the cont deadline
	 */
	public String getContDeadline() {
		return contDeadline;
	}

	/**
	 * Sets the cont deadline.
	 *
	 * @param contDeadline the new cont deadline
	 */
	public void setContDeadline(String contDeadline) {
		this.contDeadline = contDeadline;
	}

	/**
	 * Gets the subs deadline.
	 *
	 * @return the subs deadline
	 */
	public String getSubsDeadline() {
		return subsDeadline;
	}

	/**
	 * Sets the subs deadline.
	 *
	 * @param subsDeadline the new subs deadline
	 */
	public void setSubsDeadline(String subsDeadline) {
		this.subsDeadline = subsDeadline;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Gets the location name.
	 *
	 * @return the location name
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * Sets the location name.
	 *
	 * @param locationName the new location name
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * Gets the max seats.
	 *
	 * @return the max seats
	 */
	public int getMaxSeats() {
		return maxSeats;
	}

	/**
	 * Sets the max seats.
	 *
	 * @param maxSeats the new max seats
	 */
	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
	}

	/**
	 * Gets the ticket participant.
	 *
	 * @return the ticket participant
	 */
	public double getTicketParticipant() {
		return ticketParticipant;
	}

	/**
	 * Sets the ticket participant.
	 *
	 * @param ticketParticipant the new ticket participant
	 */
	public void setTicketParticipant(double ticketParticipant) {
		this.ticketParticipant = ticketParticipant;
	}

	/**
	 * Gets the ticket contributor.
	 *
	 * @return the ticket contributor
	 */
	public double getTicketContributor() {
		return ticketContributor;
	}

	/**
	 * Sets the ticket contributor.
	 *
	 * @param ticketContributor the new ticket contributor
	 */
	public void setTicketContributor(double ticketContributor) {
		this.ticketContributor = ticketContributor;
	}

	/**
	 * Checks if is open.
	 *
	 * @return true, if is open
	 */
	public boolean getIsOpen() {
		return isOpen;
	}

	/**
	 * Sets the open.
	 *
	 * @param isOpen the new open
	 */
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * Checks if is open contr.
	 *
	 * @return true, if is open contr
	 */
	public boolean getIsOpenContr() {
		return isOpenContr;
	}

	/**
	 * Sets the open contr.
	 *
	 * @param isOpenContr the new open contr
	 */
	public void setIsOpenContr(boolean isOpenContr) {
		this.isOpenContr = isOpenContr;
	}

	/**
	 * Gets the available money.
	 *
	 * @return the available money
	 */
	public double getAvailableMoney() {
		return availableMoney;
	}

	/**
	 * Sets the available money.
	 *
	 * @param availableMoney the new available money
	 */
	public void setAvailableMoney(double availableMoney) {
		this.availableMoney = availableMoney;
	}

	/**
	 * Gets the available seats.
	 *
	 * @return the available seats
	 */
	public int getAvailableSeats() {
		return availableSeats;
	}

	/**
	 * Sets the available seats.
	 *
	 * @param availableSeats the new available seats
	 */
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
		
}
