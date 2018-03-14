package it.unibo.soseng.mdm.acme.venue.contactpartner;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.acme.venue.model.PartnerData;
import static org.camunda.spin.Spin.JSON;

public class AskForAvailability implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {		
		/* TODO (??):
		 * 	1) Retrieve informations about conference
		 * 	2) Retrieve a partner from the list using my loopCounter ID
		 * 	3) Create the message for partner
		 * 	4) Send the message
		 */

		// (??) Retrieve informations about the conference
		String conferenceInformations = getConferenceInformations();
		
		// Retrieve the partnerList setted before
		List<PartnerData> partnerList = new ArrayList<>();
		ObjectValue typedPartnerList = execution.getVariableTyped("partnerList");
		String JSONpartnerList = typedPartnerList.getValueSerialized();
		partnerList = JSON(JSONpartnerList).mapTo("java.util.ArrayList<it.unibo.soseng.mdm.acme.venue.model.PartnerData>");
				
		// Retrieve my squential_process_ID 
		Integer myID = (Integer) execution.getVariable("loopCounter");
		
		// Send the message setting variables
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("ask_for_availability_" + partnerList.get(myID).getName())
	    .processInstanceBusinessKey("AB-123")
	    .setVariable("partnerName", partnerList.get(myID).getName())
	    .setVariable("conferenceInformations", conferenceInformations)
	    .correlate();
	    
	    // TODO!!! 
	    // Su Camunda devo lanciare N processi partner
	    // Però prima, devo capire come gestire "messageCorrelation" per istanze multiple
	}
	
	private String getConferenceInformations() {
		return "some informations?";
	}
}