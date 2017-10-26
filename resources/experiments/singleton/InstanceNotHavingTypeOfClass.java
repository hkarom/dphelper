package experiments.singleton;

import dpHelper.annotations.Singleton;

@Singleton
public class InstanceNotHavingTypeOfClass{

    private static Integer instance = null;

    private InstanceNotHavingTypeOfClass(){

    }
    public static InstanceNotHavingTypeOfClass getInstance(){
        return null;
    }

}