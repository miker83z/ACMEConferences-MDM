package it.unibo.soseng.mdm.acme.venue.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.acme.model.ConferenceData;
import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class LoadDataFromCSV implements ExecutionListener {

	// Values for the CSV reader
	private static final String CSV_FILENAME_PARTNERS = "/data/partner-list.csv";
	private static final String CSV_SPLIT_BY_PARTNERS = ";";
	private static final String CSV_FILENAME_CATERING = "/data/catering-list.csv";
	private static final String CSV_SPLIT_BY_CATERING = ";";
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
			
		// Flag: false = load venue; true = load catering
		Boolean itsCateringTime = (Boolean) execution.getVariable("itsCateringTime");
		
		// Retrieve partners informations from CSV
		PartnerDatas partners = new PartnerDatas();
		
		if (!itsCateringTime) {
			// Set partner list
			partners.definePartnersListFromCSV(CSV_FILENAME_PARTNERS, CSV_SPLIT_BY_PARTNERS);
		} 
		else {
			// Set catering list
			partners.definePartnersListFromCSV(CSV_FILENAME_CATERING, CSV_SPLIT_BY_CATERING);
		}
			
		execution.setVariable("allPartners", partners);
		
		// TODO: rimuovere queste righe al momento del merge, in teoria lo fa già Mirko precedentemente
		ConferenceData conference = (ConferenceData) execution.getVariable("conferenceData");
		ObjectValue typedConferenceData = Variables.objectValue(conference).serializationDataFormat("application/json").create();
		execution.setVariable("conferenceData", typedConferenceData);
		
	}

}
