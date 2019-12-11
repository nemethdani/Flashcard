package swing;

import backend.Card;
import backend.Challenge;
import backend.Deck;
import backend.Side;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

public class LearnFrame extends JFrame{
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

    class showAnswerButtonActionListener implements ActionListener {
        public showAnswerButtonActionListener(){};
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("showAnswer")) {
                generateResponses();

            }
        }
    }

    class gradeButtonActionListener implements ActionListener {
        private int grade;
        public gradeButtonActionListener(int grade){this.grade=grade;};
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("grade")) {
                deck.getFirstChallenge().updateRepetitionTime(grade);
                mainFrame.refresh();
                challenges.removeAll();
                responses.removeAll();
                generateChallenges();

            }
        }
    }

    private void generateChallenges(){
        Challenge challenge=deck.getFirstChallenge();
        Set<Side> challengeSet=challenge.getChallenge();
        for(Side c: challengeSet){
            challenges.add(new JLabel((String) c.getInformation().getInformation()));
        }
        grading.setVisible(false);
        showAnswer.setEnabled(true);
    }

    private void generateResponses(){
        Challenge challenge=deck.getFirstChallenge();
        Set<Side> responseSet=challenge.getResponse();
        for(Side r: responseSet){
            responses.add(new JLabel((String) r.getInformation().getInformation()));
        }
        grading.setVisible(true);
        showAnswer.setEnabled(false);
    }

    public LearnFrame(Deck deck, MainFrame mainFrame){
        super("Learning: "+ deck.getName());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(400,100));

        this.deck=deck;
        this.mainFrame=mainFrame;

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
        grade2.setActionCommand("grade");
        grade2.addActionListener(new gradeButtonActionListener(3));
        grade4=new JButton("4");
        grade2.setActionCommand("grade");
        grade2.addActionListener(new gradeButtonActionListener(4));
        grade5=new JButton("5");
        grade2.setActionCommand("grade");
        grade2.addActionListener(new gradeButtonActionListener(5));
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


    }

}
