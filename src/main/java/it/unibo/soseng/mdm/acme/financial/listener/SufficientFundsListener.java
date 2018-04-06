package it.unibo.soseng.mdm.acme.financial.listener;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.Bill;
import it.unibo.soseng.mdm.acme.model.BillsCollection;

public class SufficientFundsListener implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		Double actualFunds = obtainAvailableFunds() - (Double) execution.getVariable("sumPayed");
		Double sumToPay = 0.0;
		
		BillsCollection billsToPay = new BillsCollection();
		if(execution.hasVariable("billsToPay"))
			billsToPay = (BillsCollection) execution.getVariable("billsToPay");
		BillsCollection otherBillsToPay;
		boolean flag = execution.hasVariable("otherBillsToPay");
		if(flag)
			otherBillsToPay = (BillsCollection) execution.getVariable("otherBillsToPay");
		else {
			otherBillsToPay = new BillsCollection();
			execution.setVariable("otherBillsToPay", otherBillsToPay);
		}
			
		for( Bill bill : billsToPay.getBills() )
			if( bill.getAmount() + sumToPay > actualFunds )
				otherBillsToPay.addBill(bill);
			else
				sumToPay += bill.getAmount();
		for( Bill bill : otherBillsToPay.getBills() )
			billsToPay.removeBill(bill);
		if(flag) {
			ArrayList<Bill> tmp = new ArrayList<Bill>();
			for( Bill bill : otherBillsToPay.getBills() )
				if( bill.getAmount() + sumToPay <= actualFunds ) {
					billsToPay.addBill(bill);
					sumToPay += bill.getAmount();
					tmp.add(bill);
				}
			for(Bill bill : tmp)
				otherBillsToPay.removeBill(bill);
		}
		
		boolean sufFun = false;
		if( billsToPay.getBills().size() > otherBillsToPay.getBills().size() || (billsToPay.getBills().size() > 0 && (Boolean) execution.getVariable("subscriptionClosed")) ) 
			sufFun = true;
		execution.setVariable("sufficientFunds", sufFun);
	}
	
	private Double obtainAvailableFunds() {
		//obtain from registration platform
		return 1000.0;
	}

}
