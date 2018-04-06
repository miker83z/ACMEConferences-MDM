package it.unibo.soseng.mdm.acme.venue;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.venue.model.PartnerData;

public class AddBillToPayments implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		/* TODO:
		 * 	1) Retrieve 'chosenPartner' variable from Camunda engine
		 *  2) Add 'chosenPartner.price' to bills
		 */
		
		// Get the JSON variable from Camunda engine
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("chosenPartner");
		
		// Convert the JSON to a PartnerData object
		PartnerData partner = new PartnerData();
		partner.setValueFromJSON(jsonNode);
		
		// Add price to bills
		// FIXME: Ma questi "bills" dove sono? Li salviamo in un qualche DB o qualcosa di persistente?		
		
	}

}
