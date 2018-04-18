package it.unibo.soseng.mdm.acme.catering.delegate;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.model.JobData;
import it.unibo.soseng.mdm.model.PartnerDatas;

public class SearchBestPartner implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Get the JSON variable from Camunda engine
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("allCatering");
		PartnerDatas catering = new PartnerDatas();
		catering.setPartnersFromJSON(jsonNode);
				
		// Retrieve job informations from Camunda
		SpinJsonNode jobJsonNode = (SpinJsonNode) execution.getVariable("jobInformations");
		JobData jobData = new JobData();
		jobData.setValueFromJSON(jobJsonNode);
		
		// Order partners by distance using GIS informations
		catering.orderPartnersList(jobData.getAddress());
		
		// Remove contacted partners
		catering.removeContactedPartners();
		
		// Leave only the nearest partner
		catering.cutPartnersList(1);
				
		// Set partner for catering
		execution.setVariable("cateringPartner", JSON(catering.getPartnerList().get(0).toJSON()));
	}

}
