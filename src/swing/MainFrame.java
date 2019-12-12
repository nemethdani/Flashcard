package swing;

import backend.Deck;
import backend.Flashcard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/***
 * Program főablaka, kezeli a backendet
 */
public class MainFrame extends JFrame {

    private JPanel deckList;


    private JButton newDeck;

    private Flashcard fc;
    private Set<DeckFrame> deckFrames;


    /***
     * Létrehozza a főablakot
     * @param title főablak kívánt címe
     */
    public MainFrame(String title) {
        super(title);

        fc=new Flashcard();
        fc.loadDecks();
        deckFrames=new HashSet<DeckFrame>();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fc.saveDecks();
                System.exit(0);
            }
        });
        this.setMinimumSize(new Dimension(400,100));

        newDeck=new JButton("New deck");
        newDeck.setActionCommand("createNewDeck");
        ActionListener al=new newDeckButtonActionListener();
        newDeck.addActionListener(al);

        JButton proFunc=new JButton("Pro function");
        proFunc.setActionCommand("proFunc");
        //proFunc.addActionListener(new proFuncButtonActionListener());


        JPanel buttons=new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.add(newDeck);
        //buttons.add(proFunc);
        this.add(buttons, BorderLayout.SOUTH);

        setDeckList();


    }

    /***
     * frissíti a főablakot
     */
    public void refresh(){
        remove(deckList);
        setDeckList();
        revalidate();
        repaint();
        for(DeckFrame df: deckFrames){
            df.refresh();
        }
    }

    /***
     * létrehoz egy új Decket és frissíti a főablakot
     */
    class newDeckButtonActionListener implements ActionListener {

        public newDeckButtonActionListener(){};

        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("createNewDeck")) {

                fc.addDeck(new Deck("New deck"));

                refresh();

            }
        }

    }

    /***
     * Visszaadja a MainFramet
     * @return this
     */
    private MainFrame getThis(){return this;}

    /***
     * törli a Frame által megnyitott DeckFrame-ek listájából az DeckFrame-t
     * @param df törlendő DeckFrame
     */
    public void removeDeckFrame(DeckFrame df){
        deckFrames.remove(df);
    }

    /***
     * Megynyitja a DeckFrame ablakot szerkesztéshez
     */
    class editDeckButtonActionListener implements ActionListener {

        private Deck d;

        /***
         *
         * @param d melyik Deck-et szeretnénk szerkeszteni
         */
        public editDeckButtonActionListener(Deck d){this.d=d;};

        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("editDeck")) {
                DeckFrame df=new DeckFrame(d.getName(), d,fc, getThis());
                df.setVisible(true);
                deckFrames.add(df);

            }
        }

    }


    /***
     * megnyitja a LearnFrame ablakot
     */
    class learnDeckButtonActionListener implements ActionListener {

        private Deck d;

        /***
         *
         * @param d melyik Decket szeretnénk tanulni
         */
        public learnDeckButtonActionListener(Deck d){this.d=d;};

        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("learnDeck")) {
                LearnFrame lf=new LearnFrame(d, getThis());
                lf.setVisible(true);

            }
        }

    }


    /***
     * generálja a Deck listát
     */
    private void setDeckList(){
        deckList = new JPanel();
        deckList.setLayout(new BoxLayout(deckList, BoxLayout.Y_AXIS));
        this.add(deckList, BorderLayout.CENTER);
        Set<Deck> decks=fc.getDecks();
        for(Deck d: decks){
            JPanel decklistItem = new JPanel();
            decklistItem.setLayout(new BoxLayout(decklistItem, BoxLayout.X_AXIS));
            deckList.add(decklistItem);
            JLabel deckNameValue = new JLabel();
            deckNameValue.setText(d.getName());
            decklistItem.add(deckNameValue);
            decklistItem.add(Box.createHorizontalGlue());
            JLabel duevalue = new JLabel();
            duevalue.setText(Integer.toString(d.getNumberOfDue()));
            decklistItem.add(duevalue);

            decklistItem.add(Box.createRigidArea(new Dimension(20,0)));

            JButton learn = new JButton();
            learn.setText("Learn");
            learn.setActionCommand("learnDeck");
            learn.addActionListener(new learnDeckButtonActionListener(d));
            decklistItem.add(learn);

            JButton edit = new JButton();
            edit.setText("Edit");
            edit.setActionCommand("editDeck");
            ActionListener al_edit=new editDeckButtonActionListener(d);
            edit.addActionListener(al_edit);
            decklistItem.add(edit);

            deckList.add(decklistItem);
        }
    }






}
