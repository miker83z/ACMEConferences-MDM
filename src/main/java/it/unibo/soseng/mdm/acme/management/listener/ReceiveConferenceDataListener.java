package it.unibo.soseng.mdm.acme.management.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.ConferenceData;

public class ReceiveConferenceDataListener implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		ConferenceData conference = (ConferenceData) execution.getVariable("conferenceData");
			
		
		//ConferenceData conference = new ConferenceData(conferenceData);
		//execution.setVariable("confTitle", conference.getTitle());
		//execution.setVariable("confFirstDay", conference.getFirstDay());
		
		
		
		// TODO: forse questo va riabilitato
		// execution.setVariable("confLastDayPlus60", new DateTime(conference.getLastDay()).plusDays(60).toDate() );
		
		
		
		//execution.setVariable("confTicketPrice", conference.getTicketPrice());
		execution.setVariable("allin", conference.getIsAllin());
		/*if( conference.getIsAllin() ) {
			execution.setVariable("confAllInLocation", conference.getAllinLocation());
			execution.setVariable("confAllInExpectedAttendance", conference.getAllinExpectedAttendance());
		}
		else {
			execution.setVariable("confStdAddress", conference.getStdAddress());
			execution.setVariable("confStdMaxPartecipants", conference.getStdMaxPartecipants());
		}*/
		
		execution.setVariable("sumPayed", 0.0);
		execution.setVariable("subscriptionClosed", false);
		execution.setVariable("partnerBillsToPay", false);
	}
}
