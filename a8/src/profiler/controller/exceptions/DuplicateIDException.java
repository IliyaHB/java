package profiler.controller.exceptions;

public class DuplicateIDException extends Exception {
    public DuplicateIDException() {
        super("Duplicate Username !");
    }
}
