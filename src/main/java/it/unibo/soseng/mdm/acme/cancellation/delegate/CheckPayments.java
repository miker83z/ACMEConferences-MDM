package it.unibo.soseng.mdm.acme.cancellation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;
/**
 * The Class CheckPayments, used in task "Check All Payments" to compute the sum of payments done and debts, in order to receive a payment from the client.
 * @author Mirko Zichichi
 */
public class CheckPayments implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		boolean clientHasDebt = false;
		boolean stillBillsToPay = false;
		double ACMEServiceBillAmount = (Double) execution.getVariable("ACMEServiceBillAmount");
		Bill clientDebtBill = new Bill();
		clientDebtBill.setReceiver("ACME");
		BillsCollection payedBills = (BillsCollection) execution.getVariable("billsPayed");
		BillsCollection billsToPay = (BillsCollection) execution.getVariable("billsToPay");
		if( !((boolean)execution.getVariable("clientDebtPayedCancFlag"))  ) { //If client hasn't payed any debt yet
			if( !payedBills.hasBills() ) //If any bills has been payed, client has only to pay ACME Service bill
				clientDebtBill.setAmount(ACMEServiceBillAmount);
			else {
				double sum = ACMEServiceBillAmount;
				for (Bill bill : payedBills.getBills())
					sum += bill.getAmount();
				clientDebtBill.setAmount(sum);
			}
			clientHasDebt = true;
		}
		else { //if client has already payed debt before
			Bill clientDebtPayed = (Bill) execution.getVariable("clientDebt");
			double clientPayedForBills = clientDebtPayed.getAmount() - ACMEServiceBillAmount;
			double sum = 0.0;
			for (Bill bill : payedBills.getBills())
				sum += bill.getAmount();
			sum -= clientPayedForBills; //sum payed for bills by ACME through registration founds
			if ( sum > 0.1 ) { // != 0
				clientDebtBill.setAmount(sum);
				clientHasDebt = true;
			} //else no debt
			
			if( billsToPay.hasBills() ) //pay remaining bills if there are some
				stillBillsToPay = true;
		}

		execution.setVariable("clientHasDebt", clientHasDebt);		
		if(clientHasDebt)
			execution.setVariable("clientDebt", clientDebtBill);
		execution.setVariable("stillBillsToPay", stillBillsToPay);
	}
	
}
	  



