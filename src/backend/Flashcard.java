package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Set;
import java.util.TreeSet;

public class Flashcard {
    private Set<Deck> decks;
    private File programDir;

    public Flashcard(){
        decks=new TreeSet<Deck>();
        String homestring=System.getProperty("user.home");
        File homeFolder=new File(homestring);
        //System.setProperty("user.dir", homeFolder.getAbsolutePath());
        programDir=new File(homeFolder, "flashcard_data");
        programDir.mkdir();
    }

    public void addDeck(Deck d){decks.add(d);}

    public Set<Deck> getDecks() {
        return decks;
    }

    public void loadDecks()  {

        File[] deckFiles=programDir.listFiles();
        decks.clear();
        for (File df: deckFiles){
            try {

                FileInputStream fis=new FileInputStream(df);
                ObjectInputStream ois=new ObjectInputStream(fis);

                decks.add((Deck)ois.readObject());
                ois.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void saveDecks() {
        for(Deck d: decks){
            try {
                d.serialize(programDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
