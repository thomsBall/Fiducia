import org.junit.Test;

import org.junit.Assert;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class IntegrationTest{

    @Test
    public void testCreateJob(){

        assertEquals(JobInterview.createJob("http://localhost:8080/", "IntegrationTest", "<flow-definition plugin=\"workflow-job@2.32\">\n" +
                "<keepDependencies>false</keepDependencies>\n" +
                "<properties/>\n" +
                "<triggers/>\n" +
                "<disabled>false</disabled>\n" +
                "</flow-definition>"),200);


    }


    @Test
    public void testOfCreatedJobs(){
        String jobname = "IntegrationTest";
        JobInterview.createJob("http://localhost:8080/", "IntegrationTest", "<flow-definition plugin=\"workflow-job@2.32\">\n" +
                "<keepDependencies>false</keepDependencies>\n" +
                "<properties/>\n" +
                "<triggers/>\n" +
                "<disabled>false</disabled>\n" +
                "</flow-definition>");
        assertFalse(JobInterview.kmpSearch("IntegrationTest" ,JobInterview.getAllJobs().toString()));
        assertTrue(JobInterview.kmpSearch("integrationtest" ,JobInterview.getAllJobs().toString()));

    }



}
