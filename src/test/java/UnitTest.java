import org.junit.Test;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class UnitTest {

    @Test
    public void testLowerCase(){

        assertTrue(JobInterview.proofLowerCase("test"));
        assertTrue(JobInterview.proofLowerCase("test"));
        assertFalse(JobInterview.proofLowerCase("Test"));
        assertFalse(JobInterview.proofLowerCase("TeST"));

    };

    @Test
    public void testChars(){

        assertTrue(JobInterview.proofCorrectChars("test123"));
        assertTrue(JobInterview.proofCorrectChars("test"));
        assertFalse(JobInterview.proofCorrectChars("Test@"));
        assertFalse(JobInterview.proofCorrectChars("TeS44!"));

    };

    @Test
    public void testSingleJobList(){

        String jsonMock = "{jobs:[{name:TestSingleJobList1}, {name:TestSingleJobList2}]}";
        List<String> results = new ArrayList<>();
        results.add("TestSingleJobList1");
        results.add("TestSingleJobList2");
        assertEquals(JobInterview.singleJobList(jsonMock),results);
    };

    @Test
    public void testDuplicate(){

        List<String> results = new ArrayList<>();
        results.add("TestDuplicate1");
        results.add("TestDuplicate2");
        assertTrue(JobInterview.proofOfContainingJob("DuplicateTest", results));
        assertFalse(JobInterview.proofOfContainingJob("TestDuplicate2", results));
    };













}
