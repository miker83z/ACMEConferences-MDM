package it.unibo.soseng.mdm.partner.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Refuse the received job offer.
 * 
 * @author Davide Marchi
 *
 */
public class RefuseJob implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Get my partnerName
		String partnerName = (String) execution.getVariable("processTenant");
				
		// Send message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("partner_not_available_" + partnerName)
	    .processInstanceBusinessKey((String) execution.getVariable("acmeBusinessKey"))
	    .correlate();	
	}

}
