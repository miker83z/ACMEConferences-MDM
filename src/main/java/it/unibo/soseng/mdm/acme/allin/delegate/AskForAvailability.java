package it.unibo.soseng.mdm.acme.allin.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.joda.time.DateTime;

import it.unibo.soseng.mdm.model.ConferenceData;
import it.unibo.soseng.mdm.model.PartnerCollection;
import it.unibo.soseng.mdm.model.PartnerData;
import it.unibo.soseng.mdm.util.EmailSender;
import it.unibo.soseng.mdm.util.RandomAlphanumericString;

/**
 * Send an e-mail to the partner asking for their availability.
 * This class is also used to create the Camunda instance of the partner pool.
 * 
 * @author Davide Marchi
 *
 */
public class AskForAvailability implements JavaDelegate {

	// Values for email
	private static final String EMAIL_USERNAME = "provecamundaisos@gmail.com";
	private static final String EMAIL_PASSWORD = "camundaisos";
	private static final String EMAIL_NAME = "ACME Conferences";
	private static final String EMAIL_SUBJECT = "Job offer from ACME Conferences";
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Get Camunda runtime service 
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		
	    // Venue/catering flag
	    Boolean itsCateringTime = (Boolean) execution.getVariable("itsCateringTime");
	    
		// Get the variable from Camunda engine (all partners)
		PartnerCollection allPartners = (PartnerCollection) execution.getVariable("allPartners");
		
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
	    	PartnerCollection partners = (PartnerCollection) execution.getVariable("contactedPartners");
	    	
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
		
		// Create and send an email to the partner
		String emailMessage = "Dear " + partnerName + ",\n"
				+ "\n"
				+ "We would like to formally offer you a job for a conference, the starting date is " + new DateTime(conferenceData.getDates().get(0)) + ".\n"
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
		
		if (!itsCateringTime)
			// Send the message to create the partner pool
			runtimeService.createMessageCorrelation(messageCorrelation)
			.processInstanceBusinessKey(partnerBusinessKey)					// business key of the new instance
			.setVariable("acmeBusinessKey", execution.getBusinessKey())		// business key to communicate with ACME
			.setVariable("processTenant", partnerNameWithoutWhitespaces)	// the username of partner in Camunda
			.setVariable("conferenceData", typedConferenceData)				// the informations about the conference
			.correlate();
		else {
			PartnerData partner = (PartnerData) execution.getVariable("chosenPartner");
			ObjectValue typedPartner = Variables.objectValue(partner).serializationDataFormat("application/json").create();
			
			runtimeService.createMessageCorrelation(messageCorrelation)
			.processInstanceBusinessKey(partnerBusinessKey)					
			.setVariable("acmeBusinessKey", execution.getBusinessKey())		
			.setVariable("processTenant", partnerNameWithoutWhitespaces)	
			.setVariable("conferenceData", typedConferenceData)
			.setVariable("chosenPartner", typedPartner)
			.correlate();
		}
			
					
		// Update partner list (all partners)
		execution.setVariable("allPartners", allPartners);
	}

}
