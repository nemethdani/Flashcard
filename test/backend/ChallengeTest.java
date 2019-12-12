package backend;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class ChallengeTest {

    Challenge chinf;
    Set<Side> ch;
    Set<Side> rp;

    @Before
    public void setUp() throws Exception {
        Side side1=new Side(new TextInformation("english"));
        Side side2=new Side(new TextInformation("angol"));
        ch=new TreeSet<Side>();
        ch.add(side1);
        rp=new TreeSet<Side>();
        rp.add(side2);
        chinf=new Challenge(null, ch, rp);
    }

    @Test
    public void isDue() {
        assertFalse(chinf.isDue());

    }

    @Test
    public void getChallenge() {
        assertTrue(chinf.getChallenge().equals(ch));
    }

    @Test
    public void getResponse() {
        assertTrue(chinf.getResponse().equals(rp));
    }

    @Test
    public void getRepetitionTime() {
        assertTrue(chinf.getRepetitionTime().equals(LocalDateTime.MAX));
    }

    @Test
    public void updateRepetitionTime() {
        chinf.updateRepetitionTime(4);
        assertTrue(chinf.getRepetitionTime().compareTo(LocalDateTime.MAX)<0);
    }

    @Test
    public void compareTo() {
        Side side3=new Side(new TextInformation("english"));
        Side side4=new Side(new TextInformation("angol"));
        Set<Side> ch2=new TreeSet<Side>();
        ch2.add(side3);
        Set<Side> rp2=new TreeSet<Side>();
        rp2.add(side4);
        Challenge chinf2=new Challenge(null, ch, rp);

        chinf.updateRepetitionTime(4);

        assertTrue(chinf.getRepetitionTime().compareTo(chinf2.getRepetitionTime())<0);

    }
}