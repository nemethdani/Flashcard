import swing.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            MainFrame mf=new MainFrame("My flashcard app");
            mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mf.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("unhandled error: exiting");
            System.exit(1);
        }
    }
}
