
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobInterview {


    public static void main(String[] args) throws IOException, InterruptedException {
        String jobname = "FIDUCIA_Jobinterview";
        String jobToken = "11d840314d9ac1f512b9e68724c7c3af25";

//        startJenkinsJob(jobname, jobToken);
        createJob("http://localhost:8080/","TestdersdseRRR22","<flow-definition plugin=\"workflow-job@2.32\">\n" +
                "<keepDependencies>false</keepDependencies>\n" +
                "<properties/>\n" +
                "<triggers/>\n" +
                "<disabled>false</disabled>\n" +
                "</flow-definition>");

    }

    private static void startJenkinsJob(String jobname, String jobToken) {
        try {
            URL url = new URL("http://localhost:8080/job/"+ jobname+ "/build"); // Jenkins URL localhost:8080, job named 'test'
            String user = "thomsBall"; // username
            String pass = jobToken; // password or API token
            String authStr = user + ":" + pass;
            String encoding = Base64.getEncoder().encodeToString(authStr.getBytes("utf-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            InputStream content = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            String line;
            System.out.println("Done ::"  + connection.getResponseCode());
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //

    public static String createJob(String url, String newJobName, String configXML){
        if (!proofLowerCase(newJobName)){
            newJobName = newJobName.toLowerCase();
        }
        Client client = Client.create();
		client.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter("thomsball", "118991e91fb6077e1782904753f0f720f1"));
        WebResource webResource = client.resource(url+"/createItem?name="+newJobName);
        ClientResponse response = webResource.type("application/xml").post(ClientResponse.class, configXML);
        String jsonResponse = response.getEntity(String.class);
        client.destroy();
        System.out.println("Response createJob:::::"+jsonResponse);
        return jsonResponse;
    }

    public static boolean proofLowerCase(String jobname){
        String regex = "^[a-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jobname);
        return matcher.matches();
    }
}
