package by.htp.ex.util.validation;

import by.htp.ex.util.validation.impl.UserValidatorImpl;

public final class ValidatorProvider {

    private static final ValidatorProvider instance = new ValidatorProvider();

    // TODO news validator
    private final IUserValidator userValidator = new UserValidatorImpl();
    private ValidatorProvider(){}

    public static ValidatorProvider getInstance() {
        return instance;
    }

    public IUserValidator getUserValidator() {
        return userValidator;
    }

}
