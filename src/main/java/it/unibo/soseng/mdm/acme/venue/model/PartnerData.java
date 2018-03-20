package it.unibo.soseng.mdm.acme.venue.model;

import it.unibo.soseng.mdm.acme.venue.model.Address;

import java.util.ArrayList;
import java.util.List;

import org.camunda.spin.SpinList;
import org.camunda.spin.json.SpinJsonNode;

public class PartnerData {
	
	/**
	 * 
	 */
	private String name;
	private String type;
	private String email;
	private String phoneNumber;
	private double price;
	private List<Address> addresses = new ArrayList<Address>();
	private Boolean available;
	private Boolean contacted;
	
	public PartnerData() {
		
	}
	
	public PartnerData(String name, String type, String email, String phoneNumber, List<Address> addresses) {
		this.name = name;
		this.type = type;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.addresses = addresses;
		// Default values
		this.price = 0.0;
		this.available = true;
		this.contacted = false;
	}
	
	public PartnerData(String name, String type, String email, String phoneNumber, List<Address> addresses, 
			double price, Boolean available, Boolean contacted) {
		this(name, type, email, phoneNumber, addresses);
		this.price = price;
		this.available = available;
		this.contacted = contacted;
	}
		
	public String getName() {
		return name;
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
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	public void addAddress(Address address) {
		this.addresses.add(address);
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailability(Boolean available) {
		this.available = available;
	}
	public Boolean getContacted() {
		return contacted;
	}
	public void setContacted(Boolean contacted) {
		this.contacted = contacted;
	}
	
	/**
	 * 
	 */
	public String toString() {
		return "PartnerData ["
				+ "name=" + name + ", "
				+ "type=" + type + ", "
				+ "email=" + email + ", "
				+ "phoneNumber=" + phoneNumber + ", "
				+ "price=" + price + ", "
				+ "addresses=" + addresses + ", "
				+ "available=" + available + ", "
				+ "contacted=" + contacted
				+ "]";
	}

	/**
	 * 
	 * @return
	 */
	private String addressesToJSON() {
		
		String addressesJSON = "[";
		for (int i = 0; i < addresses.size(); i++) {
			addressesJSON += addresses.get(i).toJSON();
			if (i < addresses.size() - 1) {
				addressesJSON += ", ";
			}
		}
		addressesJSON += "]";
		
		return addressesJSON;
	}

	/**
	 * 
	 * @return
	 */
	public String toJSON() {
		return "{"
				+ "\"name\": \"" + name + "\", "
				+ "\"type\": \"" + type + "\", "
				+ "\"email\": \"" + email + "\", "
				+ "\"phoneNumber\": \"" + phoneNumber + "\", "
				+ "\"price\": " + price + ", "
				+ "\"addresses\": " + addressesToJSON() + ", "
				+ "\"available\": " + available + ", "
				+ "\"contacted\": " + contacted
				+ "}";
	}	
	
	/**
	 * 
	 * @param jsonNode
	 */
	public void setValueFromJSON(SpinJsonNode jsonNode) {	
		// Set partnerns value
		setName(jsonNode.prop("name").stringValue());
		setType(jsonNode.prop("type").stringValue());
		setEmail(jsonNode.prop("email").stringValue());
		setPhoneNumber(jsonNode.prop("phoneNumber").stringValue());
		setPrice(jsonNode.prop("price").numberValue().doubleValue());
		setAvailability(jsonNode.prop("available").boolValue());
		setContacted(jsonNode.prop("contacted").boolValue());
		
		// Fetch a list of items when your property is an array of data
		SpinJsonNode addressesProperty = jsonNode.prop("addresses");
		@SuppressWarnings("rawtypes")
		SpinList addresses = addressesProperty.elements();
		for (int i = 0; i < addresses.size(); i++) {
			// Get the i-th element
			SpinJsonNode addressJSON = (SpinJsonNode) addresses.get(i);
			// Convert to Address object
			Address address = new Address();
			address.setValueFromJSON(addressJSON);
			// Add to addresses
			addAddress(address);
		}
	}
}
