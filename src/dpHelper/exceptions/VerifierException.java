package dpHelper.exceptions;

/**
 * Created by root on 14/03/17.
 */
public abstract class VerifierException extends Exception {


    public VerifierException() {
    }

    public VerifierException(String message) {
        super(message);
    }

    public VerifierException(Throwable cause) {
        super(cause);
    }

    public VerifierException(String message, Throwable cause) {
        super(message, cause);
    }
}
