package it.unibo.soseng.mdm.client.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;

import it.unibo.soseng.mdm.model.ConferenceData;
import it.unibo.soseng.mdm.util.RandomAlphanumericString;

/**
 * The Class SendConferenceData, used for "Send Conference Data" message event to send the conference data the client submitted.
 * @author Mirko Zichichi
 */
public class SendConferenceData implements JavaDelegate {
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
	    ConferenceData conferenceData = (ConferenceData) execution.getVariable("conferenceData");
	    execution.setVariable("confLastDayPlus60", new DateTime(conferenceData.getDates().get(conferenceData.getDates().size()-1)).plusDays(60).toDate() );
	    String clientId = (String) execution.getVariable("processInstantiator");	//Send to ACME the client id
	    //Generate a Business key for ACME process
	    String businessKeyA = RandomAlphanumericString.generate();
	    execution.setVariable("businessKeyACME", businessKeyA);
	    
	    runtimeService.createMessageCorrelation("ConferenceData")
		.processInstanceBusinessKey(businessKeyA)
		.setVariable("businessKeyClient", execution.getBusinessKey())
		.setVariable("conferenceData", conferenceData)
		.setVariable("clientId", clientId)
		.correlate();         
	  }
	  
}
