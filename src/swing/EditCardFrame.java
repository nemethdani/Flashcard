package swing;

import backend.Card;
import backend.Deck;
import backend.Flashcard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditCardFrame extends JFrame{
    private Card card;
    private Flashcard flashcard;

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



    EditCardFrame(Card card, Flashcard flashcard){
        super("Edit card");
        this.setMinimumSize(new Dimension(400,100));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.card=card;
        this.flashcard=flashcard;

        info1=new JTextField();
        if(card.getNumberOfSides()>=1) info1.setText((String)card.getSide(0).getInformation().getInformation());
        info2=new JTextField();
        if(card.getNumberOfSides()>=2) info1.setText((String)card.getSide(1).getInformation().getInformation());
        deckLabel=new JLabel("Deck:");
        decks=new JComboBox<String>(flashcard.getDeckNames());
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
        commands=new JPanel();
        commands.setLayout(new FlowLayout());
        commands.add(delete);
        commands.add(cancel);
        commands.add(save);
        this.add(commands, BorderLayout.SOUTH);









    }
}
