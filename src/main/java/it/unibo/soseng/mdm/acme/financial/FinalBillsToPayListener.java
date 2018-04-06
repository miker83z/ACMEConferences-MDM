package it.unibo.soseng.mdm.acme.financial;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.Bill;
import it.unibo.soseng.mdm.acme.model.BillsCollection;

public class FinalBillsToPayListener  implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		execution.removeVariable("otherBillsToPay");
		Double availableFunds = obtainAvailableFunds();
		execution.setVariable("availableFunds", availableFunds);
		Double actualFunds = availableFunds- (Double) execution.getVariable("sumPayed");
		execution.setVariable("remainingFunds", false);
		execution.setVariable("onlyACMEBill", false);
		
		Bill ACMEBill = new Bill();
	    ACMEBill.setReceiver("ACME");
	    ACMEBill.setAmount(1000.0);
	    execution.setVariable("ACMEBill", ACMEBill);
		Double sumToPay = ACMEBill.getAmount();
		
		BillsCollection billsToPay = new BillsCollection();
		if(execution.hasVariable("billsToPay"))
			billsToPay = (BillsCollection) execution.getVariable("billsToPay");
		else
			execution.setVariable("billsToPay", billsToPay);
		
		boolean billsToPayFlag = false;
		if(!billsToPay.getBills().isEmpty()) {
			billsToPayFlag = true;
			for( Bill bill : billsToPay.getBills() )
				sumToPay += bill.getAmount();
			Double difference = sumToPay - actualFunds;
			Bill clientBill = new Bill();
			clientBill.setAmount(difference);
			clientBill.setReceiver("ACME");
			execution.setVariable("clientDebt", clientBill);
		}
		else {
			execution.setVariable("onlyACMEBill", true);
			//Remaining funds
			if( actualFunds - sumToPay >= 0 ) {
				execution.setVariable("sumPayed", (Double) execution.getVariable("sumPayed") + sumToPay); //Pay ACMEbill
				if( actualFunds - sumToPay > 0 ) {
					Bill remainingFundsBill = new Bill();
					remainingFundsBill.setReceiver( (String) execution.getVariable("clientId"));
					remainingFundsBill.setAmount(actualFunds - sumToPay);
					billsToPay.addBill(remainingFundsBill);
					execution.setVariable("remainingFunds", true);
				}
			}			
			else {
				billsToPayFlag = true;
				Bill clientBill = new Bill();
				clientBill.setAmount(sumToPay - actualFunds);
				clientBill.setReceiver("ACME");
				execution.setVariable("clientDebt", clientBill);
			}
		}
		execution.setVariable("billsToPayFlag", billsToPayFlag);
	}
	
	private Double obtainAvailableFunds() {
		//obtain from registration platform
		return 1000.0;
	}

}
