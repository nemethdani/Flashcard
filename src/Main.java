import swing.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MainFrame mf=new MainFrame("My flashcard app");
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mf.setVisible(true);
    }
}
