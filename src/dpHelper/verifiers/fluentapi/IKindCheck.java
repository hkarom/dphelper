package dpHelper.verifiers.fluentapi;

import dpHelper.exceptions.ElementKindException;
import dpHelper.exceptions.NumberOfElementException;

import javax.lang.model.element.ElementKind;

public interface IKindCheck {

    ISpecification is(ElementKind kind)
            throws ElementKindException, NumberOfElementException;

    ISpecification are(ElementKind kind)
            throws ElementKindException, NumberOfElementException;
}


