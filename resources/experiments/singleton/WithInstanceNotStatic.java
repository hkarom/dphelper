package experiments.singleton;

import dpHelper.annotations.Singleton;

@Singleton
public class WithInstanceNotStatic {

   private WithInstanceNotStatic instance = null;

   private WithInstanceNotStatic() {
   }

   public static WithInstanceNotStatic getInstance() {
      return null;
   }

}