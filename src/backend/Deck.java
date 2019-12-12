package backend;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/***
 * Tárolja a Card-okat, bővíti az AbstractTableModel-t táblázatos kiíráshoz
 */
public class Deck extends AbstractTableModel implements Serializable, Comparable {
    private List<Card> cardlist;
    private String name;
    private boolean ordered;
    private int numberOfSides=2;

    /***
     * Újra növekvő sorrendbe rendezi a Card-okat, ha !ordered
     */
    private void orderIfNecessary(){
        if(!ordered){
            Collections.sort(cardlist);
            ordered=true;
        }
    }

    /***
     * Létrehoz egy üres Deck-et, paraméterben megadott névvel
     * @param name A Deck kívánt neve
     */
    public Deck(String name){
        cardlist=new ArrayList<Card>();
        this.name=name;
        ordered=true;
    }

    /***
     * Visszadja a Deck nevét
     * @return Deck neve
     */
    public String getName(){return name;}

    /***
     * Beállítja a Deck nevét
     * @param newname a Deck új neve
     */
    public void setName(String newname){this.name=newname;}

    /***
     * Hány kártyát kell ismételni a Deckből
     * @return Hány kártyát kell ismételni a Deckből
     */
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

    /***
     * Visszaadja a következő challenget:
     * Ha van Due, akkor azokból a legkorábbit
     * Ha nincs Due és van még nem látott Card, akkor a nemlátottakból az utolsó Card utolsó Challengét (új tanulás)
     * Egyébként a legkorábbi kártyát (korai ismétlés)
     * @return következő Challenge
     * @throws IndexOutOfBoundsException Ha üres a Deck
     */
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

    /***
     * Frissíti a megadott challenge ismétlési idejét grade szerint
     * @param c Az ismételt Challenge
     * @param grade 0-5 értékelés
     */
    public void learn(Challenge c, int grade){
        try {
            c.updateRepetitionTime(grade);
            ordered=false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /***
     * Kártyát ad a Deck-hez
     * @param c A Deck-hez adandó kártya
     */
    public void addCard(Card c){
        cardlist.add(c);

    }

    /***
     * Kitöröl egy kártyát a Deckből
     * @param c A törlendő kártyaa
     */
    public void deleteCard(Card c){
        cardlist.remove(c);
    }

    /***
     * Serializálja a Deck-et
     * @param parent A célmappa
     * @throws IOException ha nem sikerült serializálni
     */
    public void serialize(File parent) throws IOException {
        File f=new File(parent, name);
        FileOutputStream fos=new FileOutputStream(f);
        ObjectOutputStream ous=new ObjectOutputStream(fos);
        ous.writeObject(this);
        ous.close();
    }

    /***
     * Visszaadja az i-edik sorhoz tartozó Card-ot
     * @param i A sor indexe
     * @return Az i-edik Card
     */
    public Card getCardByRow(int i){return cardlist.get(i);}



    @Override
    /***
     * ABC rend szerint hasonlítja a Deck-eket
     */
    public int compareTo(Object d) {
        return name.compareTo(((Deck) d).name);
    }

    @Override
    /***
     * Hány Card van a deckben
     */
    public int getRowCount() {
        return cardlist.size();
    }

    @Override
    /***
     * Hány oszlopa legyen a táblázatnak: A Card-ok Sidejeai +1
     */
    public int getColumnCount() {
        return numberOfSides+1;
    }

    @Override
    /***
     * A Side oszlopokban a Side szövege, a harmadikban a Card első Challengének ismétlési ideje
     */
    public Object getValueAt(int i, int i1) {
        if(i1>=2){
            return cardlist.get(i).getFirstChallenge().getRepetitionTime();
        }
        return cardlist.get(i).getSide(i1).getInformation().getInformation();
    }
}





