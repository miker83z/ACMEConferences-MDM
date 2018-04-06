package it.unibo.soseng.mdm.acme.model;

import org.camunda.spin.json.SpinJsonNode;

/**
 * Store all informations about the client and the job
 *
 */
public class JobData {
	protected String clientName;
	protected String date;
	protected Address address;
	
	public JobData() {
		
	}
	
	public JobData(String clientName, String date, Address address) {
		this.clientName = clientName;
		this.date = date;
		this.address = address;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public String toString() {
		return "JobData ["
				+ "clientName=" + clientName + ", "
				+ "date=" + date + ", "
				+ "address=" + address
				+ "]";
	}
	
	/**
	 * Convert the informations in a string with JSON format
	 * @return The JSON with job informations
	 */
	public String toJSON() {
		return "{"
				+ "\"clientName\": \"" + clientName + "\", "
				+ "\"date\": \"" + date + "\", "
				+ "\"address\": " + address.toJSON()
				+ "}";
	}	
	
	/**
	 * Convert a JSON into a JobData object.
	 * @param jsonNode The JSON with all job informations.
	 * @param clietNameProperty The name of the JSON property for "clientName".
	 * @param dateProperty The name of the JSON property for "date".
	 * @param addressProperty The name of the JSON property for the "address".
	 */
	public void setValueFromJSON(SpinJsonNode jsonNode, 
			String clientNameProperty, String dateProperty, String addressProperty) {
		setClientName(jsonNode.prop(clientNameProperty).stringValue());
		setDate(jsonNode.prop(dateProperty).stringValue());
		SpinJsonNode addressJSON = jsonNode.prop(addressProperty);
		Address address = new Address();
		address.setValueFromJSON(addressJSON);
		setAddress(address);
	}
	/**
	 * Convert a JSON into a JobData object. The properties of the JSON must have the same name
	 * of JobData object's properties:
	 * "clientName", "date", "address".
	 * @param jsonNode The JSON with all partner informations.
	 */
	public void setValueFromJSON(SpinJsonNode jsonNode) {
		setValueFromJSON(jsonNode, "clientName", "date", "address");
	}
	
	/**
	 * Convert a string array to a JobData object specifying the correct position of 
	 * properties' values.
	 * @param jobInformations The string array with job informations.
	 * @param clientNameIdx Index of clientName property.
	 * @param dateIdx Index of date property.
	 * @param addressCountryIdx Starting index of country property.
	 * @param addressCityIdx Starting index of city property.
	 * @param addressStreetIdx Starting index of street property.
	 * @param addressPostalCodeIdx Starting index of postalCode property.
	 */
	public void setValueFromStrings(String[] jobInformations, 
			Integer clientNameIdx, Integer dateIdx, 
			Integer addressCountryIdx, Integer addressCityIdx, Integer addressStreetIdx, Integer addressPostalCodeIdx) {
		setClientName(jobInformations[clientNameIdx]);
		setDate(jobInformations[dateIdx]);
		setAddress(new Address(jobInformations[addressCountryIdx], jobInformations[addressCityIdx], jobInformations[addressStreetIdx], jobInformations[addressPostalCodeIdx]));
	}
	/**
	 * Convert a string array to a JobData object. The order of properties in the string must be:
	 * "clientName","date","country","city","street", "postalCode".
	 * @param jobInformations The string array with job informations.
	 */
	public void setValueFromStrings(String[] jobInformations) {
		setValueFromStrings(jobInformations, 0, 1, 2, 3, 4, 5);
	}
	
	
}
