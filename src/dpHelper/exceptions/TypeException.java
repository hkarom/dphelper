package dpHelper.exceptions;

public class TypeException
extends VerifierException {
    public TypeException() {
    }

    public TypeException(String message) {
        super(message);
    }

    public TypeException(Throwable cause) {
        super(cause);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
