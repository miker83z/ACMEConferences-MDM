package it.unibo.soseng.mdm.acme.allin.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.model.PartnerCollection;
import it.unibo.soseng.mdm.model.PartnerData;

/**
 * This Listener is used to update all the Camunda's variables when a partner is available.
 * In this class we update the information about the contacted partners and the list of
 * all partners: the first is used to know which are the contacted partners in this moment,
 * the second is used to know which partners are already been contacted in the case in 
 * which all the partners refuse the offers or in the case in which the client refuse all 
 * the presented offers.
 * 
 * @author Davide Marchi
 *
 */
public class PartnerAvailableListener implements ExecutionListener {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// Venue/catering flag
		Boolean itsCateringTime = (Boolean) execution.getVariable("itsCateringTime");
		
		// Get job estimate
		Double jobEstimate = (Double) execution.getVariable("jobEstimate");

		// Get max seats
		Integer maxSeats = (Integer) execution.getVariable("maxSeats");

		// Get the full partner list
		PartnerCollection partners = (PartnerCollection) execution.getVariable("allPartners");	
		
		if (!itsCateringTime) {
			// Get my id
			Integer id = (Integer) execution.getVariable("loopCounter");
			
			/*
			 * STEP 1:
			 * 	Ricavo ed aggiorno la lista dei soli PARTNERS CONTATTATI.
			 * 	Questa variabile è quella che passerò poi alla pool del Client per mostrargli
			 *  i preventivi dei soli partners contattati e disponibili.
			 */
			// Get the contacted partner list
			PartnerCollection contactedPartners = (PartnerCollection) execution.getVariable("contactedPartners");
			
			// Update partner values
			contactedPartners.getPartnerList().get(id).setAvailable(true);
			contactedPartners.getPartnerList().get(id).setPrice(jobEstimate);
			contactedPartners.getPartnerList().get(id).setMaxSeats(maxSeats);
			
			// Update partner list
			execution.setVariable("contactedPartners", contactedPartners);
			
			/*
			 * STEP 2:
			 * 	Ricavo ed aggiorna la lista con le informazioni TUTTI I PARTNERS.
			 *  Questa variabile è quella che utilizzo nel caso in cui l'utente non accetti
			 *  nessuna delle offerte ricevute e voglia vederne altre: da questa lista di
			 *  partner posso controllare se tutti i partners sono stati contattati oppure no.
			 */
			// Update partner values
			partners.getPartnerList().get(id).setAvailable(true);
			partners.getPartnerList().get(id).setPrice(jobEstimate);
			partners.getPartnerList().get(id).setMaxSeats(maxSeats);

		}
		else {
			
			/* 
			 * STEP 1:
			 * 	Aggiorno il partner contattato.
			 */
			// Get the contacted partner
			PartnerData contactedPartner = (PartnerData) execution.getVariable("cateringPartner");
						
			// Update partner values
			contactedPartner.setAvailable(true);
			contactedPartner.setPrice(jobEstimate);
			
			// Update partner
			execution.setVariable("cateringPartner", contactedPartner);
			
			/*
			 * STEP 2:
			 * 	Aggiorno la lista di tutti i partner.
			 */
			
			// Get the id of the contacted partner
			Integer index = partners.indexOf(contactedPartner.getName());

			// Update partner values
			partners.getPartnerList().get(index).setAvailable(true);
			partners.getPartnerList().get(index).setPrice(jobEstimate);
			
			// Set gateway variable
			execution.setVariable("catering_available", true);	
		}
		
		// Update partner list
		execution.setVariable("allPartners", partners);
		
	}

}
