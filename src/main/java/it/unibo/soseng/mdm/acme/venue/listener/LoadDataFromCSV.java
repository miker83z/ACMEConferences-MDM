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
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
				
		// Retrieve partners informations from CSV
		PartnerDatas partners = new PartnerDatas();
		partners.definePartnersListFromCSV(CSV_FILENAME_PARTNERS, CSV_SPLIT_BY_PARTNERS);
			
		// Set partner list
		execution.setVariable("allPartners", partners);
		
		// TODO: rimuovere queste righe al momento del merge, in teoria lo fa già Mirko precedentemente
		ConferenceData conference = (ConferenceData) execution.getVariable("conferenceData");
		ObjectValue typedConferenceData = Variables.objectValue(conference).serializationDataFormat("application/json").create();
		execution.setVariable("conferenceData", typedConferenceData);
		
	}

}
