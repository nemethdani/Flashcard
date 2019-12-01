package backend;

public class TextInformation extends Information{
    private String string;
    public TextInformation(String string){
        this.string=string;
        type=String.class;
    }
    public String getInformation(){return string;}

}
