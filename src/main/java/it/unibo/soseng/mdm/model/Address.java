package it.unibo.soseng.mdm.model;

import java.math.BigDecimal;

import org.camunda.spin.json.SpinJsonNode;


/**
 * Stores all informations about the address.
 */
public class Address {
	private String country;
	private String city;
	private String street;
	private String postalCode;
	private BigDecimal distanceFromUserRequest;

	public Address() {
		
	}
	
	public Address(String country, String city, String street, String postalCode) {
		this.country = country;
		this.city = city;
		this.street = street;
		this.postalCode = postalCode;
		this.distanceFromUserRequest = BigDecimal.valueOf(0.0);
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
	public BigDecimal getDistanceFromUserRequest() {
		return distanceFromUserRequest;
	}
	public void setDistanceFromUserRequest(BigDecimal distanceFromUserRequest) {
		this.distanceFromUserRequest = distanceFromUserRequest;
	}

	public String toString() {
		return "Address ["
				+ "country=" + country + ", "
				+ "city=" + city + ", "
				+ "street=" + street + ", "
				+ "postalCode=" + postalCode + ", "
				+ "distanceFromUserRequest=" + distanceFromUserRequest 
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
				+ "\"postalCode\": \"" + postalCode + "\", "
				+ "\"distanceFromUserRequest\": " + distanceFromUserRequest 
				+ "}";
	}
	
	/**
	 * Convert a JSON into an Address object.
	 * @param jsonNode The JSON with all address informations.
	 * @param countryProperty The name of the JSON property for the "country".
	 * @param cityProperty The name of the JSON property for the "city".
	 * @param streetProperty The name of the JSON property for the "street".
	 * @param postalCodeProperty The name of the JSON property for the "postalCode".
	 * @param distanceProperty The name of the JSON property for the "distanceFromUserRequest".
	 */
	public void defineValueFromJSON(SpinJsonNode jsonNode,
			String countryProperty, String cityProperty, String streetProperty, String postalCodeProperty,
			String distanceProperty) {	
		setCountry(jsonNode.prop(countryProperty).stringValue());
		setCity(jsonNode.prop(cityProperty).stringValue());
		setStreet(jsonNode.prop(streetProperty).stringValue());
		setPostalCode(jsonNode.prop(postalCodeProperty).stringValue());
		setDistanceFromUserRequest(BigDecimal.valueOf(jsonNode.prop(distanceProperty).numberValue().doubleValue()));
	}
	/**
	 * Convert a JSON into an Address object. The properties of the JSON must have the same name
	 * of Address object's properties:
	 * "country", "city", "street", "postalCode".
	 * @param jsonNode The JSON with all address informations.
	 */
	public void defineValueFromJSON(SpinJsonNode jsonNode) {
		defineValueFromJSON(jsonNode, "country", "city", "street", "postalCode", "distanceFromUserRequest");
	}
	
	/**
	 * Create a string that is readable from the GIS external service.
	 * @return The string with address informations
	 */
	public String toGisReadableString() {
		return country + ", " + city + ", " + street;
	}
}
