package it.unibo.soseng.mdm.acme.financial.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.Invoice;

/**
 * The Class SendACMEInvoice, used for "Send ACME Invoice" message event to send to the client the ACME Service invoice.
 * @author Mirko Zichichi
 */
public class SendACMEInvoice implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    Invoice invoice = (Invoice) execution.getVariable("ACMEInvoice");
	    ObjectValue typedInvoiceValue = Variables.objectValue(invoice).serializationDataFormat("application/json").create();
	    
	    runtimeService.createMessageCorrelation("ACMEInvoice")
			.processInstanceBusinessKey((String) execution.getVariable("businessKeyClient"))
			.setVariable("ACMEInvoice", typedInvoiceValue)
			.correlate();
	  }
	  
}