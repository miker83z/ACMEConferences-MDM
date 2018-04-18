package it.unibo.soseng.mdm.acme.catering.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.model.PartnerData;

public class AddBillToPayments implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {		
		// Get the JSON variable from Camunda engine
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("cateringPartner");
		PartnerData catering = new PartnerData();
		catering.setValueFromJSON(jsonNode);
		
		// Add price to bills
		// FIXME: Ma questi "bills" dove li salvo? Creiamo un file temporaneo?
		
	}

}
