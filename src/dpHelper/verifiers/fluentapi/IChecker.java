package dpHelper.verifiers.fluentapi;


import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.Collection;


public interface IChecker {

    IKindCheck that(Element element);
    IKindCheck thatAll(Collection<Element> elements);
    IExistCheck that(ElementKind kind);
    IExistCheck that(int number, ElementKind kind);
    IExistCheck thatAll(ElementKind kind);
    IExistCheck thatAtLeast(int number, ElementKind kind);

}






