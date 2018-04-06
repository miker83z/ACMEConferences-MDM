package it.unibo.soseng.mdm.acme.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.camunda.spin.json.SpinJsonNode;
import org.joda.time.DateTime;

public class ConferenceData {

	private String title;
	private List<Date> dates = new ArrayList<Date>();
	private double ticketPrice;
	private boolean isAllin;
	private String allinLocation;
	private int allinExpectedAttendance;
	private String stdAddress;
	private int stdMaxPartecipants;
	
	public ConferenceData() {
		//
	}
	
	public ConferenceData(SpinJsonNode jsonNode) {
		this();
		setValueFromJSON(jsonNode);
	}
	
	public ConferenceData(String title, List<Date> dates, double ticketPrice, boolean isAllin, String allinLocation,
			int allinExpectedAttendance, String stdAddress, int stdMaxPartecipants) {
		this.title = title;
		this.dates = dates;
		this.ticketPrice = ticketPrice;
		this.isAllin = isAllin;
		this.allinLocation = allinLocation;
		this.allinExpectedAttendance = allinExpectedAttendance;
		this.stdAddress = stdAddress;
		this.stdMaxPartecipants = stdMaxPartecipants;
	}
	
	public Date getFirstDay() {
		if(dates != null && dates.size() > 0)
			return dates.get(0);
		else
			return new DateTime("2019-01-01T12:00:00").toDate();
	}
	
	public Date getLastDay() {
		if(dates != null && dates.size() > 0)
			return dates.get(dates.size() - 1);
		else
			return new DateTime("2019-01-01T12:00:00").toDate();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	public boolean getIsAllin() {
		return isAllin;
	}

	public void setIsAllin(boolean isAllin) {
		this.isAllin = isAllin;
	}

	
	public String getAllinLocation() {
		return allinLocation;
	}

	public void setAllinLocation(String allinLocation) {
		this.allinLocation = allinLocation;
	}

	public int getAllinExpectedAttendance() {
		return allinExpectedAttendance;
	}

	public void setAllinExpectedAttendance(int allinExpectedAttendance) {
		this.allinExpectedAttendance = allinExpectedAttendance;
	}

	public String getStdAddress() {
		return stdAddress;
	}

	public void setStdAddress(String stdAddress) {
		this.stdAddress = stdAddress;
	}

	public int getStdMaxPartecipants() {
		return stdMaxPartecipants;
	}

	public void setStdMaxPartecipants(int stdMaxPartecipants) {
		this.stdMaxPartecipants = stdMaxPartecipants;
	}

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
				setStdAddress(jsonNode.prop("stdprop").prop("address").toString());
			else
				setStdAddress("");
			//stdMaxPartecipants
			if( !jsonNode.prop("stdprop").prop("maxPartecipants").isNull() )
				setStdMaxPartecipants(jsonNode.prop("stdprop").prop("maxPartecipants").numberValue().intValue());
			else
				setStdMaxPartecipants(0);
		}
		else {
			setStdAddress("");
			setStdMaxPartecipants(0);
		}		
	}

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
					"stdMaxPartecipants=" + stdMaxPartecipants + 
					"]";
	}
	
}
