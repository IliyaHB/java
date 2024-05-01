package profiler.models.tools;

import profiler.controller.exceptions.DataValidationException;

import java.util.regex.Pattern;

public class Validator {
    public static String validateName(String name, String message) throws DataValidationException {
        if (Pattern.matches("^[a-zA-Z\\s]{2,30}$", name)){
            return name;
        }else{
            throw new DataValidationException(message);
        }
    }

    public static Double validatePrice(Double price, String message) throws DataValidationException {
        String priceString = String.valueOf(price);
        if (Pattern.matches("[+-]?([0-9]*[.])?[0-9]+", priceString)){
            return price;
        } else {
            throw new DataValidationException(message);
        }
    }
}
