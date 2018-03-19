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
		 * 	1) Retrieve the list of partners from DB or something;
		 * 	2) Save in JSON and set in Camunda
		 */
			
		// // Retrieve the partner list
		// List<PartnerData> partnerList = new ArrayList<>();
		// partnerList = retrievePartnersList();
		// // The invocation serializationDataFormat("application/json") tells the process engine in which format the variable should be serialized
		// ObjectValue JSONpartnerList = Variables
		// 		.objectValue(partnerList)
		// 		.serializationDataFormat("application/json")
		// 		.create();
		// execution.setVariable("partnerList", JSONpartnerList);
				
		/* A Sample JSON
		String prova = ""
				+ "["
					+ "{"
						+ "\"name\": \"" + partnerList.get(0).getName() + "\", "
						+ "\"type\": \"" + partnerList.get(0).getType() + "\", "
						+ "\"email\": \"" + partnerList.get(0).getEmail() + "\", "
						+ "\"phoneNumber\": \"" + partnerList.get(0).getPhoneNumber() + "\", "
						+ "\"addresses\": "
						+ "["
							+ "{"
								+ "\"country\": \"" + partnerList.get(0).getAddresses().get(0).getCountry() + "\", "
								+ "\"city\": \"" + partnerList.get(0).getAddresses().get(0).getCity() + "\", "
								+ "\"street\": \"" + partnerList.get(0).getAddresses().get(0).getStreet() + "\", "
								+ "\"postalCode\": \"" + partnerList.get(0).getAddresses().get(0).getPostalCode() + "\""
							+ "}"
						+ "], "
						+ "\"available\": " + partnerList.get(0).getAvailable() + ", "
						+ "\"contacted\": " + partnerList.get(0).getContacted()
					+ "}"
				+ "]";
		*/
		
		// Retrieve the partner list
		List<PartnerData> partnerList = new ArrayList<>();
		partnerList = retrievePartnersList();
		
		// FIXME: questo pezzo c'è anche in PresentOffers
		// Create the string with all partner informations
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
			// Addresses addresses = new Addresses();
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