package backend;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SideTest {

    private TextInformation tx1;
    private TextInformation tx2;

    private Side side1;
    private Side side2;

    @Before
    public void setUp() throws Exception {
        tx1=new TextInformation("alma");
        tx2=new TextInformation("banán");
        side1=new Side(tx1);
        side2=new Side(tx2);
    }

    @Test
    public void getInformation() {
        assertTrue(side1.getInformation().equals(tx1));

    }

    @Test
    public void setInformation() {
        TextInformation tx3=new TextInformation("cékla");
        side2.setInformation(tx3);
        assertTrue(side2.getInformation().equals(tx3));
    }

    @Test
    public void compareTo() {
        assertTrue(side1.compareTo(side2) <0);
    }
}