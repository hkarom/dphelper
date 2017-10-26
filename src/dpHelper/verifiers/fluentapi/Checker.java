package dpHelper.verifiers.fluentapi;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.ArrayList;
import java.util.Collection;


public class Checker implements IChecker {

  public static Checker CHECK = new Checker();

  @Override
  public IKindCheck that(Element element) {
    return new KindCheck(element);
  }

  @Override
  public IKindCheck thatAll(Collection<Element> elements) {
    return new KindCheck(new ArrayList<>(elements));
  }

  @Override
  public IExistCheck that(ElementKind kind) {
    return new ExistCheck(kind);
  }

  @Override
  public IExistCheck that(int number, ElementKind kind) {
    return new ExistCheck(Constraint.FIXED, number, kind);
  }

  @Override
  public IExistCheck thatAll(ElementKind kind) {
    return new ExistCheck(Constraint.ALL, kind);
  }

  @Override
  public IExistCheck thatAtLeast(int number, ElementKind kind) {
    return new ExistCheck(Constraint.AT_LEAST, number, kind);
  }

  enum Constraint {
    ALL(""),
    FIXED("exactly "),
    AT_LEAST("at least ");

    private String strConstraint;

    Constraint(String name) {
      strConstraint = name;
    }

    public String toString() {
      return strConstraint;
    }
  }
}
