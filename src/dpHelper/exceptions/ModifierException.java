package dpHelper.exceptions;

public class ModifierException extends VerifierException {

    public ModifierException() {
    }

    public ModifierException(String message) {
        super(message);
    }

    public ModifierException(Throwable cause) {
        super(cause);
    }

    public ModifierException(String message, Throwable cause) {
        super(message, cause);
    }
}
