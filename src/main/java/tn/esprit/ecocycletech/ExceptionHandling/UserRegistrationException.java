package tn.esprit.ecocycletech.ExceptionHandling;

public class UserRegistrationException extends RuntimeException {
    private final String fieldName;

    public UserRegistrationException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
