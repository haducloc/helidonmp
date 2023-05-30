package labs.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

public class ConstraintViolationUtil {

    public static String buildMessage(ConstraintViolationException exception) {
        StringBuilder sb = new StringBuilder();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String invalidPath = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();

            if (sb.length() > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append("Invalid Path: ").append(invalidPath)
                    .append(", Error Message: ").append(errorMessage);
        }

        return sb.toString();
    }
}
