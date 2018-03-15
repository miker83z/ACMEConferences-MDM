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

		// First partner
		PartnerData p1 = new PartnerData();		
		Address a1 = new Address();
		List<Address> al1 = new ArrayList<>();
		p1.setName("Dreaming solutions");
		p1.setType("Manor");
		a1.setCity("Ferrara");
		a1.setCountry("Italia");
		a1.setStreet("Via Garibaldi, 111");
		a1.setPostalCode("44122");
		al1.add(a1);
		p1.setAddresses(al1);
		p1.setContacted(false);
		p1.setAvailability(true);

		// Second partner
		PartnerData p2 = new PartnerData();		
		Address a2 = new Address();
		List<Address> al2 = new ArrayList<>();
		p2.setName("Hotel Carlton");
		p2.setType("Hotel");
		a2.setCity("Bologna");
		a2.setCountry("Italia");
		a2.setStreet("Via Zamboni, 44");
		a2.setPostalCode("44444");
		al2.add(a2);
		p2.setAddresses(al2);
		p2.setAvailability(true);
		p2.setContacted(false);
		
		// Third partner
		PartnerData p3 = new PartnerData();		
		Address a3 = new Address();
		List<Address> al3 = new ArrayList<>();
		p3.setName("Tiffany");
		p3.setType("Bar");
		a3.setCity("Padova");
		a3.setCountry("Italia");
		a3.setStreet("Via Belle Arti, 4/A");
		a3.setPostalCode("40111");
		al3.add(a3);
		p3.setAddresses(al3);
		p3.setAvailability(true);
		p3.setContacted(false);
		
		// Add to the list
		partnerList.add(p1);
		partnerList.add(p2);
		partnerList.add(p3);

		
		return partnerList;
	}
}