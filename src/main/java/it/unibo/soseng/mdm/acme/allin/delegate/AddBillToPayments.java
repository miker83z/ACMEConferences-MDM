package it.unibo.soseng.mdm.acme.allin.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.PartnerData;
import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;

public class AddBillToPayments implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {		
		// Venue/catering flag
		Boolean itsCateringTime = (Boolean) execution.getVariable("itsCateringTime");
		
		// Get the available partner
		PartnerData partner;
		if (!itsCateringTime) {
			partner = (PartnerData) execution.getVariable("chosenPartner");
		}
		else {
			partner = (PartnerData) execution.getVariable("cateringPartner");
		}
		
		execution.setVariable("partnerBillsToPay", true);
		
		Bill bill = new Bill();
		bill.setReceiver(partner.retrieveNameWithoutWhitespaces());
		bill.setAmount(partner.getPrice());
		
		BillsCollection otherBillsToPay = new BillsCollection();
		if( execution.hasVariable("otherBillsToPay") ) {
			otherBillsToPay = (BillsCollection) execution.getVariable("otherBillsToPay");
			otherBillsToPay.addBill(bill);
		}
		else {
			otherBillsToPay.addBill(bill);
			execution.setVariable("otherBillsToPay", otherBillsToPay);
		}
	}

}
