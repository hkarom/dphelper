package experiments.singleton;

import dpHelper.annotations.Singleton;

@Singleton
public class WithGetInstanceNotStatic {

   private static WithGetInstanceNotStatic instance = null;

   private WithGetInstanceNotStatic() {

   }

   public WithGetInstanceNotStatic getInstance() {
      return null;
   }

}