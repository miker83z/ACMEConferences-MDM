package it.unibo.soseng.mdm.acme.venue;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.acme.venue.model.Address;
import it.unibo.soseng.mdm.acme.venue.model.PartnerData;

public class FindFeasiblePartners implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		/* TODO:
		 * 	1) Retrieve the list of partners from DB or something;
		 * 	2) Save in JSON and set in Camunda
		 */
			
		// Retrieve the partner list
		List<PartnerData> partnerList = new ArrayList<>();
		partnerList = retrievePartnersList();
		
		// The invocation serializationDataFormat("application/json") tells the process engine in which format the variable should be serialized
		ObjectValue JSONpartnerList = Variables.objectValue(partnerList).serializationDataFormat("application/json").create();
		execution.setVariable("partnerList", JSONpartnerList);
	}
	
	
	/**
	 * Create a list of partners
	 * @return The list of partners
	 */
	private List<PartnerData> retrievePartnersList() {
		List<PartnerData> partnerList = new ArrayList<>();

		// Addresses
		String[] countries = {"Italy", "Italy", "Italy"};
		String[] cities = {"Ferrara", "Bologna", "Imola"};
		String[] streets = {"Via Garibaldi, 1", "Via Zamboni, 2", "Via Marchesini, 3"};
		String[] postalCodes = {"11111", "22222", "33333"};
		
		// Partners
		String[] names = {"Dreaming solutions", "Hotel Venezia", "Tiffany"};
		String[] types = {"Manor", "Hotel", "Bar"};
		String[] emails = {"dreaming@solutions.com", "hotel@venezia.com", "tiffany@bar.it"};
		String[] phoneNumbers = {"0532121212", "0544232323", "051343434"};
		
		// Create a list with partner informations
		for (int i = 0; i < countries.length; i++) {
			Address address = new Address(countries[i], cities[i], streets[i], postalCodes[i]);
			List<Address> addresses = new ArrayList<>();
			addresses.add(address);
			PartnerData partner = new PartnerData(names[i], types[i], emails[i], phoneNumbers[i], addresses);
			partnerList.add(partner);
		}
				
		return partnerList;
	}
}