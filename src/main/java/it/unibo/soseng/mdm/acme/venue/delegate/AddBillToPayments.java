package it.unibo.soseng.mdm.acme.venue.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.model.Bill;
import it.unibo.soseng.mdm.acme.model.BillsCollection;
import it.unibo.soseng.mdm.acme.model.PartnerData;

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
		
		// TODO: controllare insieme a Mirko se questi valori sono settati correttamente
		execution.setVariable("partnerBillsToPay", true);
		
		Bill bill = new Bill();
		bill.setReceiver(partner.getName());
		bill.setAmount(partner.getPrice());
		
		BillsCollection billsCollection = new BillsCollection();
		billsCollection.addBill(bill);
		
		execution.setVariable("otherBillsToPay", billsCollection);
	}

}
