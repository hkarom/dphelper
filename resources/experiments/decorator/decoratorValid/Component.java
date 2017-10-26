package experiments.decorator.decoratorValid;

import dpHelper.annotations.*;

@Decorator(Decorator.Role.COMPONENT)
public interface Component{
    public void methode();
}