package it.unibo.soseng.mdm.acme.venue.delegate;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.Address;
import it.unibo.soseng.mdm.acme.model.ConferenceData;
import it.unibo.soseng.mdm.acme.model.JobData;
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
		// FIXME: togliere JSON
		// Get the JSON variable from Camunda engine
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("allPartners");
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);
				
		// TODO: controllare se ci sono partner rimanenti oppure no
		// Remove contacted partners
		partners.removeContactedPartners();
				
		// Set gateway variable and continue
		if (partners.getNumberOfPartners() > 0) {
			execution.setVariable("remaining_partners", true);
			
			// Retrieve job informations from Camunda
			ConferenceData conferenceData = (ConferenceData) execution.getVariable("conferenceData");
					
			// FIXME: invece di questo address usare la variabile Address dentro conferenceData
			Address address = new Address("Italia", conferenceData.getAllinLocation(), "", "");
			partners.orderPartnersList(address);
			
			// Leave only the two nearest partners
			partners.cutPartnersList(NUMBER_OF_PARTNERS);
			
			// Set loop cardinality for the parallel sub-process
			execution.setVariable("numberOfPartners", partners.getPartnerList().size());
			
			// FIXME: togliere JSON
			// Set partner list
			execution.setVariable("contactedPartners", JSON(partners.toJSON()));
		} 
		// End
		else {
			// Create a connection between Camunda and Google Mail server
			EmailSender emailSender = new EmailSender(EMAIL_USERNAME, EMAIL_PASSWORD, EMAIL_NAME);
			emailSender.configureConnection();
			
			// FIXME: usare la variable clientName dentro conferenceData
			// Create and send an email to the partner
			String emailMessage = "Dear " + "CLIENT" + ",\n"
					+ "\n"
					+ "Sorry there aren't available partners for this date.\n"
					+ "\n"
					+ "\n"
					+ "Best regards,\n"
					+ "Demo demo\n"
					+ "President of ACME Conferences";
		
			// FIXME: usare la variabile clientName dentro conferenceData
			// FIXME: usare la variabile clientMail dentro conferenceData
			String clientEmail = "provecamundaisos@gmail.com";
			emailSender.send("CLIENT" + " - " + EMAIL_SUBJECT, emailMessage, clientEmail);
			
			// Set gateway variable
			execution.setVariable("remaining_partners", false);
		}
		
		
	}
		
}