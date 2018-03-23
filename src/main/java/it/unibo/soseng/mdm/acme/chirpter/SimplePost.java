package it.unibo.soseng.mdm.acme.chirpter;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SimplePost implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
      ApacheHttpClientPost chirpter_post = new ApacheHttpClientPost("Javapost", "Javapost", "Javapost", "Javapost", "localhost/chirpter_api/article/create.php");
      chirpter_post.makeHTTPPOSTRequest();
  }
}


