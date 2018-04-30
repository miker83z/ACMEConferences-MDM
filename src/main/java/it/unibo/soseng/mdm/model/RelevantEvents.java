package it.unibo.soseng.mdm.model;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

/**
 * The Class RelevantEvents, used to model relevant events to be advertised in Chirpter.
 * @author Mirko Zichichi
 */
public class RelevantEvents {
	
	/** The events. */
	private ArrayList<Date> events;
	
	/** The checked. */
	private ArrayList<Boolean> checked;
	
	/** The event description. */
	private ArrayList<String> eventDescription;

	/**
	 * Instantiates a new relevant events.
	 */
	public RelevantEvents() {
		events = new ArrayList<Date>();
		checked = new ArrayList<Boolean>();
		eventDescription = new ArrayList<String>();
		for (int i = 0; i < 4; i++)
			checked.add(false);
		eventDescription.add("Subscriptions Starting in 2 Days");
		eventDescription.add("Contributions Submission Closing in 2 Days");
		eventDescription.add("Subscriptions Closing in 2 Days");
		eventDescription.add("Conference Starting in 2 Days");		
	}
	
	/**
	 * Instantiates a new relevant events.
	 *
	 * @param data the data
	 */
	public RelevantEvents(ConferenceData data) {
		this();
		events.add(new DateTime(data.getSubsStart()).minusDays(2).toDate());
		events.add(new DateTime(data.getContDeadline()).minusDays(2).toDate());
		events.add(new DateTime(data.getSubsDeadline()).minusDays(2).toDate());
		events.add(new DateTime(data.getDates().get(0)).minusDays(2).toDate());
	}
	
	public String retrieveCurrentEventDescription() {
		int i = retrieveNextRelevantEventId() - 1;
		if ( i == -1 ) return null;
		if( i == -2 ) return eventDescription.get(eventDescription.size()-1);
		return eventDescription.get(i);
	}
	
	/**
	 * Retrieve next relevant event id.
	 *
	 * @return the int
	 */
	public int retrieveNextRelevantEventId() {
		boolean flag = true;
		int i = 0;
		while(flag)
			if(i < checked.size())
				flag = checked.get(i++);
			else {
				flag = false;
				i = 0;
			}
		return --i;
	}
	
	/**
	 * Retrieve relevant event.
	 *
	 * @return the date
	 */
	public Date retrieveNextRelevantEvent() {
		int i = retrieveNextRelevantEventId();
		if( i < 0 ) return null;
		checked.set(i, true);
		return events.get(i);
	}
	
	/**
	 * Gets the subs start.
	 *
	 * @return the subs start
	 */
	public Date getSubsStart() {
		return events.get(0);
	}

	/**
	 * Sets the subs start.
	 *
	 * @param subsStart the new subs start
	 */
	public void setSubsStart(Date subsStart) {
		this.events.set(0, subsStart);
	}

	/**
	 * Gets the cont deadline.
	 *
	 * @return the cont deadline
	 */
	public Date getContDeadline() {
		return events.get(1);
	}

	/**
	 * Sets the cont deadline.
	 *
	 * @param contDeadline the new cont deadline
	 */
	public void setContDeadline(Date contDeadline) {
		this.events.set(1, contDeadline);
	}

	/**
	 * Gets the subs deadline.
	 *
	 * @return the subs deadline
	 */
	public Date getSubsDeadline() {
		return events.get(2);
	}

	/**
	 * Sets the subs deadline.
	 *
	 * @param subsDeadline the new subs deadline
	 */
	public void setSubsDeadline(Date subsDeadline) {
		this.events.set(2, subsDeadline);
	}

	/**
	 * Gets the first day of conference.
	 *
	 * @return the first day of conference
	 */
	public Date getFirstDayOfConference() {
		return events.get(3);
	}

	/**
	 * Sets the first day of conference.
	 *
	 * @param firstDayOfConference the new first day of conference
	 */
	public void setFirstDayOfConference(Date firstDayOfConference) {
		this.events.set(3, firstDayOfConference);
	}

	/**
	 * Gets the events.
	 *
	 * @return the events
	 */
	public ArrayList<Date> getEvents() {
		return events;
	}

	/**
	 * Sets the events.
	 *
	 * @param events the new events
	 */
	public void setEvents(ArrayList<Date> events) {
		this.events = events;
	}

	/**
	 * Gets the checked.
	 *
	 * @return the checked
	 */
	public ArrayList<Boolean> getChecked() {
		return checked;
	}

	/**
	 * Sets the checked.
	 *
	 * @param checked the new checked
	 */
	public void setChecked(ArrayList<Boolean> checked) {
		this.checked = checked;
	}

	/**
	 * Gets the event description.
	 *
	 * @return the event description
	 */
	public ArrayList<String> getEventDescription() {
		return eventDescription;
	}

	/**
	 * Sets the event description.
	 *
	 * @param eventDescription the new event description
	 */
	public void setEventDescription(ArrayList<String> eventDescription) {
		this.eventDescription = eventDescription;
	}
	
	
}
