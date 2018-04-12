package it.unibo.soseng.mdm.acme.venue.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class PartnerAvailableListener implements ExecutionListener {
		
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// Get my id
		Integer id = (Integer) execution.getVariable("loopCounter");

		// Get job estimate
		Double jobEstimate = (Double) execution.getVariable("jobEstimate");
		
		/*
		 * STEP 1:
		 * 	Ricavo ed aggiorna la lista con le informazioni TUTTI I PARTNERS.
		 *  Questa variabile è quella che utilizzo nel caso in cui l'utente non accetti
		 *  nessuna delle offerte ricevute e voglia vederne altre: da questa lista di
		 *  partner posso controllare se tutti i partners sono stati contattati oppure no.
		 */
		// Get the full partner list
		PartnerDatas partners = (PartnerDatas) execution.getVariable("allPartners");
		
		// Update partner values
		partners.getPartnerList().get(id).setAvailable(true);
		partners.getPartnerList().get(id).setPrice(jobEstimate);
		
		// Update partner list
		execution.setVariable("allPartners", partners);
		
		/*
		 * STEP 2:
		 * 	Ricavo ed aggiorno la lista dei soli PARTNERS CONTATTATI.
		 * 	Questa variabile è quella che passerò poi alla pool del Client per mostrargli
		 *  i preventivi dei soli partners contattati e disponibili.
		 */
		// Get the contacted partner list
		PartnerDatas contactedPartners = (PartnerDatas) execution.getVariable("contactedPartners");
		
		// Update partner values
		contactedPartners.getPartnerList().get(id).setAvailable(true);
		contactedPartners.getPartnerList().get(id).setPrice(jobEstimate);
		
		// Update partner list
		execution.setVariable("contactedPartners", contactedPartners);
	}

}
