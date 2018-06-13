package it.unibo.soseng.mdm.acme.cancellation.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;
import it.unibo.soseng.mdm.services.django.Event;
import it.unibo.soseng.mdm.services.django.EventReservation;
import it.unibo.soseng.mdm.services.django.EventReservations;

/**
 * The Class ParticipantRefund, used in task "Initiate Participant Refund" to initiate the refund of the participant that have payed for the event in the registration platform.
 * @author Mirko Zichichi
 */
public class ParticipantRefund implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		String token = (String) execution.getVariable("djangoToken");
		int eventID = (Integer) execution.getVariable("djangoEventID");
		Event event = new Event(token, eventID);
		EventReservations eventReservations = new EventReservations(token, eventID);
		BillsCollection bills = new BillsCollection();
		try {
			event.put("{\"is_open\": false ,\"is_open_contr\": false}");	//Close subscriptions and contributions upload
			event.get();
			eventReservations.get();
			for (EventReservation res : eventReservations.getEventReservations()) {
				Bill bill = new Bill();
				if(res.isStaff())
					bill.setAmount(event.getTicketContributor());
				else
					bill.setAmount(event.getTicketParticipant());
				bill.setReceiver(res.getBankUser());
				bills.addBill(bill);
			}
			boolean participantPayment = false;
			if(bills.hasBills())
				participantPayment = true;
			execution.setVariable("participantPayment", participantPayment);
			execution.setVariable("billsToPay", bills);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BpmnError("CONNECTION_ERROR");
		}
	}
	
}
	  



