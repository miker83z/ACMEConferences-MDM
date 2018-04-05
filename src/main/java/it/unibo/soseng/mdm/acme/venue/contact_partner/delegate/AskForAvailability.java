package it.unibo.soseng.mdm.acme.venue.contact_partner.delegate;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.venue.model.JobData;
import it.unibo.soseng.mdm.acme.venue.model.PartnerDatas;
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
		
		// Get my id 
		Integer id = (Integer) execution.getVariable("loopCounter");
		
		// FIXME: mettere username e password per il partner
		// Create and send an email to the partner
		String emailMessage = "Dear " + partners.getPartnerList().get(id).getName() + ",\n"
				+ "\n"
				+ "We would like to formally offer you a job for " + jobData.getClientName() + ", the starting date is " + jobData.getDate() + ".\n"
				+ "\n" 
				+ "We would like to have your response by tomorrow.\n"
				+ "Subscribe to our platform http://localhost:8080/camunda/tasklist/default/, your JOB-ID is " + id + ".\n"
				+ "In the meantime, please feel free to contact us via email on provecamundaisos@gmail.com.\n"
				+ "\n"
				+ "\n"
				+ "Best regards,\n"
				+ "Demo demo\n"
				+ "President of ACME Conferences";
	
		emailSender.send(partners.getPartnerList().get(id).getName() + " - " + EMAIL_SUBJECT, emailMessage, partners.getPartnerList().get(id).getEmail());
		
		// Set the partner as "contacted" (all partners list)
		Integer index = allPartners.indexOf(partners.getPartnerList().get(id).getName());
		System.out.println("[PROVA] INDEX: " + String.valueOf(index));
		System.out.println("[PROVA] PARTNER: " + allPartners.getPartnerList().get(index).getName());
		System.out.println("[PROVA] REAL: " + partners.getPartnerList().get(id).getName());
		allPartners.getPartnerList().get(index).setContacted(true);
		
		// Set the partner as "contacted" (contacted partners list)
		partners.getPartnerList().get(id).setContacted(true);
		
		// Set the new partner businessKey
		String partnerNameWithoutWhitespaces = partners.getPartnerList().get(id).getNameWithoutWhitespaces();
		String partnerBusinessKey = RandomAlphanumericString.generate();
		execution.setVariable(partnerNameWithoutWhitespaces + "BusinessKey", partnerBusinessKey);
					
		// Send the message to create the partner pool
		runtimeService.createMessageCorrelation("job_proposal")
		.processInstanceBusinessKey(partnerBusinessKey)					// business key of the new instance
		.setVariable("acmeBusinessKey", execution.getBusinessKey())		// business key to communicate with ACME
		.setVariable("processTenant", partnerNameWithoutWhitespaces)	// the username of partner in Camunda
		.setVariable("jobInformations", JSON(jobData.toJSON()))			// the informations about the job
		.correlate();
					
		// Update partner list (contacted partners)
		execution.setVariable("partnerList", JSON(partners.toJSON()));
		
		// Update partner list (all partners)
		execution.setVariable("allPartners", JSON(allPartners.toJSON()));

	}

}
