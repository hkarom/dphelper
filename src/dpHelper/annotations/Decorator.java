package dpHelper.annotations;


public @interface Decorator {

   enum Role {
      COMPONENT,
      COMPONENT_CONCRETE,
      DECORATOR,
      DECORATOR_CONCRETE
   }

   Role value();

}
