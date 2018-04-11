package it.unibo.soseng.mdm.acme.venue.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.model.ConferenceData;
import it.unibo.soseng.mdm.acme.model.PartnerDatas;
import it.unibo.soseng.mdm.util.EmailSender;

public class FindFeasiblePartners implements JavaDelegate {
	
	private static final Integer NUMBER_OF_PARTNERS = 3;
	
	// Values for email
	private static final String EMAIL_USERNAME = "provecamundaisos@gmail.com";
	private static final String EMAIL_PASSWORD = "camundaisos";
	private static final String EMAIL_NAME = "ACME Conferences";
	private static final String EMAIL_SUBJECT = "Job offer from ACME Conferences";
	
	public void execute(DelegateExecution execution) throws Exception {		
		// Get the JSON variable from Camunda engine
		PartnerDatas partners = (PartnerDatas) execution.getVariable("allPartners");
		
		// Remove contacted partners
		partners.removeContactedPartners();
		
		// Retrieve job informations from Camunda
		ConferenceData conference = (ConferenceData) execution.getVariable("conferenceData");
		
		// Set gateway variable and continue
		if (partners.retrieveNumberOfPartners() > 0) {
			execution.setVariable("remaining_partners", true);
		
			// Order partners from distance to the conference location
			partners.orderPartnersList(conference.getAllinAddress());
			
			// Leave only the two nearest partners
			partners.cutPartnersList(NUMBER_OF_PARTNERS);
			
			// Set loop cardinality for the parallel sub-process
			execution.setVariable("numberOfPartners", partners.getPartnerList().size());
			
			// Set partner list
			// execution.setVariable("contactedPartners", JSON(partners.toJSON()));
			execution.setVariable("contactedPartners", partners);
		} 
		// End
		else {
			// Create a connection between Camunda and Google Mail server
			EmailSender emailSender = new EmailSender(EMAIL_USERNAME, EMAIL_PASSWORD, EMAIL_NAME);
			emailSender.configureConnection();
			
			// Create and send an email to the partner
			String emailMessage = "Dear " + conference.getClientName() + ",\n"
					+ "\n"
					+ "Sorry there aren't available partners for this date.\n"
					+ "\n"
					+ "\n"
					+ "Best regards,\n"
					+ "Demo demo\n"
					+ "President of ACME Conferences";
		
			// FIXME: usare la variabile clientMail dentro conferenceData
			String clientEmail = "provecamundaisos@gmail.com";
			emailSender.send(conference.getClientName() + " - " + EMAIL_SUBJECT, emailMessage, clientEmail);
			
			// Set gateway variable
			execution.setVariable("remaining_partners", false);
		}
		
		
	}
		
}