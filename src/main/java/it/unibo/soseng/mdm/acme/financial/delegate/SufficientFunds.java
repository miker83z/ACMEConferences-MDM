package it.unibo.soseng.mdm.acme.financial.delegate;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;
import it.unibo.soseng.mdm.services.django.Event;

/**
 * The Class SumBills, used for "Sum Bills" task to move bills present in otherBillsToPay to billsToPay.
 * @author Mirko Zichichi
 */
public class SufficientFunds implements JavaDelegate{
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		boolean sufFun = false;
		if (execution.hasVariable("djangoEventID")) {
			Double availableFunds = obtainAvailableFunds(execution);
			Double actualFunds = availableFunds - (Double) execution.getVariable("sumPayed") - (Double) execution.getVariable("sumReservedForManualPayment");
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
			if( !billsToPay.getBills().isEmpty() )
				sufFun = true;
		}		
		execution.setVariable("sufficientFunds", sufFun);
	}
	
	/**
	 * Obtain available funds from Registration Service.
	 *
	 * @return the double
	 */
	@SuppressWarnings("finally")
	private Double obtainAvailableFunds(DelegateExecution execution) {
		String token = (String) execution.getVariable("djangoToken");
		int eventID = (Integer) execution.getVariable("djangoEventID");
		double tmp = (Double) execution.getVariable("availableFunds");
		Event event = new Event(token, eventID);
		try {
			event.get();
			tmp = event.getAvailableMoney();
			execution.setVariable("availableFunds", tmp);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return tmp;
		}
	}
}
