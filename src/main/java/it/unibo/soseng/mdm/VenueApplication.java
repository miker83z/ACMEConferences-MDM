package it.unibo.soseng.mdm;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;

@ProcessApplication("Venue Management App")
public class VenueApplication extends ServletProcessApplication {
	
	@PostDeploy
	public void startService(ProcessEngine processEngine) throws Exception {

	}
}
