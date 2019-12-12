package backend;

import java.io.Serializable;
import java.util.*;

/***
 * A Card osztály tartalmazza a Side-okat, és a belőlük generált Challengeket
 */
public class Card implements Comparable<Card>, Serializable {
    private List<Side> sides;
    private SortedSet<Challenge> challenges;


    /***
     * Létrehozza a Card-ot Side-ok listájából, az átvett Side-okból pedig generálja a Challenge-eket úgy hogy, mindegyik
     * Side egy-egy kérdés lesz, a hozzájuk tartozó válasz pedig a többi Side
     * @param sides Side-ok listája, amikből létre szeretnénk hozni a Card-ot
     */
    public Card(List<Side> sides){
        this.sides=sides;
        challenges=new TreeSet<Challenge>();
        for(Side s: sides){
            Set<Side> ch=new TreeSet<Side>();
            ch.add(s);
            Set<Side> rp=new TreeSet<Side>();
            for(Side s2: sides){
                if(!(s.equals(s2))) rp.add(s2);
            }

            challenges.add(new Challenge(this, ch, rp));
        }

    }

    /***
     * Ismételni kell-e a kártyát már?
     * @return true, ha ismételni kell
     */
    public Boolean isDue(){
        return challenges.first().isDue();
    }

    /***
     * A kártyához tartozó Challengek közül lekéri a legkorábban ismételendő Challenge-et
     * @return A legkorábban ismételendő Challenge
     */
    public Challenge getFirstChallenge(){return challenges.first();}

    /***
     * A kártyához tartozó Challengek közül lekéri a legkésőbb ismételendő Challenge-et
     * @return A legkésőbb ismételendő Challenge
     */
    public Challenge getLastChallenge(){return challenges.last();}

    /***
     * Visszaadja a paraméterben megadott indexű Side-ot
     * @param i: Side indexe, 0-tól
     * @return A paraméterben megadott indexű Side
     * @throws IndexOutOfBoundsException Ha nem létezik ilyen indexű
     */
    public Side getSide(int i) throws IndexOutOfBoundsException{

        return sides.get(i);
    }

    /***
     * Hány oldalt tárol a Card
     * @return a tárolt Side-ok száma
     */
    public int getNumberOfSides(){return sides.size();}


    @Override
    /***
     * Összehasonlít két Card-ot az első Challenge-eik alapján
     * @return mint a többi compareTo
     */
    public int compareTo(Card card) {
        return challenges.first().compareTo(card.challenges.first());
    }
}
