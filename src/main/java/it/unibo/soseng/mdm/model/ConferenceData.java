package it.unibo.soseng.mdm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Class ConferenceData, used to store the conference data.
 * @author Mirko Zichichi
 */
public class ConferenceData {

	/** The title. */
	private String title;
	
	/** The dates. */
	private List<Date> dates = new ArrayList<Date>();
	
	/** The subscriptions starting date. */
	private Date subsStart;

	/** The contributions deadline date. */
	private Date contDeadline;

	/** The subscriptions deadline date. */
	private Date subsDeadline;
	
	/** The ticket price.for Participants */
	private double ticketParticipant;
	
	/** The ticket price.for Contributors */
	private double ticketContributor;
	
	/** The All-Inclusive flag. */
	private boolean isAllin;
	
	/** The location if it is All-Inclusive. */
	private Address allinLocation;
	
	/** The expected attendance if it is All-Inclusive. */
	private int allinExpectedAttendance;
	
	/** The venue name if it is Standard. */
	private String stdVenueName;
	
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
	 * @param subsStart the subs start
	 * @param contDeadline the cont deadline
	 * @param subsDeadline the subs deadline
	 * @param ticketParticipant the ticket participant
	 * @param ticketContributor the ticket contributor
	 * @param isAllin the is allin
	 * @param allinLocation the allin location
	 * @param allinExpectedAttendance the allin expected attendance
	 * @param stdAddress the std address
	 * @param stdMaxParticipants the std max participants
	 */
	public ConferenceData(String title, List<Date> dates, Date subsStart, Date contDeadline, Date subsDeadline,
			double ticketParticipant, double ticketContributor, boolean isAllin, Address allinLocation,
			int allinExpectedAttendance, Address stdAddress, int stdMaxParticipants) {
		this.title = title;
		this.dates = dates;
		this.subsStart = subsStart;
		this.contDeadline = contDeadline;
		this.subsDeadline = subsDeadline;
		this.ticketParticipant = ticketParticipant;
		this.ticketContributor = ticketContributor;
		this.isAllin = isAllin;
		this.allinLocation = allinLocation;
		this.allinExpectedAttendance = allinExpectedAttendance;
		this.stdAddress = stdAddress;
		this.stdMaxParticipants = stdMaxParticipants;
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
	 * Gets the subs start.
	 *
	 * @return the subs start
	 */
	public Date getSubsStart() {
		return subsStart;
	}

	/**
	 * Sets the subs start.
	 *
	 * @param subsStart the new subs start
	 */
	public void setSubsStart(Date subsStart) {
		this.subsStart = subsStart;
	}

	/**
	 * Gets the cont deadline.
	 *
	 * @return the cont deadline
	 */
	public Date getContDeadline() {
		return contDeadline;
	}

	/**
	 * Sets the cont deadline.
	 *
	 * @param contDeadline the new cont deadline
	 */
	public void setContDeadline(Date contDeadline) {
		this.contDeadline = contDeadline;
	}

	/**
	 * Gets the subs deadline.
	 *
	 * @return the subs deadline
	 */
	public Date getSubsDeadline() {
		return subsDeadline;
	}

	/**
	 * Sets the subs deadline.
	 *
	 * @param subsDeadline the new subs deadline
	 */
	public void setSubsDeadline(Date subsDeadline) {
		this.subsDeadline = subsDeadline;
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
	public Address getAllinLocation() {
		return allinLocation;
	}

	/**
	 * Sets the allin location.
	 *
	 * @param allinLocation the new allin location
	 */
	public void setAllinLocation(Address allinLocation) {
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
	 * Gets the std venue name.
	 *
	 * @return the std venue name
	 */
	public String getStdVenueName() {
		return stdVenueName;
	}

	/**
	 * Sets the std venue name.
	 *
	 * @param stdVenueName the new std venue name
	 */
	public void setStdVenueName(String stdVenueName) {
		this.stdVenueName = stdVenueName;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConferenceData [" + 
					"title=" + title + "," +
					"dates=" + dates + ", " + 
					"subsStart=" + subsStart + ", " + 
					"contDeadline=" + contDeadline + ", " + 
					"subsDeadline=" + subsDeadline + ", " + 
					"ticketParticipant=" + ticketParticipant + ", " + 
					"ticketContributor=" + ticketContributor + ", " + 
					"isAllin=" + isAllin + ", " + 
					"allinLocation=" + allinLocation + ", " + 
					"allinExpectedAttendance=" + allinExpectedAttendance + ", " + 
					"stdAddress=" + stdAddress + ", " + 
					"stdMaxParticipants=" + stdMaxParticipants + 
					"]";
	}
	
}
