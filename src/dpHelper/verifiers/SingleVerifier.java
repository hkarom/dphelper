package dpHelper.verifiers;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.Set;


public abstract class SingleVerifier<C extends Annotation> implements PatternVerifier<C> {


    @Override
    public void verifyAll(Set<? extends Element> annotatedElements) {
        for (Element element : annotatedElements) {
            verify(element);
        }
    }

    public abstract void verify(Element annotatedElement);
}
