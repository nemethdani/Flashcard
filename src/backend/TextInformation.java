package backend;

/***
 * String információ típus
 */
public class TextInformation extends Information{
    private String string;

    /***
     * Létrehozza stringből az információt
     * @param string ezt tárolja majd az információban
     */
    public TextInformation(String string){
        this.string=string;
        type=String.class;
    }

    /***
     * Visszaadja a tárolt stringet
     * @return a tárolt string
     */
    public String getInformation(){return string;}

}
