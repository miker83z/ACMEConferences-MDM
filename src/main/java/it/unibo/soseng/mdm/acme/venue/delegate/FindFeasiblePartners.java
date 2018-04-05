package it.unibo.soseng.mdm.acme.venue.delegate;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.venue.model.JobData;
import it.unibo.soseng.mdm.acme.venue.model.PartnerDatas;

public class FindFeasiblePartners implements JavaDelegate {
	
	private static final Integer NUMBER_OF_PARTNERS = 4;
	
	public void execute(DelegateExecution execution) throws Exception {		
		// Get the JSON variable from Camunda engine
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("allPartners");
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);
				
		// Retrieve job informations from Camunda
		SpinJsonNode jobJsonNode = (SpinJsonNode) execution.getVariable("jobInformations");
		JobData jobData = new JobData();
		jobData.setValueFromJSON(jobJsonNode);
		
		// Order partners by distance using GIS informations
		partners.orderPartnersList(jobData.getAddress());
		
		// Remove contacted partners
		partners.removeContactedPartners();
		
		// Leave only the two nearest partners
		partners.cutPartnersList(NUMBER_OF_PARTNERS);
		
		// Set partner list
		execution.setVariable("partnerList", JSON(partners.toJSON()));
	}
		
}