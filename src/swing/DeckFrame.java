package swing;

import backend.Deck;

import javax.swing.*;
import java.awt.*;

public class DeckFrame extends JFrame {
    private JTextField deckName;
    private JTable decksTable;
    private JScrollPane jsp;
    private JPanel buttons;
    private JButton newCard;
    private JButton editCard;
    private JButton deleteCard;

    private Deck data;

    public DeckFrame(Deck data){
        this.data=data;
        this.setMinimumSize(new Dimension(400,100));

        deckName=new JTextField(data.getName());
        add(deckName, BorderLayout.NORTH);

        decksTable=new JTable(data);
        jsp=new JScrollPane(decksTable);
        add(jsp, BorderLayout.CENTER);

        buttons=new JPanel();
        buttons.setLayout(new FlowLayout());
        add(buttons, BorderLayout.SOUTH);

        newCard=new JButton("New card");
        buttons.add(newCard);

        editCard=new JButton("Edit card");
        buttons.add(editCard);

        deleteCard=new JButton("Delete card");
        buttons.add(deleteCard);



    }
}
