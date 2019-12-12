package backend;

import java.io.Serializable;

/***
 * Megvalósítja a Card egy oldalát, egy-egy információt tárol
 */
public class Side implements Comparable<Side>, Serializable {
    private Information information;

    /***
     * Létrehoz egy Side-ot a megadott Information-ből
     * @param information Ezt teszi rá az oldalra
     */
    public Side(Information information){
        this.information=information;
    }

    /***
     * Visszaadja a Side-on levő információt
     * @return A Side-on levő információ
     */
    public Information getInformation() {
        return information;
    }

    /***
     * beállítja a Side információját
     * @param i az új információ, amire átálítjuk a Dide-ot
     */
    public void setInformation(Information i){information=i;}

    @Override
    /***
     * Az info által tartalmazott string szerint ABC rendben
     */
    public int compareTo(Side side) {

        Object info=information.getInformation();
        return ((String)info).compareTo((String)side.information.getInformation());
    }
}
