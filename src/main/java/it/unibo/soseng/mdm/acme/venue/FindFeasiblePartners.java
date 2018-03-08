package it.unibo.soseng.mdm.acme.venue;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

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
		
		
		// Create a fake list of partners and set the variable used by Contact Partners Subprocess
		Integer numberOfPartners = 1;
		Integer[] partnersInfo = new Integer[numberOfPartners];
		for (int i = 0; i < numberOfPartners; i++) {
			partnersInfo[i] = i;
		}
		execution.setVariable("partnersList", partnersInfo);
	}
}