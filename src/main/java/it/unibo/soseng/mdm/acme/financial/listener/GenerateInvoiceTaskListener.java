package it.unibo.soseng.mdm.acme.financial.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;
import it.unibo.soseng.mdm.model.Invoice;

/**
 *The class GenerateInvoiceTaskListener, used to generate an invoice using global variables.
 *@author Mirko Zichichi
 */
public class GenerateInvoiceTaskListener implements TaskListener {
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.TaskListener#notify(org.camunda.bpm.engine.delegate.DelegateTask)
	 */
	public void notify(DelegateTask delegateTask) {
		BillsCollection billsPayed = (BillsCollection) delegateTask.getVariable("billsPayed");
		Double availableFunds = (Double) delegateTask.getVariable("availableFunds");
		Double acmeServicesCosts = ((Bill) delegateTask.getVariable("ACMEBill") ).getAmount();
		Double clientDebtPayed = 0.0;
		Double remainingFunds = 0.0;
		if( (boolean) delegateTask.getVariable("remainingFunds") )
			remainingFunds = (Double) delegateTask.getVariable("remainingFundsCount");
		if( delegateTask.hasVariable("clientDebt") )
			clientDebtPayed = ((Bill) delegateTask.getVariable("clientDebt")).getAmount();
		
		Bill remainingFundsBill = new Bill();
		if( delegateTask.hasVariable("remainingFundsBill") )
			remainingFundsBill = (Bill) delegateTask.getVariable("remainingFundsBill");
		BillsCollection tmp = new BillsCollection();
		for (Bill bill : billsPayed.getBills())
			if(!bill.equals(remainingFundsBill))
				tmp.addBill(bill);
		
		Invoice invoice = new Invoice(tmp, availableFunds, clientDebtPayed, acmeServicesCosts, remainingFunds);
		ObjectValue typedInvoiceValue = Variables.objectValue(invoice).serializationDataFormat("application/json").create();
		delegateTask.setVariable("ACMEInvoice", typedInvoiceValue);		
	}
}
