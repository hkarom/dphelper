package experiments.singleton;

import dpHelper.annotations.Singleton;

@Singleton
public class WithGetInstanceNotHavingReturn {

   private static WithGetInstanceNotHavingReturn instance = null;

   private WithGetInstanceNotHavingReturn() {

   }

   public static WithGetInstanceNotHavingReturn getInstance() {

   }

}