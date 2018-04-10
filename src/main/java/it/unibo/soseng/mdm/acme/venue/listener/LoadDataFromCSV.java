package it.unibo.soseng.mdm.acme.venue.listener;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class LoadDataFromCSV implements ExecutionListener {

	// Values for the CSV reader
	private static final String CSV_FILENAME_PARTNERS = "/data/partner-list.csv";
	private static final String CSV_SPLIT_BY_PARTNERS = ";";
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
				
		// Retrieve partners informations from CSV
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersListFromCSV(CSV_FILENAME_PARTNERS, CSV_SPLIT_BY_PARTNERS);
			
		// Set partner list
		execution.setVariable("allPartners", JSON(partners.toJSON()));

	}

}
