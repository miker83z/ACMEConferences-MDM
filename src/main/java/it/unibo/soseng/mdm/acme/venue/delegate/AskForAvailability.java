package it.unibo.soseng.mdm.acme.venue.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.acme.model.ConferenceData;
import it.unibo.soseng.mdm.acme.model.PartnerData;
import it.unibo.soseng.mdm.acme.model.PartnerDatas;
import it.unibo.soseng.mdm.util.EmailSender;
import it.unibo.soseng.mdm.util.RandomAlphanumericString;

public class AskForAvailability implements JavaDelegate {

	// Values for email
	private static final String EMAIL_USERNAME = "provecamundaisos@gmail.com";
	private static final String EMAIL_PASSWORD = "camundaisos";
	private static final String EMAIL_NAME = "ACME Conferences";
	private static final String EMAIL_SUBJECT = "Job offer from ACME Conferences";
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Get Camunda runtime service 
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		
	    // Venue/catering flag
	    Boolean itsCateringTime = (Boolean) execution.getVariable("itsCateringTime");
	    
		// Get the variable from Camunda engine (all partners)
		PartnerDatas allPartners = (PartnerDatas) execution.getVariable("allPartners");
		
		// Retrieve job informations from Camunda
		ConferenceData conferenceData = (ConferenceData) execution.getVariable("conferenceData");
		
		// Create a connection between Camunda and Google Mail server
		EmailSender emailSender = new EmailSender(EMAIL_USERNAME, EMAIL_PASSWORD, EMAIL_NAME);
		emailSender.configureConnection();
		
		// Partner informations
		String partnerName;
		String partnerEmail;
		String partnerNameWithoutWhitespaces;
		
		// Name of the message
		String messageCorrelation;
		
		if (!itsCateringTime) {
			// Get the list of contacted partners
	    	PartnerDatas partners = (PartnerDatas) execution.getVariable("contactedPartners");
	    	
			// Get my id 
			Integer id = (Integer) execution.getVariable("loopCounter");
			
			// Set partner informations
			partnerName = partners.getPartnerList().get(id).getName();
			partnerEmail = partners.getPartnerList().get(id).getEmail();
			partnerNameWithoutWhitespaces = partners.getPartnerList().get(id).retrieveNameWithoutWhitespaces();
			
			// Set the partner as "contacted" (contacted partners list)
			partners.getPartnerList().get(id).setContacted(true);
			
			// Update partner list (contacted partners)
			execution.setVariable("contactedPartners", partners);
			
			// Set message name for correlation
			messageCorrelation = "job_proposal";
		}
		else {
			// Get the best catering partner
	    	PartnerData partner = (PartnerData) execution.getVariable("cateringPartner");
	    	
	    	// Set catering informations
	    	partnerName = partner.getName();
	    	partnerEmail = partner.getEmail();
	    	partnerNameWithoutWhitespaces = partner.retrieveNameWithoutWhitespaces();
	    	
			// Set the partner as "contacted" 
			partner.setContacted(true);
			
			// Update partner
			execution.setVariable("cateringPartner", partner);
			
			// Set message name for correlation
			messageCorrelation = "catering_job_proposal";
		}
		
		// FIXME: mettere username e password con cui il partner farà il login su Camunda
		// Create and send an email to the partner
		String emailMessage = "Dear " + partnerName + ",\n"
				+ "\n"
				// FIXME: trasformare il conference.getDates() in una data leggibile
				+ "We would like to formally offer you a job for " + conferenceData.getClientName() + ", the starting date is " + conferenceData.getDates() + ".\n"
				+ "\n" 
				+ "We would like to have your response by tomorrow.\n"
				+ "Subscribe to our platform http://localhost:8080/camunda/tasklist/default/.\n"
				+ "In the meantime, please feel free to contact us via email on provecamundaisos@gmail.com.\n"
				+ "\n"
				+ "\n"
				+ "Best regards,\n"
				+ "Demo demo\n"
				+ "President of ACME Conferences";
	
		emailSender.send(partnerName + " - " + EMAIL_SUBJECT, emailMessage, partnerEmail);
		
		// Set the partner as "contacted" (all partners list)
		Integer index = allPartners.indexOf(partnerName);
		allPartners.getPartnerList().get(index).setContacted(true);
		
		// Set the new partner businessKey
		String partnerBusinessKey = RandomAlphanumericString.generate();
		execution.setVariable(partnerNameWithoutWhitespaces + "BusinessKey", partnerBusinessKey);
		
		// Set JSON serialization for conference data
		ObjectValue typedConferenceData = Variables.objectValue(conferenceData).serializationDataFormat("application/json").create();
		
		// Send the message to create the partner pool
		runtimeService.createMessageCorrelation(messageCorrelation)
		.processInstanceBusinessKey(partnerBusinessKey)					// business key of the new instance
		.setVariable("acmeBusinessKey", execution.getBusinessKey())		// business key to communicate with ACME
		.setVariable("processTenant", partnerNameWithoutWhitespaces)	// the username of partner in Camunda
		.setVariable("conferenceData", typedConferenceData)				// the informations about the conference
		.correlate();
					
		// Update partner list (all partners)
		execution.setVariable("allPartners", allPartners);
	}

}
