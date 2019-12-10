package backend;

import java.io.Serializable;

public abstract class Information implements Serializable {
    protected Class type;
    public Class getType(){return type;}
    abstract public Object getInformation();
}
