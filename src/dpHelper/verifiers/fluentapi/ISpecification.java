package dpHelper.verifiers.fluentapi;

import dpHelper.exceptions.ModifierException;
import dpHelper.exceptions.NumberOfElementException;
import dpHelper.exceptions.TypeException;
import dpHelper.exceptions.VerifierException;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public interface ISpecification {

    List<Element> getSelectedElements();

    /**
     * Specification functions
     */

    ISpecification withName(String name)
            throws NoSuchElementException, NumberOfElementException;

    ISpecification withNameContaining(String name)
          throws NoSuchElementException, NumberOfElementException;

    ISpecification withModifiers(Modifier...modifiers)
            throws ModifierException, NumberOfElementException;

    ISpecification withType(Element type)
            throws TypeException, NumberOfElementException;

    ISpecification withOneOfTheseTypes(Collection<Element> types)
            throws TypeException, NumberOfElementException;

    ISpecification withSuperClass(Element superClass)
            throws TypeException, NumberOfElementException;

    ISpecification withInterface(Element interfaceE)
            throws TypeException, NumberOfElementException;

    ISpecification withInterfaces(Collection<Element> interfaces)
            throws TypeException, NumberOfElementException;

    ISpecification withOneOfTheseInterfaces(Collection<Element> interfaces)
            throws TypeException, NumberOfElementException;



    /**
     * Description functions
     */

    ISpecification haveName(String name)
            throws NoSuchElementException, NumberOfElementException;

    ISpecification haveModifiers(Modifier...modifiers)
            throws ModifierException, NumberOfElementException;

    ISpecification haveType(Element type)
            throws TypeException, NumberOfElementException;

    ISpecification haveOneOfTheseTypes(Collection<Element> types)
            throws TypeException, NumberOfElementException;

    ISpecification haveDistinctTypes(Collection<Element> types)
            throws TypeException, NumberOfElementException;

    ISpecification haveParameter(Element parameter)
          throws TypeException, NumberOfElementException;

    ISpecification extendsClass(Element superClass)
            throws TypeException, NumberOfElementException;

    ISpecification implementsInterface(Element interfaceE)
            throws TypeException, NumberOfElementException;

    ISpecification implementsInterfaces(Collection<Element> interfaces)
            throws TypeException, NumberOfElementException;

    ISpecification implementsOneOfInterfaces(Collection<Element> interfaces)
            throws TypeException, NumberOfElementException;

    ISpecification implementsDistinctInterfaces(Collection<Element> interfaces)
            throws TypeException, NumberOfElementException;

    ISpecification annotatedWith(Class<? extends Annotation> annotation)
        throws NoSuchElementException, NumberOfElementException;

    ISpecification descendantOf(Element element)
        throws NoSuchElementException, TypeException, NumberOfElementException;

    ISpecification descendantOfOneOf(Collection<Element> interfaces)
        throws NoSuchElementException,TypeException, NumberOfElementException;

    ISpecification descendantOfOnlyOneOf(Collection<Element> interfaces)
        throws NoSuchElementException,TypeException, VerifierException;
}
