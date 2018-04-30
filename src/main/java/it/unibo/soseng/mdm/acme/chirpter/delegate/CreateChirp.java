package it.unibo.soseng.mdm.acme.chirpter.delegate;

import java.net.ConnectException;
import java.util.Date;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.RelevantEvents;

/**
 * The Class CreateChirp, used in task Create Chirp to post a Chirp containing Relevant Event info in Chirpter.
 * @author Michele Contu
 * @author Mirko Zichichi
 */
@SuppressWarnings("deprecation")
public class CreateChirp implements JavaDelegate {
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		RelevantEvents relEv = (RelevantEvents) execution.getVariable("relevantEvents");
		try {
			@SuppressWarnings("resource")
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://lamp/chirpter_api/events/" + execution.getVariable("chirpterEventID") + "/chirps/");
			
			StringEntity input = new StringEntity("{\"chirp\":\"" + relEv.retrieveCurrentEventDescription() + "\"}");
			input.setContentType("application/json");
			postRequest.setEntity(input);
			postRequest.setHeader(HttpHeaders.AUTHORIZATION, (String) execution.getVariable("chirpterToken"));
			httpClient.execute(postRequest);

			httpClient.getConnectionManager().shutdown();
			}  catch (ConnectException e) {
				e.printStackTrace();
				throw new BpmnError("CONNECTION_ERROR");
			}
		Date tmp = relEv.retrieveNextRelevantEvent();
		if( tmp != null )
			execution.setVariable("nextRelevantDate", tmp);
		else
			//Set to "Infinite"
			execution.setVariable("nextRelevantDate", (Date) execution.getVariable("confLastDayPlus60"));
	}
	
}
	  



