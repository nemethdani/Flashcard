package backend;

public class Side implements Comparable<Side>{
    private Information information;
    public Side(Information information){
        this.information=information;
    }

    public Information getInformation() {
        return information;
    }
    public void setInformation(Information i){information=i;}

    @Override
    public int compareTo(Side side) {

        Object info=information.getInformation();
        return ((String)info).compareTo((String)side.information.getInformation());
    }
}
