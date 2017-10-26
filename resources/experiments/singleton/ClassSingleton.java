/******
 *
 * Commentaire 1
 */
package experiments.singleton;

import dpHelper.annotations.Singleton;


/******
 *Commentaire 2
 *
 */
class FirstTest {

}

/**
 *Commentaire 3
 */
@Singleton
public class ClassSingleton {


    enum MonEnum {
        VAL1,
        VAL2,
    }

   /**
    *
    * Commentaire variable
    */
   private ClassSingleton instance = null;
   private ClassSingleton instance2 = null;

   public ClassSingleton() {
   }

   public ClassSingleton(int v) {
      System.out.print(v);
   }


   /******
    *
    * Commentaire method
    */
   public void getUniqueElement(int i, Boolean ok) {
      String e = "test";

      switch (e) {
         case "1":
            System.out.print(e);
            break;
         default:
            break;
      }
   }

   private class insideTest {

      public int getInstance() {
      }
   }

}


