package it.unibo.soseng.mdm.acme.venue.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.venue.model.JobData;
import it.unibo.soseng.mdm.acme.venue.model.PartnerDatas;
import it.unibo.soseng.mdm.util.EmailSender;
import it.unibo.soseng.mdm.util.RandomAlphanumericString;

import static org.camunda.spin.Spin.JSON;

public class UNUSED_AskForAvailability implements JavaDelegate {

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
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("partnerList");
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);
				
		// Get the JSON variable from Camunda engine (all partners)
		SpinJsonNode allPartnersJsonNode = (SpinJsonNode) execution.getVariable("allPartners");
		PartnerDatas allPartners = new PartnerDatas();
		allPartners.setPartnersFromJSON(allPartnersJsonNode);
		
		// Retrieve job informations from Camunda
		SpinJsonNode jobJsonNode = (SpinJsonNode) execution.getVariable("jobInformations");
		JobData jobData = new JobData();
		jobData.setValueFromJSON(jobJsonNode);
		
		// Create a connection between Camunda and Google Mail server
		EmailSender emailSender = new EmailSender(EMAIL_USERNAME, EMAIL_PASSWORD, EMAIL_NAME);
		emailSender.configureConnection();
		
		// Create and send an email to each partner
		for (int i = 0; i < partners.getPartnerList().size(); i++) {
			String emailMessage = "Dear " + partners.getPartnerList().get(i).getName() + ",\n"
					+ "\n"
					+ "We would like to formally offer you a job for " + jobData.getClientName() + ", the starting date is " + jobData.getDate() + ".\n"
					+ "\n" 
					+ "We would like to have your response by tomorrow.\n"
					+ "Subscribe to our platform http://localhost:8080/camunda/tasklist/default/, your JOB-ID is " + i + ".\n"
					+ "In the meantime, please feel free to contact us via email on provecamundaisos@gmail.com.\n"
					+ "\n"
					+ "\n"
					+ "Best regards,\n"
					+ "Demo demo\n"
					+ "President of ACME Conferences";
		
			emailSender.send(partners.getPartnerList().get(i).getName() + " - " + EMAIL_SUBJECT, emailMessage, partners.getPartnerList().get(i).getEmail());
			
			// Set the partner as "contacted" (all partners list)
			Integer index = allPartners.indexOf(partners.getPartnerList().get(i).getName());
			System.out.println("[PROVA] INDEX: " + String.valueOf(index));
			System.out.println("[PROVA] PARTNER: " + allPartners.getPartnerList().get(index).getName());
			System.out.println("[PROVA] REAL: " + partners.getPartnerList().get(i).getName());
			allPartners.getPartnerList().get(index).setContacted(true);
			
			// Set the partner as "contacted" (contacted partners list)
			partners.getPartnerList().get(i).setContacted(true);
			
			// Set the new partner businessKey
			String partnerNameWithoutWhitespaces = partners.getPartnerList().get(i).getNameWithoutWhitespaces();
			String partnerBusinessKey = RandomAlphanumericString.generate();
			execution.setVariable(partnerNameWithoutWhitespaces + "BusinessKey", partnerBusinessKey);
						
			// Send the message to create the partner pool
			runtimeService.createMessageCorrelation("job_proposal")
			.processInstanceBusinessKey(partnerBusinessKey)					// business key of the new instance
			.setVariable("acmeBusinessKey", execution.getBusinessKey())		// business key to communicate with ACME
			.setVariable("processTenant", partnerNameWithoutWhitespaces)	// the username of partner in Camunda
			.setVariable("jobInformations", JSON(jobData.toJSON()))			// the informations about the job
			.correlate();
			
		}
						
		// Set loop cardinality for the parallel sub-process
		execution.setVariable("numberOfPartners", partners.getPartnerList().size());
		
		// Update partner list (contacted partners)
		execution.setVariable("partnerList", JSON(partners.toJSON()));
		
		// Update partner list (all partners)
		execution.setVariable("allPartners", JSON(allPartners.toJSON()));
	}
		
	/**
	 * Create a JSON with some job information from the client.
	 * Only for test.
	 * @return The JSON.
	 */
	@SuppressWarnings("unused")
	private String testCreateJSONJobOffer() {
		return "{"
				+ "\"name\": \"Client\", "
				+ "\"date\": \"01/01/2019\", "
				+ "\"address\":"
				+ "{"
					+ "\"country\": \"Italia\", "
					+ "\"city\": \"Ferrara\", "
					+ "\"street\": \"Via Garibaldi, 111\", "
					+ "\"postalCode\": \"44122\", "
					+ "\"distanceFromUserRequest\": 0.0" 
				+ "}"
				+ "}";
	}
}
