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
    private JPanel mainPanel;
    private JPanel deckList;
    private JPanel decklistList;
    //private JPanel decklistItem;
    private JLabel deckNameValue;
    private JLabel duevalue;
    private JButton learn;
    private JButton edit;
    private JButton newDeck;

    private Flashcard fc;

    public void refresh(){
        setDeckList();
        decklistList.revalidate();
        decklistList.repaint();
    }

    class newDeckButtonActionListener implements ActionListener {

        public newDeckButtonActionListener(){};

        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("createNewDeck")) {
                System.out.println("new deck clicked");
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
                DeckFrame df=new DeckFrame(d.getName(), d, getThis());
                df.setVisible(true);

            }
        }

    }

    public MainFrame(String title) {
        super(title);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fc.saveDecks();
                System.exit(0);
            }
        });
        this.setContentPane(mainPanel);
        this.setMinimumSize(new Dimension(400,100));


    }

    private void setDeckList(){
        Set<Deck> decks=fc.getDecks();
        for(Deck d: decks){
            JPanel decklistItem = new JPanel();
            decklistItem.setLayout(new BoxLayout(decklistItem, BoxLayout.X_AXIS));
            decklistList.add(decklistItem, BorderLayout.CENTER);
            deckNameValue = new JLabel();
            deckNameValue.setText(d.getName());
            decklistItem.add(deckNameValue);
            decklistItem.add(Box.createHorizontalGlue());
            duevalue = new JLabel();
            duevalue.setText(Integer.toString(d.getNumberOfDue()));
            decklistItem.add(duevalue);
            decklistItem.add(Box.createRigidArea(new Dimension(20,0)));

            learn = new JButton();
            learn.setText("Learn");
            decklistItem.add(learn);
            edit = new JButton();
            edit.setText("Edit");
            edit.setActionCommand("editDeck");
            ActionListener al_edit=new editDeckButtonActionListener(d);
            edit.addActionListener(al_edit);
            decklistItem.add(edit);
            decklistList.add(decklistItem);
        }
    }



    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        fc=new Flashcard();
        fc.loadDecks();
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        deckList = new JPanel();
        deckList.setLayout(new BorderLayout(0, 0));
        mainPanel.add(deckList, BorderLayout.CENTER);

        newDeck=new JButton("New deck");
        newDeck.setActionCommand("createNewDeck");
        ActionListener al=new newDeckButtonActionListener();
        newDeck.addActionListener(al);
        mainPanel.add(newDeck, BorderLayout.SOUTH);

        decklistList = new JPanel();
        decklistList.setLayout(new BoxLayout(decklistList, BoxLayout.Y_AXIS));
        deckList.add(decklistList, BorderLayout.CENTER);


        setDeckList();
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
