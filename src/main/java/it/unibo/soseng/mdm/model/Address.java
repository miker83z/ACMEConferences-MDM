package it.unibo.soseng.mdm.model;

import java.math.BigDecimal;

/**
 * Stores all the useful informations about a user's address
 * 
 * @author Davide Marchi
 *
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
	 * Create a string that is readable from the GIS external service.
	 * @return The string with address informations
	 */
	public String toGisReadableString() {
		return country + ", " + city + ", " + street;
	}
}
