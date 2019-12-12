package swing;

import backend.Card;
import backend.Challenge;
import backend.Deck;
import backend.Side;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Set;

/***
 * Ezen lehet megjeleníteni a challengeket, a válaszokat, önmagunkat értékelni
 */
public class LearnFrame extends JFrame implements KeyListener{
    private Deck deck;
    private MainFrame mainFrame;

    JPanel challengeResponse;
    JPanel challenges;
    JPanel responses;

    private JButton showAnswer;

    private JLabel gradeLabel;
    private JButton grade2;
    private JButton grade3;
    private JButton grade4;
    private JButton grade5;
    private JPanel grading;

    private JPanel buttons;

    /***
     * showAnswer eseményre megjeleníti a Challenge-ben tárolt response-okat
     */
    class showAnswerButtonActionListener implements ActionListener {
        public showAnswerButtonActionListener(){};
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("showAnswer")) {
                generateResponses();
                pack();

            }
        }
    }

    /***
     * értékeljük az aktuális Challengre az eredményünket, legeneráltatja a következő challenget
     * @param grade 0-5 értékelés
     */
    private void gradeChallenge(int grade){
        deck.learn(deck.getFirstChallenge(), grade);
        mainFrame.refresh();
        challenges.removeAll();
        responses.removeAll();
        generateChallenges();
    }

    /***
     * gradeButton eseményre meghívja a gradeChallenge(int) függvényt
     */
    class gradeButtonActionListener implements ActionListener {
        private int grade;

        /***
         * Létrehozza az actionlistenert
         * @param grade 0-5 értékelés
         */
        public gradeButtonActionListener(int grade){this.grade=grade;};
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("grade")) {
                gradeChallenge(grade);

            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    /***
     * space vagy numpad 2-5 felengedésére meghívja  a kapcsolódó függvényeket
     */
    public void keyReleased(KeyEvent keyEvent) {
        int key =keyEvent.getKeyCode();
        System.err.println(key);
        switch (key){
            case KeyEvent.VK_SPACE:{
                generateResponses();break;
            }
            case KeyEvent.VK_NUMPAD2:{
                gradeChallenge(2);break;
            }
            case KeyEvent.VK_NUMPAD3:{
                gradeChallenge(3);break;
            }
            case KeyEvent.VK_NUMPAD4:{
                gradeChallenge(4);break;
            }
            case KeyEvent.VK_NUMPAD5:{
                gradeChallenge(5);break;
            }
            default:{break;}
        }
    }

    /***
     * generálja  a következő challenge GUI-ját, aktiválja a szükséges gombokat
     */
    private void generateChallenges(){
        Challenge challenge=deck.getFirstChallenge();
        Set<Side> challengeSet=challenge.getChallenge();
        for(Side c: challengeSet){
            challenges.add(new JLabel((String) c.getInformation().getInformation()));
        }
        grading.setVisible(false);
        showAnswer.setEnabled(true);
    }

    /***
     * generálja a válaszokat a GUI-ban
     */
    private void generateResponses(){
        Challenge challenge=deck.getFirstChallenge();
        Set<Side> responseSet=challenge.getResponse();
        for(Side r: responseSet){
            responses.add(new JLabel((String) r.getInformation().getInformation()));
        }
        grading.setVisible(true);
        showAnswer.setEnabled(false);
    }

    /***
     * Létrehozza a frame-et
     * @param deck melyik Deckből tanulunk
     * @param mainFrame a MainFrame, amiből megnyitottuk az ablakot
     */
    public LearnFrame(Deck deck, MainFrame mainFrame){
        super("Learning: "+ deck.getName());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(400,100));

        this.deck=deck;
        this.mainFrame=mainFrame;
        this.addKeyListener(this);
        this.setFocusable(true);

        challenges=new JPanel();
        challenges.setLayout(new BoxLayout(challenges, BoxLayout.Y_AXIS));
        responses=new JPanel();
        responses.setLayout(new BoxLayout(responses, BoxLayout.Y_AXIS));
        challengeResponse=new JPanel();
        challengeResponse.setLayout(new BoxLayout(challengeResponse, BoxLayout.Y_AXIS));
        challengeResponse.add(challenges);
        challengeResponse.add(responses);
        add(challengeResponse, BorderLayout.CENTER);

        showAnswer=new JButton("Show answer");
        showAnswer.addActionListener(new showAnswerButtonActionListener());
        showAnswer.setActionCommand("showAnswer");

        gradeLabel=new JLabel("Grade: ");
        grade2=new JButton("<=2");
        grade2.setActionCommand("grade");
        grade2.addActionListener(new gradeButtonActionListener(2));
        grade3=new JButton("3");
        grade3.setActionCommand("grade");
        grade3.addActionListener(new gradeButtonActionListener(3));
        grade4=new JButton("4");
        grade4.setActionCommand("grade");
        grade4.addActionListener(new gradeButtonActionListener(4));
        grade5=new JButton("5");
        grade5.setActionCommand("grade");
        grade5.addActionListener(new gradeButtonActionListener(5));
        grading=new JPanel();
        grading.add(gradeLabel);
        grading.add(grade2);
        grading.add(grade3);
        grading.add(grade4);
        grading.add(grade5);

        buttons=new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.add(showAnswer);
        buttons.add(grading);
        add(buttons, BorderLayout.SOUTH);

        generateChallenges();

        pack();

    }

}
