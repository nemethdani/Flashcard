package swing;

import backend.Card;
import backend.Deck;
import backend.Flashcard;
import backend.Side;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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
    private Flashcard fc;

    public void refresh(){
        decksTable.revalidate();
        decksTable.repaint();
    }

    private DeckFrame getThis(){return this;}

    class newCardButtonActionListener implements ActionListener {
        public newCardButtonActionListener(){};
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("newCard")) {
                EditCardFrame ecf=new EditCardFrame(new Card(new ArrayList<Side>()), fc, true, data, getThis());
                ecf.setVisible(true);
            }
        }
    }

    class editCardButtonActionListener implements ActionListener {
        public editCardButtonActionListener(){};
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("editCard")) {
                int selectedRow=decksTable.getSelectedRow();
                Card selectedCard=data.getCardByRow(selectedRow);
                EditCardFrame ecf=new EditCardFrame(selectedCard, fc, false, data, getThis());
                ecf.setVisible(true);
            }
        }
    }

    public DeckFrame(String title, Deck data, Flashcard fc,  MainFrame main){
        super(title);
        this.main=main;
        this.data=data;
        this.setMinimumSize(new Dimension(400,100));
        this.fc=fc;

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fc.getDecks().remove(data);
                data.setName(deckName.getText());
                fc.getDecks().add(data);
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
        newCard.addActionListener(new newCardButtonActionListener());
        newCard.setActionCommand("newCard");

        editCard=new JButton("Edit card");
        buttons.add(editCard);
        editCard.addActionListener(new editCardButtonActionListener());
        editCard.setActionCommand("editCard");

        deleteCard=new JButton("Delete card");
        buttons.add(deleteCard);



    }
}
