package it.unibo.soseng.mdm.acme.management.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import it.unibo.soseng.mdm.model.Address;
import it.unibo.soseng.mdm.model.ConferenceData;
import it.unibo.soseng.mdm.model.PartnerData;
import it.unibo.soseng.mdm.model.RelevantEvents;
import it.unibo.soseng.mdm.services.django.Event;

/**
 * The Class CreateDjangoEvent, used in task "Create Event in Registration Platform" to create an event in the registration platform.
 * @author Mirko Zichichi
 */
public class CreateDjangoEvent implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("loopVar", ((Integer) execution.getVariable("loopVar")) + 1 );
		if( !execution.hasVariable("subscriptionsStarting") )
			execution.setVariable("subscriptionsStarting", ((RelevantEvents) execution.getVariable("relevantEvents")).getSubsStart());
		ConferenceData data = (ConferenceData) execution.getVariable("conferenceData");
		String token = (String) execution.getVariable("djangoToken");
		Address address = new Address();
		String locationName = "";
		int maxSeats = 0;
		if (!data.getIsAllin()) {
			address = data.getStdAddress();
			locationName = data.getStdVenueName();
			maxSeats = data.getStdMaxParticipants();
		}
		else {
			PartnerData pd = (PartnerData) execution.getVariable("chosenPartner");
			address = pd.getAddress();
			locationName = pd.getName();
			maxSeats = pd.getMaxSeats();
		}
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		
		String dates = "[";
		for (int i = 0; i < data.getDates().size(); i++)
			if( i != data.getDates().size() - 1 )
				dates += "\"" + new DateTime(data.getDates().get(i)).toString(dtf) + "\",";  
			else
				dates += "\"" + new DateTime(data.getDates().get(i)).toString(dtf) + "\"],"; 
		
		Event event = new Event(token, data.getTitle(), dates, new DateTime(data.getSubsStart()).toString(dtf), new DateTime(data.getContDeadline()).toString(dtf),
				new DateTime(data.getSubsDeadline()).toString(dtf), address, locationName, maxSeats, data.getTicketParticipant(), data.getTicketContributor(),
				false, false);
		
		try {			
			int eventID = event.post();
			execution.setVariable("djangoEventID", eventID);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BpmnError("CONNECTION_ERROR");
		}
	}
	
}
	  



