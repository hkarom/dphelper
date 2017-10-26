package experiments.decorator.decoratorValid;

import dpHelper.annotations.*;
@Decorator(Decorator.Role.DECORATOR)
public abstract class ComponentDecorator implements Component{
    private Component agregat = null;
    public void methode(){

    }
}