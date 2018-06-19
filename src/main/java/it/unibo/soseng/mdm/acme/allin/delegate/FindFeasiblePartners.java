package it.unibo.soseng.mdm.acme.allin.delegate;

import javax.xml.ws.WebServiceException;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.PartnerCollection;
import it.unibo.soseng.mdm.model.ConferenceData;
import it.unibo.soseng.mdm.model.Address;
import it.unibo.soseng.mdm.util.EmailSender;

/**
 * Check if there are some feasible partners for venue or catering.
 * The partners are ordered by distance using GIS external services.
 * If we are in the Venue Lane, only the nearest 3 partners are contacted, 
 * else only the nearest one is contacted.
 * 
 * In this class we also send an e-mail to partners.
 * 
 * @author Davide Marchi
 *
 */
public class FindFeasiblePartners implements JavaDelegate {
	
	private static final Integer NUMBER_OF_PARTNERS = 3;
	
	// Values for email
	private static final String EMAIL_USERNAME = "provecamundaisos@gmail.com";
	private static final String EMAIL_PASSWORD = "camundaisos";
	private static final String EMAIL_NAME = "ACME Conferences";
	private static final String EMAIL_SUBJECT = "Job offer from ACME Conferences";
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {		
		// Venue/catering flag
		Boolean itsCateringTime = (Boolean) execution.getVariable("itsCateringTime");
		
		// Get the JSON variable from Camunda engine
		PartnerCollection allPartners = (PartnerCollection) execution.getVariable("allPartners");
		PartnerCollection partners = new PartnerCollection();
		partners.setPartnerList(allPartners.getPartnerList());
		
		// Remove contacted partners
		partners.removeContactedPartners();
		
		// Retrieve job informations from Camunda
		ConferenceData conference = (ConferenceData) execution.getVariable("conferenceData");
		
		// Set gateway variable and continue
		if (partners.retrieveNumberOfPartners() > 0) {
			execution.setVariable("remaining_partners", true);

			/*
			try {
				// Order partners from distance to the conference location
				partners.orderPartnersList(conference.getAllinLocation());
			} catch (WebServiceException e) {
				e.printStackTrace();
				throw new BpmnError("WEB_SERVICE_ERROR");
			}
			*/
			// Leave only the nearest partners
			if (!itsCateringTime) {
				try {
					// Order partners from distance to the conference location
					partners.orderPartnersList(conference.getAllinLocation());
				} catch (WebServiceException e) {
					e.printStackTrace();
					throw new BpmnError("WEB_SERVICE_ERROR");
				}
				// Leave only the nearest partners
				partners.cutPartnersList(NUMBER_OF_PARTNERS);
				// Set loop cardinality for the parallel sub-process
				execution.setVariable("numberOfPartners", partners.getPartnerList().size());
				// Set partner list
				execution.setVariable("contactedPartners", partners);
			}
			else {
				// Retrieve the location of the selected partner
				Address chosenPartnerLocation = (Address) execution.getVariable("chosenPartnerLocation");
				try {
					// Order partners from distance to the venue partner location
					partners.orderPartnersList(chosenPartnerLocation);
				} catch (WebServiceException e) {
					e.printStackTrace();
					throw new BpmnError("WEB_SERVICE_ERROR");
				}
				// Leave only the nearest catering partner
				partners.cutPartnersList();
				// Set partner for catering
				execution.setVariable("cateringPartner", partners.getPartnerList().get(0));
			}
		} 
		// End
		else {			
			// Create a connection between Camunda and Google Mail server
			EmailSender emailSender = new EmailSender(EMAIL_USERNAME, EMAIL_PASSWORD, EMAIL_NAME);
			emailSender.configureConnection();
			
			// Create and send an email to the partner
			String emailMessage = "Dear client,\n"
					+ "\n"
					+ "Sorry there aren't available partners for this date.\n"
					+ "\n"
					+ "\n"
					+ "Best regards,\n"
					+ "Demo demo\n"
					+ "President of ACME Conferences";
		
			String clientEmail = "provecamundaisos@gmail.com";
			emailSender.send(EMAIL_SUBJECT, emailMessage, clientEmail);
			
			// Set gateway variable
			execution.setVariable("remaining_partners", false);
		}
	}
}