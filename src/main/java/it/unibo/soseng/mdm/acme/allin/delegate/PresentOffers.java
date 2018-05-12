package it.unibo.soseng.mdm.acme.allin.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.PartnerCollection;

/**
 * Send all the partner's offers to the client.
 * 
 * @author Davide Marchi
 *
 */
public class PresentOffers implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {		
		// Get the variable from Camunda engine
		PartnerCollection partners = (PartnerCollection) execution.getVariable("contactedPartners");

		// Create a list with only the available and contacted partners
		partners.retrieveAvailablePartners();
		
		// Set JSON serialization for partners
		ObjectValue typedPartnerDatas = Variables.objectValue(partners).serializationDataFormat("application/json").create();
		
		// Send the message setting variables
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("offers")
	    .processInstanceBusinessKey((String) execution.getVariable("businessKeyClient"))
	    .setVariable("businessKeyACME", execution.getBusinessKey())
	    .setVariable("availablePartners", typedPartnerDatas)
	    .correlate();		
	    
	    // Set number of partners for the next parallel message task
	    execution.setVariable("numberOfPartners", partners.retrieveNumberOfPartners());
	}
	
}