package it.unibo.soseng.mdm;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;

@ProcessApplication("Prova Uno App")
public class ProvaUnoApplication extends ServletProcessApplication {
	
	@PostDeploy
	  public void startService(ProcessEngine processEngine) throws Exception {
	    //RuntimeService runtimeService = processEngine.getRuntimeService();
	    //runtimeService.createMessageCorrelation("Start");	   
	    //runtimeService.createMessageCorrelation("Confirmation");	
	    //runtimeService.createMessageCorrelation("Rejection");	
	  }
}
