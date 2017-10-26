package dpHelper.verifiers;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.lang.model.element.Element;


public interface PatternVerifier<C extends Annotation> {

    void verifyAll(Set<? extends Element> annotatedElements);
}
