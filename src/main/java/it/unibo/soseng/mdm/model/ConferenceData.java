package it.unibo.soseng.mdm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

/**
 * The Class ConferenceData, used to store the conference data.
 * @author Mirko Zichichi
 */
public class ConferenceData {

	/** The title. */
	private String title;
	
	/** The dates. */
	private List<Date> dates = new ArrayList<Date>();
	
	/** The ticket price. */
	private double ticketPrice;
	
	/** The All-Inclusive flag. */
	private boolean isAllin;
	
	/** The location if it is All-Inclusive. */
	private String allinLocation;
	
	/** The expected attendance if it is All-Inclusive. */
	private int allinExpectedAttendance;
	
	/** The address if it is Standard. */
	private Address stdAddress;
	
	/** The max participants value if is Standard. */
	private int stdMaxParticipants;
	
	/**
	 * Instantiates a new conference data.
	 */
	public ConferenceData() {
		//
	}
	
	/**
	 * Instantiates a new conference data.
	 *
	 * @param title the title
	 * @param dates the dates
	 * @param ticketPrice the ticket price
	 * @param isAllin the is allin
	 * @param allinLocation the allin location
	 * @param allinExpectedAttendance the allin expected attendance
	 * @param stdAddress the std address
	 * @param stdMaxPartecipants the std max partecipants
	 */
	public ConferenceData(String title, List<Date> dates, double ticketPrice, boolean isAllin, String allinLocation,
			int allinExpectedAttendance, Address stdAddress, int stdMaxPartecipants) {
		this.title = title;
		this.dates = dates;
		this.ticketPrice = ticketPrice;
		this.isAllin = isAllin;
		this.allinLocation = allinLocation;
		this.allinExpectedAttendance = allinExpectedAttendance;
		this.stdAddress = stdAddress;
		this.stdMaxParticipants = stdMaxPartecipants;
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
	 * Gets the dates.
	 *
	 * @return the dates
	 */
	public List<Date> getDates() {
		return dates;
	}

	/**
	 * Sets the dates.
	 *
	 * @param dates the new dates
	 */
	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	/**
	 * Gets the ticket price.
	 *
	 * @return the ticket price
	 */
	public double getTicketPrice() {
		return ticketPrice;
	}

	/**
	 * Sets the ticket price.
	 *
	 * @param ticketPrice the new ticket price
	 */
	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	/**
	 * Gets the checks if is allin.
	 *
	 * @return the checks if is allin
	 */
	public boolean getIsAllin() {
		return isAllin;
	}

	/**
	 * Sets the checks if is allin.
	 *
	 * @param isAllin the new checks if is allin
	 */
	public void setIsAllin(boolean isAllin) {
		this.isAllin = isAllin;
	}

	
	/**
	 * Gets the allin location.
	 *
	 * @return the allin location
	 */
	public String getAllinLocation() {
		return allinLocation;
	}

	/**
	 * Sets the allin location.
	 *
	 * @param allinLocation the new allin location
	 */
	public void setAllinLocation(String allinLocation) {
		this.allinLocation = allinLocation;
	}

	/**
	 * Gets the allin expected attendance.
	 *
	 * @return the allin expected attendance
	 */
	public int getAllinExpectedAttendance() {
		return allinExpectedAttendance;
	}

	/**
	 * Sets the allin expected attendance.
	 *
	 * @param allinExpectedAttendance the new allin expected attendance
	 */
	public void setAllinExpectedAttendance(int allinExpectedAttendance) {
		this.allinExpectedAttendance = allinExpectedAttendance;
	}

	/**
	 * Gets the std address.
	 *
	 * @return the std address
	 */
	public Address getStdAddress() {
		return stdAddress;
	}

	/**
	 * Sets the std address.
	 *
	 * @param stdAddress the new std address
	 */
	public void setStdAddress(Address stdAddress) {
		this.stdAddress = stdAddress;
	}

	/**
	 * Gets the std max partecipants.
	 *
	 * @return the std max partecipants
	 */
	public int getStdMaxParticipants() {
		return stdMaxParticipants;
	}

	/**
	 * Sets the std max partecipants.
	 *
	 * @param stdMaxPartecipants the new std max partecipants
	 */
	public void setStdMaxParticipants(int stdMaxParticipants) {
		this.stdMaxParticipants = stdMaxParticipants;
	}

	/**
	 * Sets the value from JSON.
	 *
	 * @param jsonNode the new value from JSON
	 public void setValueFromJSON(SpinJsonNode jsonNode) {
		//Title
		if( !jsonNode.prop("title").isNull() )
			setTitle(jsonNode.prop("title").toString());
		else
			setTitle("");
		//dates
		if( !jsonNode.prop("dates").isNull() )
			if (!jsonNode.prop("dates").elements().isEmpty())
				for( SpinJsonNode date : jsonNode.prop("dates").elements() )
					dates.add( new DateTime(date.toString()).toDate() );
			else
				setDates(null);
		else
			setDates(null);
		//ticketPrice
		if( !jsonNode.prop("ticketPrice").isNull() )
			setTicketPrice(jsonNode.prop("ticketPrice").numberValue().doubleValue());
		else
			setTicketPrice(0.0); 
		//isAllin
		if( !jsonNode.prop("isAllin").isNull() )
			setIsAllin(Boolean.getBoolean(jsonNode.prop("isAllin").toString()));
		else
			setIsAllin(false);
		//allIn
		if( !jsonNode.prop("allinprop").isNull() ) {
			//allinLocation
			if( !jsonNode.prop("allinprop").prop("location").isNull() )
				setAllinLocation(jsonNode.prop("allinprop").prop("location").toString());
			else
				setAllinLocation("");
			//allinExpectedAttendance
			if( !jsonNode.prop("allinprop").prop("expectedAtt").isNull() )
				setAllinExpectedAttendance(jsonNode.prop("allinprop").prop("expectedAtt").numberValue().intValue());
			else
				setAllinExpectedAttendance(0);
		}
		else {
			setAllinLocation("");
			setAllinExpectedAttendance(0);
		}
		
		//std
		if( !jsonNode.prop("stdprop").isNull() ) {
			//stdAddress
			if( !jsonNode.prop("stdprop").prop("address").isNull() )
				setStdAddress(new Address(prop("stdprop").prop("address").toString());
			else
				setStdAddress("");
			//stdMaxPartecipants
			if( !jsonNode.prop("stdprop").prop("maxPartecipants").isNull() )
				setStdMaxParticipants(jsonNode.prop("stdprop").prop("maxPartecipants").numberValue().intValue());
			else
				setStdMaxParticipants(0);
		}
		else {
			setStdAddress("");
			setStdMaxParticipants(0);
		}		
	}
	*/
	
	/**
	 * To JSON.
	 *
	 * @return the string
	 */
	public String toJSON() {
		String datesJSON = "[";
		datesJSON += "\"" + new DateTime(dates.get(0)).toString() + "\"";
		for( int i = 1; i < dates.size(); i++ )
			datesJSON += ", \"" + new DateTime(dates.get(i)).toString() + "\"";
		datesJSON += "]";
		return "{"
				+ "\"title\": \"" + title + "\", "
				+ "\"dates\": \"" + datesJSON + "\", "
				+ "\"ticketPrice\": \"" + ticketPrice + "\", "
				+ "\"isAllin\": \"" + isAllin + "\", "
				+ "\"allinLocation\": " + allinLocation + ", "
				+ "\"allinExpectedAttendance\": " + allinExpectedAttendance + ", "
				+ "\"stdAddress\": " + stdAddress + ", "
				+ "\"stdMaxParticipants\": " + stdMaxParticipants
				+ "}";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConferenceData [" + 
					"title=" + title + "," +
					"dates=" + dates + ", " + 
					"ticketPrice=" + ticketPrice + ", " + 
					"isAllin=" + isAllin + ", " + 
					"allinLocation=" + allinLocation + ", " + 
					"allinExpectedAttendance=" + allinExpectedAttendance + ", " + 
					"stdAddress=" + stdAddress + ", " + 
					"stdMaxParticipants=" + stdMaxParticipants + 
					"]";
	}
	
}
