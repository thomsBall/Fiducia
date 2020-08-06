
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
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


    public static void main(String[] args) throws IOException, InterruptedException {


        String jobname = "FIDUCIA_Jobinterview";
        String jobToken = "11d840314d9ac1f512b9e68724c7c3af25";

//        startJenkinsJob(jobname, jobToken);
//        createJob("http://localhost:8080/","ConfigXML",
//                "<flow-definition plugin=\"workflow-job@2.32\">\n" +
//                "<keepDependencies>false</keepDependencies>\n" +
//                "<properties/>\n" +
//                "<triggers/>\n" +
//                "<disabled>false</disabled>\n" +
//                "</flow-definition>");




//            String name;
//            JSONArray array = new JSONArray(string_of_json_array);
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject row = array.getJSONObject(i);
//                name = row.getString("name");
//                System.out.println(name);
//            }




    }

    public static List<String> getAllJobs() {
        List results = new ArrayList();
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
            System.out.println("Done ::"  + connection.getResponseCode());
            while ((line = in.readLine()) != null) {
                results.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
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
            JSONObject json = new JSONObject();

            while ((line = in.readLine()) != null) {
                json.put("jenkins",line);

//                System.out.println(line);
                System.out.println(json.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //

    public static int createJob(String url, String newJobName, String configXML){
        if (!proofLowerCase(newJobName)){
            newJobName = newJobName.toLowerCase();
        }

        Client client = Client.create();
		client.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter("thomsball", "118991e91fb6077e1782904753f0f720f1"));
        WebResource webResource = client.resource(url+"/createItem?name="+newJobName);
        ClientResponse response = webResource.type("application/xml").post(ClientResponse.class, configXML);
        String jsonResponse = response.getEntity(String.class);
        client.destroy();
        System.out.println("Response createJob:::::"+ jsonResponse);
        return response.getStatus();
    }

    public static boolean proofLowerCase(String jobname){
        String regex = "^[a-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jobname);
        return matcher.matches();
    }


    public static boolean kmpSearch(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();

        int lps[] = new int[M];
        int j = 0; // index for pat[]
        int foundPatternNumb = 0;

        computeLPSArray(pat, M, lps);
        boolean wert = false;
        int i = 0; // index for txt[]
        while (i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                wert = true;
                foundPatternNumb++;

                System.out.println(wert);
                System.out.println("Found pattern "
                        + "at index " + (i - j));
                j = lps[j - 1];
            }

            else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
        return wert;
    }

    public static void computeLPSArray(String pat, int M, int lps[]) {

        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else // (pat[i] != pat[len])
            {

                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

}
