package it.unibo.soseng.mdm.acme.management.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.ConferenceData;
import it.unibo.soseng.mdm.services.django.Event;

/**
 * The Class OpenRegistrationPlatformSubscriptions, used in task "Open Subscriptions" to open the subscriptions to an event in the registration platform.
 * @author Mirko Zichichi
 */
public class OpenRegistrationPlatformSubscriptions implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("loopVar", ((Integer) execution.getVariable("loopVar")) + 1 );
		ConferenceData data = (ConferenceData) execution.getVariable("conferenceData");
		if( !execution.hasVariable("subscriptionsDeadline") )
			execution.setVariable("subscriptionsDeadline", data.getSubsDeadline());
		if( !execution.hasVariable("contributionsDeadline") )
			execution.setVariable("contributionsDeadline", data.getContDeadline());
		String token = (String) execution.getVariable("djangoToken");
		int eventID = (Integer) execution.getVariable("djangoEventID");
		Event event = new Event(token, eventID);
		try {			
			event.put("{\"is_open\": true, \"is_open_contr\": true }");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BpmnError("CONNECTION_ERROR");
		}
	}
	
}
	  



