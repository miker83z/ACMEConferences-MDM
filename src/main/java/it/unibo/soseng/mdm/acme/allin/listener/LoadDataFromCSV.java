package it.unibo.soseng.mdm.acme.allin.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.model.PartnerCollection;

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
		PartnerCollection partners = new PartnerCollection();
		
		if (!itsCateringTime) {
			// Set partner list
			partners.definePartnersListFromCSV(CSV_FILENAME_PARTNERS, CSV_SPLIT_BY_PARTNERS);
		} 
		else {
			// Set catering list
			partners.definePartnersListFromCSV(CSV_FILENAME_CATERING, CSV_SPLIT_BY_CATERING);
		}
			
		execution.setVariable("allPartners", partners);		
	}

}
