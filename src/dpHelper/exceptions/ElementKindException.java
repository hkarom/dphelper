package dpHelper.exceptions;


public class ElementKindException extends VerifierException {

    public ElementKindException() {
    }

    public ElementKindException(String message) {
        super(message);
    }

    public ElementKindException(Throwable cause) {
        super(cause);
    }

    public ElementKindException(String message, Throwable cause) {
        super(message, cause);
    }
}