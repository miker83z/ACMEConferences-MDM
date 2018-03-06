package it.unibo.soseng.mdm.acme.management;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class MyMessageDelegate implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
    //runtimeService.startProcessInstanceByMessage("messaggio_0");
    runtimeService.createMessageCorrelation("Start")
    .processInstanceBusinessKey("AB-123")
    .setVariable("payment_type", "creditCard")
    .correlate();
  }
  
  

}
