package backend;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Deck implements Serializable, Comparable {
    private List<Card> cardlist;
    private String name;
    private boolean ordered;

    private void orderIfNecessary(){
        if(!ordered){
            Collections.sort(cardlist);
            ordered=true;
        }
    }

    public Deck(String name){
        cardlist=new ArrayList<Card>();
        this.name=name;
        ordered=true;
    }

    public String getName(){return name;}
    public int getNumberOfDue(){
        orderIfNecessary();
        Iterator it=cardlist.iterator();
        int count=0;
        while(it.hasNext()){
            Card c=(Card)it.next();
            if(c.isDue()){
                count++;
            }
            else break;
        }
        return count;
    }
    // tanulhatunk tovább is ha akarunk, nem csak a due elemeket
    public Challenge getFirstChallenge() throws IndexOutOfBoundsException{
        if(cardlist.size()>0){
            orderIfNecessary();
            return cardlist.get(0).getFirstChallenge();
        }
        else throw new IndexOutOfBoundsException("deck üres");
    }
    public void learn(Challenge c, int grade){
        try {
            c.updateRepetitionTime(grade);
            ordered=false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    public void addCard(Card c){
        cardlist.add(c);

    }
    public void deleteCard(Card c){
        cardlist.remove(c);
    }

    public void serialize(File parent) throws IOException {
        File f=new File(parent, name);
        FileOutputStream fos=new FileOutputStream(f);
        ObjectOutputStream ous=new ObjectOutputStream(fos);
        ous.writeObject(this);
        ous.close();
    }



    @Override
    public int compareTo(Object d) {
        return name.compareTo(((Deck) d).name);
    }
}





