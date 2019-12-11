package backend;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Deck extends AbstractTableModel implements Serializable, Comparable {
    private List<Card> cardlist;
    private String name;
    private boolean ordered;
    private int numberOfSides=2;


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
    public void setName(String newname){this.name=newname;}
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
            if(getNumberOfDue()>0){
                return cardlist.get(0).getFirstChallenge();
            }
            else{
                //ha utolsó card utolsó ch-e még nem tanult
                Card lastCard=cardlist.get(cardlist.size()-1);
                Challenge lastChallenge=lastCard.getLastChallenge();
                LocalDateTime lastRep=lastChallenge.getRepetitionTime();
                if(lastRep.equals(LocalDateTime.MAX)){
                    return cardlist.get(cardlist.size()-1).getLastChallenge();
                }
                else{
                    // összes card közül az első
                    return cardlist.get(0).getFirstChallenge();
                }
            }
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

    public Card getCardByRow(int i){return cardlist.get(i);}



    @Override
    public int compareTo(Object d) {
        return name.compareTo(((Deck) d).name);
    }

    @Override
    public int getRowCount() {
        return cardlist.size();
    }

    @Override
    public int getColumnCount() {
        return numberOfSides+1;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        if(i1>=2){
            return cardlist.get(i).getFirstChallenge().getRepetitionTime();
        }
        return cardlist.get(i).getSide(i1).getInformation().getInformation();
    }
}





