package experiments.singleton;

import dpHelper.annotations.Singleton;

@Singleton
public class SingletonWithPrivateGetInstance extends ClassSingleton{

    private static SingletonWithPrivateGetInstance instance = null;

    private SingletonWithPrivateGetInstance(){

    }
    private static SingletonWithPrivateGetInstance getInstance(){
        return null;
    }

}