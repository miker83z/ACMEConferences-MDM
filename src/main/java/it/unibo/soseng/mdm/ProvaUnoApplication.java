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
	    RuntimeService runtimeService = processEngine.getRuntimeService();
	    runtimeService.createMessageCorrelation("ask_for_availability");	   
	    runtimeService.createMessageCorrelation("job_estimate");	
	    runtimeService.createMessageCorrelation("offers");	
	    runtimeService.createMessageCorrelation("accepted_offer");
	    runtimeService.createMessageCorrelation("refused_offers");
	    runtimeService.createMessageCorrelation("job_refused");
	    runtimeService.createMessageCorrelation("job_accepted");	
	  }
}
