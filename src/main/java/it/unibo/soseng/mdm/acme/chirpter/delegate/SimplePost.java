//package it.unibo.soseng.mdm.acme.chirpter;
//
//import org.camunda.bpm.engine.RuntimeService;
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.camunda.bpm.engine.delegate.JavaDelegate;
//
//public class SimplePost implements JavaDelegate {
//
//  public void execute(DelegateExecution execution) throws Exception {
//      ApacheHttpClientPost chirpter_post = new ApacheHttpClientPost("Javapost", "Javapost", "Javapost", "Javapost", "localhost/chirpter_api/article/create.php");
//      chirpter_post.makeHTTPPOSTRequest();
//  }
//}

package it.unibo.soseng.mdm.acme.chirpter.delegate;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SimplePost implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
	  
	 // try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(
				"http://71e64928.ngrok.io/chirpter_api/event/create.php");

			StringEntity input = new StringEntity("{\"token\":\"YI7WYvrEwCf7IXOV+N4RJoXHnKj0N5AOA12BlRZfd7E=\",\"catTitle\":\"JavaPostCamunda\",\"catSlug\":\"JavaPostCamunda\"}");
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			/*BufferedReader br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();*/

		  }
	  
  }


