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
		/* TODO (??):
		 * 	1) Retrieve the list of partners from DB or something;
		 *  2) Send partners' addresses to GIS and get the distance between 
		 *     their address and conference's address;
		 *  3) Create a list of near partners and set the variable.
		 */
		
		// FIXME: The Partners can't be a package inside this project, It must be
		//		  an external service called using Soap and reading their XML	
		// FIXME: I'm not sure about the use of GIS in this point, see the BPMN schema
		// 		  for other details
			
		// Retrieve the partner list
		List<PartnerData> partnerList = new ArrayList<>();
		partnerList = retrievePartnersList();
		
		// The invocation serializationDataFormat("application/json") tells the process engine in which format the variable should be serialized
		ObjectValue typedPartnerList = Variables.objectValue(partnerList).serializationDataFormat("application/json").create();		
		execution.setVariable("partnerList", typedPartnerList);
		
		// Set also the number of retrieved partners used to define the loopCardinality of sequential processes
		execution.setVariable("numberOfPartners", partnerList.size());
	}
	
	/**
	 * Create a fake list of partners
	 * @return fake list of partners
	 */
	private List<PartnerData> retrievePartnersList() {
		List<PartnerData> partnerList = new ArrayList<>();
		PartnerData partner = new PartnerData();		
		Address address = new Address();
		List<Address> addresses = new ArrayList<>();
		
		// First partner
		partner.setName("Dreaming solutions");
		partner.setType("Manor");
		address.setCity("Ferrara");
		address.setCountry("Italia");
		address.setStreet("Via Garibaldi, 111");
		address.setPostalCode("44122");
		addresses.add(address);
		partner.setAddresses(addresses);
		// Add to the list
		partnerList.add(partner);

		// Second partner
		partner.setName("Hotel Carlton");
		partner.setType("Hotel");
		address.setCity("Bologna");
		address.setCountry("Italia");
		address.setStreet("Via Zamboni, 44");
		address.setPostalCode("44444");
		addresses.add(address);
		partner.setAddresses(addresses);
		// Add to the list
		partnerList.add(partner);
		
		// Third partner
		/*
		partner.setName("Tiffany");
		partner.setType("Bar");
		address.setCity("Padova");
		address.setCountry("Italia");
		address.setStreet("Via Belle Arti, 4/A");
		address.setPostalCode("40111");
		addresses.add(address);
		partner.setAddresses(addresses);
		// Add to the list
		partnerList.add(partner);
		*/
		
		return partnerList;
	}
}