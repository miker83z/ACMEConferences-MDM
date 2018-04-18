package it.unibo.soseng.mdm.acme.financial.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The class FinalBillsToPayListener, used for "Bills to pay?" gateway to verify Camunda variables billsToPayFlag, remainingFunds and onlyACMEBill.
 * @author Mirko Zichichi
 */
public class FinalBillsToPayListener implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		execution.removeVariable("otherBillsToPay");	//Cleaning
		
		//Compute the actual funds state
		Double availableFunds = obtainAvailableFunds();	//From Registration service
		execution.setVariable("availableFunds", availableFunds);
		Double actualFunds = availableFunds - (Double) execution.getVariable("sumPayed");
		execution.setVariable("remainingFunds", false);
		
		//Used for next gateway
		execution.setVariable("onlyACMEBill", false);
		
		//Generate ACME Bill
		Bill ACMEBill = new Bill();
	    ACMEBill.setReceiver("ACME");
	    ACMEBill.setAmount(500.0);
		ObjectValue value = Variables.objectValue(ACMEBill).serializationDataFormat("application/json").create();
	    execution.setVariable("ACMEBill", value);
		Double sumToPay = ACMEBill.getAmount();
		
		//Remeining bills to pay
		BillsCollection billsToPay = new BillsCollection();
		if(execution.hasVariable("billsToPay"))
			billsToPay = (BillsCollection) execution.getVariable("billsToPay");
		else
			execution.setVariable("billsToPay", billsToPay);
		
		boolean billsToPayFlag = false;
		//There are bills to pay (considering ACME bill as a bill to pay)
		if(!billsToPay.getBills().isEmpty()) {
			billsToPayFlag = true;
			for( Bill bill : billsToPay.getBills() )
				sumToPay += bill.getAmount();
			Double difference = sumToPay - actualFunds;
			//Generate bill for client debt
			Bill clientBill = new Bill();
			clientBill.setAmount(difference);
			clientBill.setReceiver("ACME");
			execution.setVariable("clientDebt", clientBill);
		}
		//There are NO bills to pay (but funds could be not sufficient to pay ACME bill)
		else {
			execution.setVariable("onlyACMEBill", true);
			//Funds sufficient for ACME bill then compute Remaining funds
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
			//Funds not sufficient to pay ACME bill then request payment from client
			else {
				billsToPayFlag = true;
				Bill clientBill = new Bill();
				clientBill.setAmount(sumToPay - actualFunds);
				clientBill.setReceiver("ACME");
				ObjectValue value2 = Variables.objectValue(clientBill).serializationDataFormat("application/json").create();
				execution.setVariable("clientDebt", value2);
			}
		}
		execution.setVariable("billsToPayFlag", billsToPayFlag);
	}
	
	/**
	 * Obtain available funds from Registration service.
	 *
	 * @return the double
	 */
	private Double obtainAvailableFunds() {
		//obtain from registration platform
		return 1000.0;
	}

}
