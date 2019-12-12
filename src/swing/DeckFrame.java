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

/***
 * Ez jeleníti meg a Deck adatait, Card-jait, itt lehet a Decket szerkeszteni
 */
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

    /***
     * Frissíti a Frame-et
     */
    public void refresh(){
        decksTable.revalidate();
        decksTable.repaint();
    }

    /***
     * Visszadja a Frame-et (belső osztályok miatt)
     * @return this
     */
    private DeckFrame getThis(){return this;}

    /***
     * Új kártya eseményre megnyitja az EditCardFrame-et új kártya módban
     */
    class newCardButtonActionListener implements ActionListener {
        public newCardButtonActionListener(){};
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("newCard")) {
                EditCardFrame ecf=new EditCardFrame(new Card(new ArrayList<Side>()), fc, true, data, getThis());
                ecf.setVisible(true);
            }
        }
    }

    /***
     * editCard eseményre megnyitja az EditCardFrame-et edit módban, a kijelölt Card szerkesztésére
     */
    class editCardButtonActionListener implements ActionListener {
        public editCardButtonActionListener(){};
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("editCard")) {
                int selectedRow=decksTable.getSelectedRow();
                if(selectedRow>=0){
                    Card selectedCard=data.getCardByRow(selectedRow);
                    EditCardFrame ecf=new EditCardFrame(selectedCard, fc, false, data, getThis());
                    ecf.setVisible(true);
                }

            }
        }
    }

    /***
     * deleteCard eseményre törli a kiejlölt kártyát
     */
    class deleteCardButtonActionListener implements ActionListener {
        public deleteCardButtonActionListener(){};
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("deleteCard")) {
                int selectedRow=decksTable.getSelectedRow();
                if(selectedRow>=0){
                    Card selectedCard=data.getCardByRow(selectedRow);
                    data.deleteCard(selectedCard);
                    refresh();
                }

            }
        }
    }

    /***
     * Létrehozza a DeckFrame-et
     * @param title A Frame címe
     * @param data A kezelt Deck
     * @param fc A kezelő Flashcard osztály
     * @param main A MainFrame, ahonnan megnyitották
     */
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
                main.removeDeckFrame(getThis());
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
        deleteCard.setActionCommand("deleteCard");
        deleteCard.addActionListener(new deleteCardButtonActionListener());

        pack();

    }
}
