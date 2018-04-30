package it.unibo.soseng.mdm.client.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The Class SendBills, used for "Send Bills" message event to send the bills to pay to ACME 
 * @author Mirko Zichichi
 */
public class SendBills implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
	    String businessKeyA = (String) execution.getVariable("businessKeyACME");
	    BillsCollection bills = (BillsCollection) execution.getVariable("clientBills");
	    if( !bills.getBills().isEmpty() ) {
	    	//Camunda switches randomly (or at least in a way not understood) between JSON serialization and XML serialization (causing problem in forms)
	    	ObjectValue typedBillsValue = Variables.objectValue(bills).serializationDataFormat("application/json").create();
	    	
			runtimeService.createMessageCorrelation("ClientBills")
			.processInstanceBusinessKey(businessKeyA)
			.setVariable("clientBillsToPay", typedBillsValue)
			.correlate();
	    }
	    else //Empty form
	    	runtimeService.createMessageCorrelation("NoClientBillsNotification")
			.processInstanceBusinessKey(businessKeyA)
			.correlate();
	  }
	  
}