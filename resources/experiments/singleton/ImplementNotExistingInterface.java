package experiments.singleton;

import dpHelper.annotations.Singleton;

@Singleton
public class ImplementNotExistingInterface implements Xyz {

    private static ImplementNotExistingInterface instance = null;

    private ImplementNotExistingInterface(){

    }
    public static ImplementNotExistingInterface getInstance(){
        return null;
    }

}