package it.unibo.soseng.mdm.acme.chirpter.delegate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

import it.unibo.soseng.mdm.model.ConferenceData;
import it.unibo.soseng.mdm.model.RelevantEvents;

/**
 * The Class CreateChirpterEvent, used in task Create Chirp Event to create a Chirp event for Conference in Chirpter.
 * @author Michele Contu
 * @author Mirko Zichichi
 */
@SuppressWarnings("deprecation")
public class CreateChirpterEvent implements JavaDelegate {
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		try {
			ConferenceData confData = (ConferenceData) execution.getVariable("conferenceData");
			
			@SuppressWarnings("resource")
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://lamp/chirpter_api/events/");
			
			StringEntity input = new StringEntity("{\"eventTitle\":\"" + confData.getTitle() + "\"}");
			input.setContentType("application/json");
			postRequest.setEntity(input);
			postRequest.setHeader(HttpHeaders.AUTHORIZATION, (String) execution.getVariable("chirpterToken"));
			HttpResponse response = httpClient.execute(postRequest);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			
			String output;
			while ((output = br.readLine()) != null) {
				if (output.contains("eventID")) {
					JSONObject o = new JSONObject(output);
					execution.setVariable("chirpterEventID", o.get("eventID"));
				}
			}

			httpClient.getConnectionManager().shutdown();
			} catch (ConnectException e) {
				e.printStackTrace();
				throw new BpmnError("CONNECTION_ERROR");
			}
		execution.setVariable("nextRelevantDate", ((RelevantEvents) execution.getVariable("relevantEvents")).retrieveNextRelevantEvent());
	}
	
}
	  



