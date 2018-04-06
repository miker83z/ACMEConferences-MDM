package it.unibo.soseng.mdm.acme.financial.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.model.Bill;

public class SendNoBillsNotificationAndRemainingFundsInfo implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    Bill clientBill = (Bill) execution.getVariable("clientDebt");
	    
	    //Search for remaining funds
	    //Send Info
	    runtimeService.createMessageCorrelation("NoBillsToPayAndRemainingFundsInfo")
			.processInstanceBusinessKey((String) execution.getVariable("businessKeyClient"))
			.setVariable("ACMEBillToPay", clientBill)
			.correlate();
	  }
	  
}