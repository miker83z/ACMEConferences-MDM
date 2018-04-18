package it.unibo.soseng.mdm.acme.management.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.joda.time.DateTime;

import it.unibo.soseng.mdm.model.ConferenceData;

/**
 * The class ReceiveConferenceDataListener, used for the ACME process start event to setup variables
 * @author Mirko Zichichi
 */
public class ReceiveConferenceDataListener implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		ConferenceData conference = (ConferenceData) execution.getVariable("conferenceData");
		ObjectValue value = Variables.objectValue(conference).serializationDataFormat("application/json").create();
		execution.setVariable("conferenceData", value);
		execution.setVariable("confLastDayPlus60", new DateTime(conference.getDates().get(0)).plusDays(60).toDate() );
		execution.setVariable("confLastDayPlus60", new DateTime(conference.getDates().get(conference.getDates().size()-1)).plusDays(60).toDate() );
		execution.setVariable("allin", conference.getIsAllin());
		
		execution.setVariable("sumPayed", 0.0);
		execution.setVariable("subscriptionClosed", false);
		execution.setVariable("partnerBillsToPay", false);
	}
}
