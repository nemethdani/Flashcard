package backend;

import java.util.*;

public class Card implements Comparable<Card>{
    private List<Side> sides;
    private SortedSet<Challenge> challenges;

    public Card(List<Side> sides){
        this.sides=sides;
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

    public Boolean isDue(){
        return challenges.first().isDue();
    }

    public Challenge getFirstChallenge(){return challenges.first();}
    public Side getSide(int i){return sides.get(i);}


    @Override
    public int compareTo(Card card) {
        return challenges.first().compareTo(card.challenges.first());
    }
}
