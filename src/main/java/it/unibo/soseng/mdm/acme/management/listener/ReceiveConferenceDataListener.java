package it.unibo.soseng.mdm.acme.management.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.joda.time.DateTime;

import it.unibo.soseng.mdm.model.BillsCollection;
import it.unibo.soseng.mdm.model.ConferenceData;
import it.unibo.soseng.mdm.model.RelevantEvents;

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
		execution.setVariable("relevantEvents", new RelevantEvents(conference));
		execution.setVariable("confLastDayPlus60", new DateTime(conference.getDates().get(conference.getDates().size()-1)).plusDays(60).toDate() );
		execution.setVariable("firstDayOfConference", new DateTime(conference.getDates().get(0)).toDate());
		execution.setVariable("allin", conference.getIsAllin());
		
		execution.setVariable("sumPayed", 0.0);
		execution.setVariable("payLock", false);
		execution.setVariable("sumReservedForManualPayment", 0.0);
		execution.setVariable("availableFunds", 0.0);
		execution.setVariable("subscriptionClosed", false);
		execution.setVariable("subsOpened", false);
		execution.setVariable("loopVar", 0);
		execution.setVariable("loopVar2", 0);
		execution.setVariable("loopVar3", 0);
		execution.setVariable("partnerBillsToPay", false);
		execution.setVariable("itsCateringTime", false);
		execution.setVariable("chirpterToken", "YI7WYvrEwCf7IXOV+N4RJoXHnKj0N5AOA12BlRZfd7E=");
		execution.setVariable("djangoToken", "Token 12cfa6232776a3213193c9a43c1c5ba27c68d5e2");
		
		execution.setVariable("venuePartnerCancFlag", false);
		execution.setVariable("cateringPartnerCancFlag", false);
		execution.setVariable("billsPaymentsCancFlag", true);
		execution.setVariable("clientDebtPayedCancFlag", false);
		execution.setVariable("eventCreatedRegPlatformCancFlag", false);
		execution.setVariable("eventCreatedChirpterCancFlag", false);
		execution.setVariable("cancFlag", true);
		
		execution.setVariable("passOnce", false);
		
		execution.setVariable("ACMEServiceBillAmount", 500.0);
		BillsCollection bills = new BillsCollection(); 
		ObjectValue typedBillsValue = Variables.objectValue(bills).serializationDataFormat("application/json").create();
		execution.setVariable("billsPayed", typedBillsValue);
		
	}
}
