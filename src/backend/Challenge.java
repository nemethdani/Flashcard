package backend;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public class Challenge implements Comparable<Challenge> {

    //ezt az értéket sosem használjuk, csak  a szorzatát
    static private Duration initialRepetitionInterval=Duration.ofSeconds(12);

    private Card card;
    private Set<Side> challenge;
    private Set<Side> response;
    private double easinessFactor;
    private int repetitionCount;
    private Duration repetitionInterval;
    private LocalDateTime repetitionTime;

    public Challenge(Card card, Set<Side> challenge, Set<Side> response) {
        this.card=card;
        this.challenge=challenge;
        this.response=response;

        easinessFactor=2.5;
        repetitionCount=0;
        repetitionInterval= initialRepetitionInterval;
        repetitionTime=LocalDateTime.MIN;
    }

    public Boolean isDue(){
        return repetitionTime.isBefore(LocalDateTime.now());
    }

    public Set<Side> getChallenge(){return challenge;}
    public Set<Side> getResponse(){return response;}
    public LocalDateTime updateRepetitionTime(int grade){
        if(grade<0 || grade>5) throw new IllegalArgumentException("0<= grade <=5");
        if(grade<3){repetitionInterval=initialRepetitionInterval;}
        else{
            double easinessFactor2=easinessFactor-0.8+0.28*grade-0.02*grade*grade;
            if(easinessFactor2>=1.3) easinessFactor=easinessFactor2;
            else easinessFactor=1.3;

        }
        repetitionInterval=Duration.ofSeconds((long)((double)repetitionInterval.getSeconds() * easinessFactor));
        repetitionTime=LocalDateTime.now().plusSeconds(repetitionInterval.getSeconds());
        return repetitionTime;
    }



    @Override
    public int compareTo(Challenge challenge) {
        return 0;
    }
}
