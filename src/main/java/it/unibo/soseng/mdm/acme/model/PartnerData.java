package it.unibo.soseng.mdm.acme.model;

import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.Address;

/**
 * Stores all informations about a partner for venue.
 *
 */
public class PartnerData {
	private String name;
	private String type;
	private String email;
	private String phoneNumber;
	private double price;
	private Address address;
	private Boolean available;
	private Boolean contacted;
	
	public PartnerData() {
		
	}
	
	public PartnerData(String name, String type, String email, String phoneNumber, Address address) {
		this.name = name;
		this.type = type;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		
		// Default values
		this.price = 0.0;
		this.available = true;
		this.contacted = false;
	}
	
	public PartnerData(String name, String type, String email, String phoneNumber, Address address,  
			double price, Boolean available, Boolean contacted) {
		this(name, type, email, phoneNumber, address); ;
		this.price = price;
		this.available = available;
		this.contacted = contacted;
	}
		
	public String getName() {
		return name;
	}
	public String retrieveNameWithoutWhitespaces() {
		return name.replaceAll("\\s+","");
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public Boolean getContacted() {
		return contacted;
	}
	public void setContacted(Boolean contacted) {
		this.contacted = contacted;
	}

	public String toString() {
		return "PartnerData ["
				+ "name=" + name + ", "
				+ "type=" + type + ", "
				+ "email=" + email + ", "
				+ "phoneNumber=" + phoneNumber + ", "
				+ "price=" + price + ", "
				+ "address=" + address + ", "
				+ "available=" + available + ", "
				+ "contacted=" + contacted
				+ "]";
	}

	/**
	 * Convert the partner informations in a string with JSON format
	 * @return The JSON with partner informations
	 */
	public String toJSON() {
		return "{"
				+ "\"name\": \"" + name + "\", "
				+ "\"type\": \"" + type + "\", "
				+ "\"email\": \"" + email + "\", "
				+ "\"phoneNumber\": \"" + phoneNumber + "\", "
				+ "\"price\": " + price + ", "
				+ "\"address\": " + address.toJSON() + ", "
				+ "\"available\": " + available + ", "
				+ "\"contacted\": " + contacted
				+ "}";
	}	
	
	/**
	 * Convert a JSON into a PartnerData object.
	 * @param jsonNode The JSON with all partner informations.
	 * @param nameProperty The name of the JSON property for "name" of partner.
	 * @param typeProperty The name of the JSON property for "type" of partner.
	 * @param emailProperty The name of the JSON property for "email" of partner.
	 * @param phoneNumberProperty The name of the JSON property for "phoneNumber" of partner.
	 * @param priceProperty The name of the JSON property for "price" of partner.
	 * @param availableProperty The name of the JSON property for "available" partner's property.
	 * @param contactedProperty The name of the JSON property for "contacted" partner's property.
	 * @param addressProperty The name of the JSON property for the "address" of partner.
	 */
	public void defineValueFromJSON(SpinJsonNode jsonNode,
			String nameProperty, String typeProperty, String emailProperty, String phoneNumberProperty,
			String priceProperty, String availableProperty, String contactedProperty, 
			String addressProperty) {
		// Set partners value
		setName(jsonNode.prop(nameProperty).stringValue());
		setType(jsonNode.prop(typeProperty).stringValue());
		setEmail(jsonNode.prop(emailProperty).stringValue());
		setPhoneNumber(jsonNode.prop(phoneNumberProperty).stringValue());
		setPrice(jsonNode.prop(priceProperty).numberValue().doubleValue());
		setAvailable(jsonNode.prop(availableProperty).boolValue());
		setContacted(jsonNode.prop(contactedProperty).boolValue());
		
		// Fetch a list of items when your property is an array of data
		SpinJsonNode addressJSON = jsonNode.prop(addressProperty);
		Address address = new Address();
		address.defineValueFromJSON(addressJSON);
		setAddress(address);		
	}
	/**
	 * Convert a JSON into a PartnerData object. The properties of the JSON must have the same name
	 * of PartnerData object's properties:
	 * "name", "type", "email", "phoneNumber", "price", "available", "contacted", "address".
	 * @param jsonNode The JSON with all partner informations.
	 */
	public void defineValueFromJSON(SpinJsonNode jsonNode) {
		defineValueFromJSON(jsonNode, 
				"name", "type", "email", "phoneNumber",
				"price", "available", "contacted", 
				"address");
	}
	
	/**
	 * Convert a string array to a PartnerData object specifying the correct position of 
	 * properties' values.
	 * @param partner The string array with partner informations.
	 * @param nameIdx Index of name property.
	 * @param typeIdx Index of type property.
	 * @param emailIdx Index of email property.
	 * @param phoneNumberIdx Index of phoneNumber property.
	 * @param addressCountryIdx Index of country property.
	 * @param addressCityIdx Index of city property.
	 * @param addressStreetIdx Index of street property.
	 * @param addressPostalCodeIdx Index of postalCode property.
	 * @param availabilityIdx Index of available property.
	 * @param contactedIdx Index of contacted property.
	 */
	public void defineValueFromStrings(String[] partner, 
			Integer nameIdx, Integer typeIdx, Integer emailIdx, Integer phoneNumberIdx,
			Integer addressCountryIdx, Integer addressCityIdx, Integer addressStreetIdx, Integer addressPostalCodeIdx,
			Integer availabilityIdx, Integer contactedIdx) {
		setName(partner[nameIdx]);
		setType(partner[typeIdx]);
		setEmail(partner[emailIdx]);
		setPhoneNumber(partner[phoneNumberIdx]);
		setAddress(new Address(partner[addressCountryIdx], partner[addressCityIdx], partner[addressStreetIdx], partner[addressPostalCodeIdx]));
		setAvailable(Boolean.valueOf(partner[availabilityIdx]));
		setContacted(Boolean.valueOf(partner[contactedIdx]));
	}
	/**
	 * Convert a string array to a PartnerData object. The order of properties in the string must be:
	 * "name","type","email","phoneNumber","country","city","street","postalCode","available","contacted".
	 * @param partner The string array with partner informations.
	 */
	public void defineValueFromStrings(String[] partner) {
		defineValueFromStrings(partner, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	}
	
	/**
	 * 
	 * @param separator
	 * @return
	 */
	public String retrieveValuesForCSV(String separator) {
		return getName() + separator 
			   + getType() + separator 
			   + getEmail() + separator 
			   + getPhoneNumber() + separator 
			   + getAddress().getCountry() + separator 
			   + getAddress().getCity() + separator 
			   + getAddress().getStreet() + separator 
			   + getAddress().getPostalCode() + separator 
			   + String.valueOf(getAvailable()) + separator 
			   + String.valueOf(getContacted());
	}
}
