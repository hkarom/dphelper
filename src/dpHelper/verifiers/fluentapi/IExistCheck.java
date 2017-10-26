package dpHelper.verifiers.fluentapi;

import dpHelper.exceptions.NumberOfElementException;

import javax.lang.model.element.Element;
import java.util.Collection;
import java.util.NoSuchElementException;

public interface IExistCheck {

    ISpecification in(Element element)
            throws NoSuchElementException, NumberOfElementException;

    ISpecification inAll(Collection<Element> elements)
            throws NoSuchElementException, NumberOfElementException;
}


