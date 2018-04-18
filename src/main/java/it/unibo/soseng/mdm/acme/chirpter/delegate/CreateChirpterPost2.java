package it.unibo.soseng.mdm.acme.chirpter.delegate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

import it.unibo.soseng.mdm.model.ConferenceData;

@SuppressWarnings("deprecation")
public class CreateChirpterPost2 {
	
	public static void main(String[] args) {
		try {
			
			@SuppressWarnings("resource")
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://172.17.0.4/chirpter_api/events/48/posts/");
			
			StringEntity input = new StringEntity("{\"token\":\"YI7WYvrEwCf7IXOV+N4RJoXHnKj0N5AOA12BlRZfd7E=\",\"postTitle\":\"postcamundadsdprova\"}");
			input.setContentType("application/json");
			postRequest.setEntity(input);
			//postRequest.setHeader(HttpHeaders.AUTHORIZATION, "YI7WYvrEwCf7IXOV+N4RJoXHnKj0N5AOA12BlRZfd7E=");
			HttpResponse response = httpClient.execute(postRequest);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
	  



