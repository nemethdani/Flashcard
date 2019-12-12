package backend;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

/***
 * A Challenge megtestesít egy Card-hoz tartozó kérdés-válasz párt, ahol a kérdés és a válasz is több Side-ból állhat
 */
public class Challenge implements Comparable<Challenge>, Serializable {

    //ezt az értéket sosem használjuk, csak  a szorzatát
    static private Duration initialRepetitionInterval=Duration.ofSeconds(12);

    private Card card;
    private Set<Side> challenge;
    private Set<Side> response;
    private double easinessFactor;
    private int repetitionCount;
    private Duration repetitionInterval;
    private LocalDateTime repetitionTime;

    /***
     * Létrehoz egy Challenge-et, az alapértelmezett maximumális repetitionTime-al
     * @param card Amelyik Card-hoz tartozik
     * @param challenge Set, ami tartalmazza a kérdésben szereplő Side-okat
     * @param response Set, ami tartalmazza a válaszban szereplő Side-okat
     */
    public Challenge(Card card, Set<Side> challenge, Set<Side> response) {
        this.card=card;
        this.challenge=challenge;
        this.response=response;

        easinessFactor=2.5;
        repetitionCount=0;
        repetitionInterval= initialRepetitionInterval;
        repetitionTime=LocalDateTime.MAX; // A lejárt ismétlésű kártyák fontosabbak, mint az újak
    }

    /***
     * Ismételni kell-e már a Challenge-et
     * @return Igaz, ha repetitionTime kisebb mint now()
     */
    public Boolean isDue(){
        return repetitionTime.isBefore(LocalDateTime.now());
    }

    /***
     * Visszaadja a kérdéshez tartozó Side-ok set-jét
     * @return A kérdéshez tartozó Side-ok Set-je
     */
    public Set<Side> getChallenge(){return challenge;}

    /***
     * Visszadja a válaszhoz tartozó Side-ok Set-jét;
     * @return A válaszhoz tartozó Side-ok Set-je
     */
    public Set<Side> getResponse(){return response;}

    /***
     * Mikor kell újra ismételni
     * @return Következő ismétlés ideje
     */
    public LocalDateTime getRepetitionTime(){return repetitionTime;}

    /***
     * Frissíti a repetitionTime-ot a megadott parmaéter alapján módosított SM-2 algoritmussal
     * @param grade Mennyire sikerült eltalálni a választ: 0 kisebb egynelő grade kisebb egyenlő 5
     * @return  A következő ismétlés ideje
     * @throws IllegalArgumentException Ha nem [0;5] intervallumban adjuk a paramétert meg
     */
    public LocalDateTime updateRepetitionTime(int grade) throws IllegalArgumentException{
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
    /***
     * A korábban ismételendő lesz a kisebb
     */
    public int compareTo(Challenge challenge) {
        return this.repetitionTime.compareTo(challenge.repetitionTime);
    }
}
