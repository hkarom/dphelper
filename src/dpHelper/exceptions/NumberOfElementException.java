package dpHelper.exceptions;

public class NumberOfElementException
extends VerifierException {
    public NumberOfElementException() {
    }

    public NumberOfElementException(String message) {
        super(message);
    }

    public NumberOfElementException(Throwable cause) {
        super(cause);
    }

    public NumberOfElementException(String message, Throwable cause) {
        super(message, cause);
    }
}
