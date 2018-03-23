/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michele
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;



public class ApacheHttpClientPost {
   
        private String postTitle;
        private String postSlug;
        private String postDesc;
        private String postCont;
        private String apiURL;

    public ApacheHttpClientPost(String postTitle, String postSlug, String postDesc, String postCont, String apiURL) {
        
        this.postTitle = postTitle;
        this.postSlug = postSlug;
        this.postDesc = postDesc;
        this.postCont = postCont;
        this.apiURL = apiURL;
        
    }
    
    public void makeHTTPPOSTRequest() {

	  try {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
			"http://localhost/chirpter_api/article/create.php");

		StringEntity input = new StringEntity("{\"token\":\"YI7WYvrEwCf7IXOV+N4RJoXHnKj0N5AOA12BlRZfd7E=\",\"postTitle\":\"JavaPost\",\"postSlug\":\"JavaPost\"}");
		input.setContentType("application/json");
		postRequest.setEntity(input);

		HttpResponse response = httpClient.execute(postRequest);

		BufferedReader br = new BufferedReader(
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

		e.printStackTrace();

	  }

	}

}