package backend;

import java.io.Serializable;

/***
 * Interfész az absztrakt információk kezeléséhez
 */
public abstract class Information implements Serializable {
    protected Class type;

    /***
     * visszadja az absztrakt infó típusát
     * @return
     */
    public Class getType(){return type;}

    /***
     * Visszadja az absztrak információt
     * @return az absztrakt információ
     */
    abstract public Object getInformation();
}
