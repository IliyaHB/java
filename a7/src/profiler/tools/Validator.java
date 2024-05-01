package profiler.tools;

import profiler.controller.exceptions.DataValidationException;

import java.util.regex.Pattern;

public class Validator {
    public static String validateName(String name, String message) throws DataValidationException {
        if (Pattern.matches("^[a-zA-Z\\s]{2,30}$", name)) {
            return name;
        } else {
            throw new DataValidationException(message);
        }
    }
}