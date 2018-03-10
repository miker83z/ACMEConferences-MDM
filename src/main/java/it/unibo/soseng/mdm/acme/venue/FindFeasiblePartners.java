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
		 *  2) Send partners' addresses to GIS and get the distance between 
		 *     their address and conference's address;
		 *  3) Create a list of near partners and set the variable.
		 */
		
		// FIXME: The Partners can't be a package inside this project, It must be
		//		  an external service called using Soap and reading their XML	
		// FIXME: I need a PartnerInformation class to create a list, maybe 
		//		  I can create the class using the XML created by the Partners
		// FIXME: I'm not sure about the use of GIS in this point, see the BPMN schema
		// 		  for other details
			
		// A sample partner
		PartnerData partner = new PartnerData();
		partner.setName("Dreaming solutions");
		partner.setType("Hotel");
		Address address = new Address();
		address.setCity("Ferrara");
		address.setCountry("Italia");
		address.setStreet("Via Garibaldi, 111");
		address.setPostalCode("44122");
		List<Address> addresses = new ArrayList<>();
		addresses.add(address);
		partner.setAddresses(addresses);
		
		// A sample list of partner
		Integer numberOfPartners = 5;
		List<PartnerData> partnerList = new ArrayList<>();
		for (int i = 0; i < numberOfPartners; i++) {
			partnerList.add(partner);
		}
		
		// The invocation serializationDataFormat("application/json") tells 
		// the process engine in which format the variable should be serialized
		ObjectValue typedPartnerList = 
				Variables.objectValue(partnerList).serializationDataFormat("application/json").create();		
		execution.setVariable("partnerList", typedPartnerList);
	}
}