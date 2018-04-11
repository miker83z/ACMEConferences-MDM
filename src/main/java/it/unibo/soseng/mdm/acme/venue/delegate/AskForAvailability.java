package it.unibo.soseng.mdm.acme.venue.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.acme.model.ConferenceData;
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
		
		// Get the JSON variable from Camunda engine (contacted partners)
		PartnerDatas partners = (PartnerDatas) execution.getVariable("contactedPartners");
				
		// Get the JSON variable from Camunda engine (all partners)
		PartnerDatas allPartners = (PartnerDatas) execution.getVariable("allPartners");
		
		// Retrieve job informations from Camunda
		ConferenceData conferenceData = (ConferenceData) execution.getVariable("conferenceData");
		
		// Create a connection between Camunda and Google Mail server
		EmailSender emailSender = new EmailSender(EMAIL_USERNAME, EMAIL_PASSWORD, EMAIL_NAME);
		emailSender.configureConnection();
		
		// Get my id 
		Integer id = (Integer) execution.getVariable("loopCounter");
		
		// FIXME: mettere username e password con cui il partner farà il login su Camunda
		String clientName = "CLIENT";
		// Create and send an email to the partner
		String emailMessage = "Dear " + partners.getPartnerList().get(id).getName() + ",\n"
				+ "\n"
				// FIXME: trasformare il conference.getDates() in una data leggibile
				+ "We would like to formally offer you a job for " + clientName + ", the starting date is " + conferenceData.getDates() + ".\n"
				+ "\n" 
				+ "We would like to have your response by tomorrow.\n"
				+ "Subscribe to our platform http://localhost:8080/camunda/tasklist/default/.\n"
				+ "In the meantime, please feel free to contact us via email on provecamundaisos@gmail.com.\n"
				+ "\n"
				+ "\n"
				+ "Best regards,\n"
				+ "Demo demo\n"
				+ "President of ACME Conferences";
	
		emailSender.send(partners.getPartnerList().get(id).getName() + " - " + EMAIL_SUBJECT, emailMessage, partners.getPartnerList().get(id).getEmail());
		
		// Set the partner as "contacted" (all partners list)
		Integer index = allPartners.indexOf(partners.getPartnerList().get(id).getName());
		allPartners.getPartnerList().get(index).setContacted(true);
		
		// Set the partner as "contacted" (contacted partners list)
		partners.getPartnerList().get(id).setContacted(true);
		
		// Set the new partner businessKey
		String partnerNameWithoutWhitespaces = partners.getPartnerList().get(id).retrieveNameWithoutWhitespaces();
		String partnerBusinessKey = RandomAlphanumericString.generate();
		execution.setVariable(partnerNameWithoutWhitespaces + "BusinessKey", partnerBusinessKey);
		
		// Set JSON serialization for conference data
		ObjectValue typedConferenceData = Variables.objectValue(conferenceData).serializationDataFormat("application/json").create();
		
		// Send the message to create the partner pool
		runtimeService.createMessageCorrelation("job_proposal")
		.processInstanceBusinessKey(partnerBusinessKey)					// business key of the new instance
		.setVariable("acmeBusinessKey", execution.getBusinessKey())		// business key to communicate with ACME
		.setVariable("processTenant", partnerNameWithoutWhitespaces)	// the username of partner in Camunda
		.setVariable("conferenceData", typedConferenceData)				// the informations about the conference
		.correlate();
					
		// Update partner list (contacted partners)
		execution.setVariable("contactedPartners", partners);
		
		// Update partner list (all partners)
		execution.setVariable("allPartners", allPartners);
	}

}
