package backend;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextInformationTest {
    @Test
    public void getInfo() {
        TextInformation textInfo=new TextInformation("szoveg");
        String txt=textInfo.getInformation();
        assertTrue(txt.contentEquals(new String("szoveg")));
    }
}