package it.unibo.soseng.mdm.acme.venue.model;

import org.camunda.spin.json.SpinJsonNode;

/**
 * Stores all informations about the address.
 */
public class Address {
	private String country;
	private String city;
	private String street;
	private String postalCode;

	public Address() {
		
	}
	
	public Address(String country, String city, String street, String postalCode) {
		this.country = country;
		this.city = city;
		this.street = street;
		this.postalCode = postalCode;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String toString() {
		return "Address ["
				+ "country=" + country + ", "
				+ "city=" + city + ", "
				+ "street=" + street + ", "
				+ "postalCode=" + postalCode
				+ "]";
	}
	
	/**
	 * Convert the Address object to a String in JSON format
	 * @return The JSON with address informations
	 */
	public String toJSON() {
		return "{"
				+ "\"country\": \"" + country + "\", "
				+ "\"city\": \"" + city + "\", "
				+ "\"street\": \"" + street + "\", "
				+ "\"postalCode\": \"" + postalCode + "\""
				+ "}";
	}
	
	/**
	 * Convert a JSON in to an Address object
	 * @param jsonNode The JSON with all address informations
	 */
	public void setValueFromJSON(SpinJsonNode jsonNode) {	
		// Set address values
		setCountry(jsonNode.prop("country").stringValue());
		setCity(jsonNode.prop("city").stringValue());
		setStreet(jsonNode.prop("street").stringValue());
		setPostalCode(jsonNode.prop("postalCode").stringValue());
	}
}
