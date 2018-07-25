package uk.co.mattbiggin.magorian.data;

public class MissingDataFileException extends RuntimeException {
    public MissingDataFileException(String message) {
        super(message);
    }
}
