package pl.pawelkozlowski.library.exception;

public class PublicationsAlreadyExistsException extends RuntimeException {
    public PublicationsAlreadyExistsException(String message) {
        super(message);
    }
}
