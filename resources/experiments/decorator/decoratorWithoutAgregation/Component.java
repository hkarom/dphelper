package experiments.decorator.decoratorWithoutAgregation;

import dpHelper.annotations.*;

@Decorator(Decorator.Role.COMPONENT)
public interface Component{
    public void methode();
}