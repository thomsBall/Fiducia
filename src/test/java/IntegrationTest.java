import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class IntegrationTest{

    @Test
    public void testCreateJob(){
        int zufallszahl = (int)(Math.random() * 10);
        assertEquals(JobInterview.createJob("http://localhost:8080/", "IntegrationsTest"+ zufallszahl, "<flow-definition plugin=\"workflow-job@2.32\">\n" +
                "<keepDependencies>false</keepDependencies>\n" +
                "<properties/>\n" +
                "<triggers/>\n" +
                "<disabled>false</disabled>\n" +
                "</flow-definition>"),200);


    }

    public boolean proofOfContainingJobList (List<String> jobList, String jobname){
        for(String results : jobList)
            if(results.equals(jobname)){
                return true;
            }
        return false;
    }


    @Test
    public void testOfCreatedJobs(){
        JobInterview.createJob("http://localhost:8080/", "IntegrationTest", "<flow-definition plugin=\"workflow-job@2.32\">\n" +
                "<keepDependencies>false</keepDependencies>\n" +
                "<properties/>\n" +
                "<triggers/>\n" +
                "<disabled>false</disabled>\n" +
                "</flow-definition>");


        assertTrue(proofOfContainingJobList(JobInterview.singleJobList(JobInterview.getAllJobsToArray()), "integrationtest"));
        assertFalse(proofOfContainingJobList(JobInterview.singleJobList(JobInterview.getAllJobsToArray()), "IntegrationTest"));


    }

    @Test
    public void testDuplicate(){
        assertEquals(JobInterview.createJob("http://localhost:8080/", "IntegrationsTest", "<flow-definition plugin=\"workflow-job@2.32\">\n" +
                "<keepDependencies>false</keepDependencies>\n" +
                "<properties/>\n" +
                "<triggers/>\n" +
                "<disabled>false</disabled>\n" +
                "</flow-definition>"),400);

    };



}
