package it.unibo.soseng.mdm.acme.management;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.misc.RandomAlphanumericString;

public class AskForAvailability implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
    //runtimeService.startProcessInstanceByMessage("messaggio_0");
    
    //lista di partner business key con un ciclo for
    String partner1BusinessKey = RandomAlphanumericString.generate();
    execution.setVariable("partner1BusinessKey", partner1BusinessKey);
    String idPartner1 = "john";//l'id dell'account camunda del partner, saranno tutti salvati da qualche parte 
    
    runtimeService.createMessageCorrelation("Prova")
    .processInstanceBusinessKey(partner1BusinessKey)
    .setVariable("acmeBK", execution.getBusinessKey())
    .setVariable("processTenant", idPartner1)
    .correlate();
    
    
    String partner2BusinessKey = RandomAlphanumericString.generate();
    execution.setVariable("partner2BusinessKey", partner2BusinessKey);
    String idPartner2 = "peter";
    
    runtimeService.createMessageCorrelation("Prova")
    .processInstanceBusinessKey(partner2BusinessKey)
    .setVariable("acmeBK", execution.getBusinessKey())
    .setVariable("processTenant", idPartner2)
    .correlate();
  }
  
  

}
