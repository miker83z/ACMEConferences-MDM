package it.unibo.soseng.mdm.acme.venue.listener;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.model.JobData;
import it.unibo.soseng.mdm.model.PartnerDatas;
import it.unibo.soseng.mdm.util.CSVUtils;

public class LoadDataFromCSV implements ExecutionListener {

	// Values for the CSV reader
	private static final String CSV_FILENAME_PARTNERS = "/data/partner-list.csv";
	private static final String CSV_SPLIT_BY_PARTNERS = ";";
	private static final String CSV_FILENAME_CLIENT   = "/data/job-informations.csv";
	private static final String CSV_SPLIT_BY_CLIENT   = ";";
	private static final String CSV_FILENAME_CATERING = "/data/catering-list.csv";
	private static final String CSV_SPLIT_BY_CATERING   = ";";	
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// Retrieve job informations from CSV
		CSVUtils csvReader = new CSVUtils(CSV_FILENAME_CLIENT, CSV_SPLIT_BY_CLIENT);
		JobData jobData = new JobData();
		jobData.setValueFromStrings(csvReader.readLine());
				
		// Retrieve partners informations from CSV
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersListFromCSV(CSV_FILENAME_PARTNERS, CSV_SPLIT_BY_PARTNERS);
	
		// Retrieve catering informations from CSV
		PartnerDatas catering = new PartnerDatas();
		catering.setPartnersListFromCSV(CSV_FILENAME_CATERING, CSV_SPLIT_BY_CATERING);
		
		// Set partner list
		execution.setVariable("allPartners", JSON(partners.toJSON()));
		
		// Set job informations
		execution.setVariable("jobInformations", JSON(jobData.toJSON()));
		
		// Set catering list
		execution.setVariable("allCatering", JSON(catering.toJSON()));
	}

}
