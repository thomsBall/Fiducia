
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobInterview {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while(flag){
            System.out.println("Bitte gib einen Jobnamen eingeben der nur aus Zahlen und Kleinbuchstaben besteht. Sonderzeichen dürfen nicht verwendet werden");
            String jobname = sc.nextLine();

            if(proofOfContainingJob(jobname.toLowerCase(), singleJobList(getAllJobsToArray())) & proofCorrectChars(jobname)){
                createJob("http://localhost:8080/",jobname,
                        "<flow-definition plugin=\"workflow-job@2.32\">\n" +
                                "<keepDependencies>false</keepDependencies>\n" +
                                "<properties/>\n" +
                                "<triggers/>\n" +
                                "<disabled>false</disabled>\n" +
                                "</flow-definition>");
                flag = false;
                sc.close();
            }
        }
    }

    //Prüfen ob Job bereits existiert
    public static boolean proofOfContainingJob(String newJobName, List<String> jobList) {
        if(jobList.contains(newJobName)){
            System.out.println("Dein Jobname existiert bereits");
            return false;
        }
        return true;
    }

    //Prüfen ob keine Sonderzeichen in dem Namen enthalten sind
    public static boolean proofCorrectChars(String jobname){
        String regex = "^[a-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jobname.toLowerCase());
        if(matcher.matches()){
            return true;
        }else {
            System.out.println("Dein Jobname enthält verbotene Zeichen");
            return false;
        }
    }

    //Prüfen ob der Name klein geschrieben ist
    public static boolean proofLowerCase(String jobname){
        String regex = "^[a-z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jobname);
        return matcher.matches();
    }


    //Schreibt alle Jobs in ein String
    public static String getAllJobsToArray() {
        String results = null;
        try {
            URL url = new URL("http://localhost:8080/api/json?tree=jobs[name,color]");
            String user = "thomsBall"; // username
            String pass = "118991e91fb6077e1782904753f0f720f1"; // password or API token
            String authStr = user + ":" + pass;
            String encoding = Base64.getEncoder().encodeToString(authStr.getBytes("utf-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoding);

            InputStream content = connection. getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            String line;
//            System.out.println("Done ::"  + connection.getResponseCode());
            while ((line = in.readLine()) != null) {
                results = line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    //Erstellt einzelne Strings aus JSON
    public static List<String> singleJobList(String getAllJobs) {
        List<String> results = new ArrayList<>();
        String jsonResult = getAllJobs;
        JSONObject jsonObject = new JSONObject(jsonResult);
        JSONArray jsonArray = jsonObject.getJSONArray("jobs");
        for (int i = 0; i <jsonArray.length(); i++){
            results.add(jsonArray.getJSONObject(i).getString("name"));
        }
        return results;
    }

    //Erstellen des Jenkins Jobs
    public static int createJob(String url, String newJobName, String configXML){
        if (!proofLowerCase(newJobName)){
            newJobName = newJobName.toLowerCase();
            System.out.println("Dein Jobname wurde automatisch angepasst");
        }
        Client client = Client.create();
		client.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter("thomsball", "118991e91fb6077e1782904753f0f720f1"));
        WebResource webResource = client.resource(url+"/createItem?name="+newJobName);
        ClientResponse response = webResource.type("application/xml").post(ClientResponse.class, configXML);
        String jsonResponse = response.getEntity(String.class);
        client.destroy();
//        System.out.println("Response createJob:::::"+ jsonResponse);
        System.out.println("Dein Job wurde erfolgreich erstellt");
        return response.getStatus();
    }

    //Starten des Jenkins Job
    private static void startJenkinsJob(String jobname, String jobToken) {
        try {
            URL url = new URL("http://localhost:8080/job/"+ jobname+ "/build");
            String user = "thomsBall"; // username
            String pass = jobToken;
            String authStr = user + ":" + pass;
            String encoding = Base64.getEncoder().encodeToString(authStr.getBytes("utf-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            InputStream content = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            String line;
//            System.out.println("Done ::"  + connection.getResponseCode());
            JSONObject json = new JSONObject();

            while ((line = in.readLine()) != null) {
                json.put("jenkins",line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
