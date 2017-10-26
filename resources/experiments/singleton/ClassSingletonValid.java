package experiments.singleton;
import dpHelper.annotations.Singleton;

@Singleton
public class ClassSingletonValid{

    private static ClassSingletonValid instance = null;

    private ClassSingletonValid(){

    }
    public static ClassSingletonValid getInstance(){
        return null;
    }

}