package it.unibo.soseng.mdm;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;

/**
 * The Class ProvaUnoApplication, used for the Camunda Process.
 */
@ProcessApplication("Prova Uno App")
public class ProvaUnoApplication extends ServletProcessApplication {
	
	/**
	 * Start service.
	 *
	 * @param processEngine the process engine
	 * @throws Exception the exception
	 */
	@PostDeploy
	  public void startService(ProcessEngine processEngine) throws Exception {
	    //Stert Service
	  }
}
