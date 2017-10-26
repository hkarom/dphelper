package experiments.singleton;


import dpHelper.annotations.Singleton;

@Singleton
public class SingletonWithPublicConstructor implements singleton
    .ASingleInterface{

    private static SingletonWithPublicConstructor instance = null;

    public SingletonWithPublicConstructor(){

    }
    public static SingletonWithPublicConstructor getInstance(){
        return null;
    }

}