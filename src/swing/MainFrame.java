package swing;

import backend.Deck;
import backend.Flashcard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

public class MainFrame extends JFrame {

    private JPanel deckList;


    private JButton newDeck;

    private Flashcard fc;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!

    }

    private void $$$setupUI$$$() {

    }

    public MainFrame(String title) {
        super(title);

        fc=new Flashcard();
        fc.loadDecks();
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
        this.add(newDeck, BorderLayout.SOUTH);

        setDeckList();


    }

    public void refresh(){
        remove(deckList);
        setDeckList();
        revalidate();
        repaint();
    }

    class newDeckButtonActionListener implements ActionListener {

        public newDeckButtonActionListener(){};

        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("createNewDeck")) {

                fc.addDeck(new Deck("New deck"));

                refresh();

            }
        }

    }

    private MainFrame getThis(){return this;}

    class editDeckButtonActionListener implements ActionListener {

        private Deck d;

        public editDeckButtonActionListener(Deck d){this.d=d;};

        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("editDeck")) {
                DeckFrame df=new DeckFrame(d.getName(), d,fc, getThis());
                df.setVisible(true);

            }
        }

    }

    class learnDeckButtonActionListener implements ActionListener {

        private Deck d;

        public learnDeckButtonActionListener(Deck d){this.d=d;};

        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("learnDeck")) {
                LearnFrame lf=new LearnFrame(d, getThis());
                lf.setVisible(true);

            }
        }

    }



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
