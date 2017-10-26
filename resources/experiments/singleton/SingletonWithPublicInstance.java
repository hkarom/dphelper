package experiments.singleton;

import dpHelper.annotations.Singleton;

@Singleton
public class SingletonWithPublicInstance extends singleton.ClassSingleton {

   public static SingletonWithPublicInstance instance = null;

   private SingletonWithPublicInstance() {

   }

   public static SingletonWithPublicInstance getInstance() {
      return null;
   }

}