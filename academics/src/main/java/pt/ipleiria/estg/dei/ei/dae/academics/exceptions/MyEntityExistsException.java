package pt.ipleiria.estg.dei.ei.dae.academics.exceptions;

public class MyEntityExistsException extends RuntimeException {
    public MyEntityExistsException(String message) {
        super(message);
    }
}
