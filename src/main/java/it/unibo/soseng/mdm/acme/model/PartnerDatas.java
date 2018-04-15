package it.unibo.soseng.mdm.acme.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.camunda.spin.SpinList;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.generated.gis.GIS;
import it.unibo.soseng.mdm.acme.generated.gis.GISPortType;
import it.unibo.soseng.mdm.acme.generated.gis.GetDistanceBetween;
import it.unibo.soseng.mdm.acme.generated.gis.GetDistanceBetweenResponse;
import it.unibo.soseng.mdm.util.CSVUtils;

/**
 * Stores a list of PartnerData.
 *
 */
public class PartnerDatas {
	
	protected List<PartnerData> partnerList = new ArrayList<>();
	
	private static final Integer DEFAULT_MAX_NUMBER_OF_PARTNERS = 1;
	
	public PartnerDatas() {
		
	}
	public PartnerDatas(List<PartnerData> partnerList) {
		this.partnerList = partnerList;
	}

	public List<PartnerData> getPartnerList() {
		return partnerList;
	}
	public void setPartnerList(List<PartnerData> partnerList) {
		this.partnerList = partnerList;
	}
	public static Integer getDefaultMaxNumberOfPartners() {
		return DEFAULT_MAX_NUMBER_OF_PARTNERS;
	}
	
	public Integer retrieveNumberOfPartners() {
		return partnerList.size();
	}
	
	public String toString() {
		return "PartnerDatas [partnerList=" + partnerList + "]";
	}
	
	/**
	 * Create a string in JSON format with all partners' informations.
	 * @return The JSON string 
	 */
	public String toJSON() {
		String partnerListJSON = "[";
		for (int i = 0; i < partnerList.size(); i++) {
			partnerListJSON += partnerList.get(i).toJSON();
			if (i < partnerList.size() - 1) {
				partnerListJSON += ", ";
			}
		}
		partnerListJSON += "]";
		return partnerListJSON;
	}
		
	/**
	 * Get all partner's informations from JSON and save in the object
	 * @param jsonNode All partner informations in JSON format
	 */
	public void definePartnersFromJSON(SpinJsonNode jsonNode) {		
		// Fetch a list of items when your property is an array of data
		@SuppressWarnings("rawtypes")
		SpinList partners = jsonNode.elements();
		for (int i = 0; i < partners.size(); i++) {
			// Get the i-th element
			SpinJsonNode partnerJSON = (SpinJsonNode) partners.get(i);
			// Convert to PartnerData object
			PartnerData partnerData = new PartnerData();
			partnerData.defineValueFromJSON(partnerJSON);
			// Add to list
			partnerList.add(partnerData);
		}
	}
	
	/**
	 * Set the list of partners reading information from the csvFile.
	 * The partner list is ordered from the nearest partner to the farthest.
	 * @param csvFile The CSV file with all the partner informations.
	 * @param csvSplitBy The character used to split values in the CSV.
	 */
	public void definePartnersListFromCSV(String csvFile, String csvSplitBy) {		
		// Create the CSVreader object
		CSVUtils csvReader = new CSVUtils(csvFile, csvSplitBy);
		
		// Read line-by-line the CSV and save all the partners' informations
		for (int i = 0; i < csvReader.getNumberOfLines(); i++) {
			PartnerData partnerData = new PartnerData();
			partnerData.defineValueFromStrings(csvReader.readLine(i+1));
			partnerList.add(partnerData);
		}
	}
		
	/**
	 * Order the list of partners by distance
	 * @param partnerList The list of partners 
	 * @param conferenceAddress The address of the conference
	 */
	public void orderPartnersList(Address conferenceAddress) {		
		// Create the object to communicate with GIS service
		GIS gisService = new GIS();
		GISPortType gis = gisService.getGIS();
		
		// For each partner, check distance between their address and client requested address
		for (PartnerData partner : partnerList) {
			// Convert addresses to Strings
			String address1 = conferenceAddress.toGisReadableString();
			String address2 = partner.getAddress().toGisReadableString();
						
			// Ask to GIS the distance
			GetDistanceBetween getDistanceBetween = new GetDistanceBetween();
			getDistanceBetween.setAddress1(address1);
			getDistanceBetween.setAddress2(address2);
			GetDistanceBetweenResponse distance = gis.getDistanceBetween(getDistanceBetween);
			partner.getAddress().setDistanceFromUserRequest(distance.getResult()); 
			
			//BigDecimal distance = gis.getDistanceBetween(address1, address2);
			// Save the distance
			//partner.getAddress().setDistanceFromUserRequest(distance);
		}
				
		// Order the list 
		partnerList.sort((d1, d2) -> d1.getAddress().getDistanceFromUserRequest().compareTo(d2.getAddress().getDistanceFromUserRequest()));	
	}
		
	/**
	 * Leave in the partner list only the first maxNumberOfPartners partners
	 * @param maxNumberOfPartners The new size of the list
	 */
	public void cutPartnersList(Integer maxNumberOfPartners) {
		if (partnerList.size() > maxNumberOfPartners) {
			partnerList = partnerList.subList(0, maxNumberOfPartners);
		}
	}
	
	/**
	 * Leave in the partner list only the first five partners
	 */	
	public void cutPartnersList() {
		cutPartnersList(DEFAULT_MAX_NUMBER_OF_PARTNERS);
	}
	
	/**
	 * Leave in the list only the contacted partners that are available
	 */
	public void retrieveAvailablePartners() {
		// Create a list with only the available and contacted partners
		List<PartnerData> availablePartners = new ArrayList<>();
		
		for (PartnerData partnerData : partnerList) {
			if (partnerData.getAvailable() && partnerData.getContacted()) {
				availablePartners.add(partnerData);
			}
		}
		
		partnerList = availablePartners;
	}
	
	/**
	 * Return true if there is at least one available partner, else false.
	 * @return Boolean value
	 */
	public Boolean atLeastOneAvailablePartner() {		
		for (PartnerData partnerData : partnerList) {
			if (partnerData.getAvailable()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Remove all the contacted partners from the list
	 */
	public void removeContactedPartners() {		
		List<PartnerData> toRemove = new ArrayList<>();
		for (PartnerData partner : partnerList) {
			if (partner.getContacted()) {
				toRemove.add(partner);
			}
		}
		partnerList.removeAll(toRemove);
	}
	
	/**
	 * Update the CSV file with partners informations.
	 * @param filename The name of CSV file
	 * @param separator The column separator
	 * @throws IOException
	 */
	public void updatePartnersCSV(String filename, String separator) throws IOException {
		CSVUtils csvUtils = new CSVUtils(filename, separator);
		List<String> values = new ArrayList<>();
		for (PartnerData partner : partnerList) {
			values.add(partner.retrieveValuesForCSV(separator));
		}
		csvUtils.writeLines(values);
	}
	
	/**
	 * Retrieve the index of the partner with this name.
	 * @param partnerName The name of the partners
	 * @return The index of this partner 
	 */
	public Integer indexOf(String partnerName) {
		Integer index = -1;
		Boolean found = false;
		Integer i = 0;
		while (i < partnerList.size() && !found) {
			System.out.println("[PROVA] PARTNER-LIST: " + partnerList.get(i).getName());
			System.out.println("[PROVA] PARNTER-NAME: " + partnerName);
			if (partnerList.get(i).getName().equals(partnerName)) {
				index = i;
				found = true;
			}			
			i++;
		}
		return index;
	}
	
}
