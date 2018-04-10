package it.unibo.soseng.mdm.acme.venue.listener;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.spin.json.SpinJsonNode;

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
		// FIXME: togliere JSON
		// Get the full partner list
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("allPartners");		
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);
		
		// Update partner values
		partners.getPartnerList().get(id).setAvailability(true);
		partners.getPartnerList().get(id).setPrice(jobEstimate);
		
		// FIXME: togliere JSON
		// Update partner list
		execution.setVariable("allPartners", JSON(partners.toJSON()));
		
		/*
		 * STEP 2:
		 * 	Ricavo ed aggiorno la lista dei soli PARTNERS CONTATTATI.
		 * 	Questa variabile è quella che passerò poi alla pool del Client per mostrargli
		 *  i preventivi dei soli partners contattati e disponibili.
		 */
		// FIXME: togliere JSON
		// Get the contacted partner list
		SpinJsonNode contactedJsonNode = (SpinJsonNode) execution.getVariable("contactedPartners");		
		PartnerDatas contactedPartners = new PartnerDatas();
		contactedPartners.setPartnersFromJSON(contactedJsonNode);
		
		// Update partner values
		contactedPartners.getPartnerList().get(id).setAvailability(true);
		contactedPartners.getPartnerList().get(id).setPrice(jobEstimate);
		
		// FIXME: togliere JSON
		// Update partner list
		execution.setVariable("contactedPartners", JSON(contactedPartners.toJSON()));
	}

}
