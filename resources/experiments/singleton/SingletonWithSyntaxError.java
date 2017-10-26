package experiments.singleton;

import dpHelper.annotations.Singleton;

@Singleton
public class SingletonWithSyntaxError {

   private static SingletonWithSyntaxError instance = null;

   private SingletonWithSyntaxError() {

   }

   public static SingletonWithSyntaxError getInstance() {
      return null;
   }

}