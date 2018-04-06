package it.unibo.soseng.mdm.acme.venue;

import java.util.ArrayList;
import static org.camunda.spin.Spin.JSON;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.venue.model.Address;
import it.unibo.soseng.mdm.acme.venue.model.PartnerData;

public class FindFeasiblePartners implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		/* TODO:
		 * 	1) Retrieve the list of partners from CSV;
		 * 	2) Save in JSON and set in Camunda
		 */
		
		// Retrieve the partner list
		List<PartnerData> partnerList = new ArrayList<>();
		partnerList = retrievePartnersList();
		
		// Create a string in JSON format with all partner informations
		String partnerListJSON = "[";
		for (int i = 0; i < partnerList.size(); i++) {
			partnerListJSON += partnerList.get(i).toJSON();
			if (i < partnerList.size() - 1) {
				partnerListJSON += ", ";
			}
		}
		partnerListJSON += "]";
		
		// Convert the string in JSON
		SpinJsonNode jsonNode = JSON(partnerListJSON);
		
		// Set variable
		execution.setVariable("partnerList", jsonNode);
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
		
		// Set the last one as contacted
		partnerList.get(partnerList.size()-1).setContacted(true);
		
		return partnerList;
	}
}