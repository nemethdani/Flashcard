package swing;

import backend.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DeckFrame extends JFrame {
    private JTextField deckName;
    private JTable decksTable;
    private JScrollPane jsp;
    private JPanel buttons;
    private JButton newCard;
    private JButton editCard;
    private JButton deleteCard;

    private Deck data;
    private MainFrame main;

    public DeckFrame(String title, Deck data, MainFrame main){
        super(title);
        this.main=main;
        this.data=data;
        this.setMinimumSize(new Dimension(400,100));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                data.setName(deckName.getText());
                main.refresh();
                dispose();

            }
        });

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
