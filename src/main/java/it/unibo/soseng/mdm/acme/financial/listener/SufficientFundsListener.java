package it.unibo.soseng.mdm.acme.financial.listener;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The class SufficientFundsListener, used for "Sufficient Funds?" gateway to check whether there are sufficient funds coming from the registration 
 * platform to pay bills in billsToPay and (if present) bills in otherBillsToPay
 * @author Mirko Zichichi
 */
public class SufficientFundsListener implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		Double actualFunds = obtainAvailableFunds() - (Double) execution.getVariable("sumPayed") - (Double) execution.getVariable("sumReservedForManualPayment");
		Double sumToPay = 0.0;
		
		//Retrieve billsToPay
		BillsCollection billsToPay = new BillsCollection();
		if(execution.hasVariable("billsToPay"))
			billsToPay = (BillsCollection) execution.getVariable("billsToPay");
		else {
			ObjectValue typedBillsValue = Variables.objectValue(billsToPay).serializationDataFormat("application/json").create();
			execution.setVariable("billsToPay", typedBillsValue);
		}

		//Retrieve otherBillsToPay or create a new one
		BillsCollection otherBillsToPay = new BillsCollection();
		boolean hasOtherBillsToPay = execution.hasVariable("otherBillsToPay");
		if(hasOtherBillsToPay)
			otherBillsToPay = (BillsCollection) execution.getVariable("otherBillsToPay");
		ObjectValue typedOBillsValue = Variables.objectValue(otherBillsToPay).serializationDataFormat("application/json").create();
		execution.setVariable("otherBillsToPay", typedOBillsValue);
		
		//Select bills in billsToPay available to be payed
		ArrayList<Bill> tmp = new ArrayList<Bill>();
		for( Bill bill : billsToPay.getBills() )
			if( bill.getAmount() + sumToPay > actualFunds ) {
				otherBillsToPay.addBill(bill);
				tmp.add(bill);
			}
			else
				sumToPay += bill.getAmount();
		for( Bill bill : tmp )
			billsToPay.removeBill(bill);
		tmp.clear();
		//Select bills in otherBillsToPay available to be payed
		if(hasOtherBillsToPay) {
			for( Bill bill : otherBillsToPay.getBills() )
				if( bill.getAmount() + sumToPay <= actualFunds ) {
					billsToPay.addBill(bill);
					sumToPay += bill.getAmount();
					tmp.add(bill);
				}
			for(Bill bill : tmp)
				otherBillsToPay.removeBill(bill);
		}
		
		//Check and set sufficientFunds 
		boolean sufFun = false;
		if( !billsToPay.getBills().isEmpty() )
			sufFun = true;
		execution.setVariable("sufficientFunds", sufFun);
	}
	
	/**
	 * Obtain available funds from Ragistration Service.
	 *
	 * @return the double
	 */
	private Double obtainAvailableFunds() {
		//obtain from registration platform
		return 1000.0;
	}

}
