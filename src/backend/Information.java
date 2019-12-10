package backend;

public abstract class Information {
    protected Class type;
    public Class getType(){return type;}
    abstract public Object getInformation();
}
