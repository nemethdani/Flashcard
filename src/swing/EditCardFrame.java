package swing;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class EditCardFrame extends JFrame{
    private Card card;
    private Flashcard flashcard;
    private Boolean isNew;
    private Deck sourceDeck;
    private DeckFrame deckFrame;

    private JTextField info1;
    private JTextField info2;
    private JLabel deckLabel;
    private JComboBox<String> decks;
    private JPanel deckChoice;
    private JPanel settings;
    private JButton delete;
    private JButton cancel;
    private JButton save;
    private JPanel commands;

    class saveCardButtonActionListener implements ActionListener {
        public saveCardButtonActionListener(){};
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("saveCard")){

                TextInformation i1=new TextInformation(info1.getText());
                TextInformation i2=new TextInformation(info2.getText());
                if(card.getNumberOfSides()==0){
                    List<Side> sides=new ArrayList<Side>();
                    sides.add(new Side(i1));
                    sides.add(new Side(i2));
                    card=new Card(sides);
                }
                else {
                    if(card.getNumberOfSides()>=1){card.getSide(0).setInformation(i1);}

                    if(card.getNumberOfSides()>=2){card.getSide(1).setInformation(i2);}
                }

                Deck targetDeck=flashcard.getDeckByName((String)decks.getSelectedItem());
                if(targetDeck.equals(sourceDeck)){
                    if(isNew){
                        targetDeck.addCard(card);
                    }
                }
                else{
                    sourceDeck.deleteCard(card);
                    targetDeck.addCard(card);
                }
                deckFrame.refresh();
            }

        }
    }



    EditCardFrame(Card card, Flashcard flashcard, Boolean isNew, Deck sourceDeck, DeckFrame deckFrame){
        super("Edit card");
        this.setMinimumSize(new Dimension(400,100));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        this.card=card;
        this.flashcard=flashcard;
        this.isNew=isNew;
        this.sourceDeck=sourceDeck;
        this.deckFrame=deckFrame;

        info1=new JTextField();
        if(card.getNumberOfSides()>=1) info1.setText((String)card.getSide(0).getInformation().getInformation());
        info2=new JTextField();
        if(card.getNumberOfSides()>=2) info2.setText((String)card.getSide(1).getInformation().getInformation());
        deckLabel=new JLabel("Deck:");
        decks=new JComboBox<String>(flashcard.getDeckNames());
        decks.setSelectedItem(sourceDeck.getName());
        deckChoice=new JPanel();
        deckChoice.setLayout(new BoxLayout(deckChoice, BoxLayout.X_AXIS));
        deckChoice.add(deckLabel);
        deckChoice.add(decks);

        settings=new JPanel();
        settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
        settings.add(info1);
        settings.add(info2);
        settings.add(deckChoice);
        this.add(settings, BorderLayout.CENTER);

        delete=new JButton("Delete card");
        cancel=new JButton("Cancel");
        save=new JButton("save");
        save.setActionCommand("saveCard");
        save.addActionListener(new saveCardButtonActionListener());
        commands=new JPanel();
        commands.setLayout(new FlowLayout());
        commands.add(delete);
        commands.add(cancel);
        commands.add(save);
        this.add(commands, BorderLayout.SOUTH);









    }
}
