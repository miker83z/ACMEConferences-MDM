package it.unibo.soseng.mdm.client;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.model.BillsCollection;

public class SendBills implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
	    String businessKeyA = (String) execution.getVariable("businessKeyACME");
	    BillsCollection bills = (BillsCollection) execution.getVariable("clientBills");
	    if( !bills.getBills().isEmpty() )
			runtimeService.createMessageCorrelation("ClientBills")
			.processInstanceBusinessKey(businessKeyA)
			.setVariable("billsToPay", bills)
			.correlate();
	    else
	    	runtimeService.createMessageCorrelation("NoClientBillsNotification")
			.processInstanceBusinessKey(businessKeyA)
			.correlate();
	  }
	  
}