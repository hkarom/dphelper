import dpHelper.annotations.Singleton;

@Singleton
public class Classe1 implements Interface1, Interface2{

    private static Classe1 instance = null;

    private Classe1(){

    }
    public static Classe1 getInstance(){
        return null;
    }

}