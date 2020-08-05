import org.junit.Test;

import org.junit.Assert;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class UnitTest {

    @Test
    public void testLowerCase(){

        assertTrue(JobInterview.proofLowerCase("test123"));
        assertTrue(JobInterview.proofLowerCase("test"));
        assertFalse(JobInterview.proofLowerCase("Test"));
        assertFalse(JobInterview.proofLowerCase("TeS44!"));

    };


}
