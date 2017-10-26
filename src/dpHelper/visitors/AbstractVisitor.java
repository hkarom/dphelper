package dpHelper.visitors;

import javax.lang.model.element.Element;
import javax.lang.model.util.ElementScanner8;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractVisitor<R, P> extends ElementScanner8<R, P> {

    protected List<Element> foundElements = new ArrayList<>();

    public List<Element> getFoundElements() {
        return this.foundElements;
    }

    public void reset() {
        foundElements.clear();
    }

}
