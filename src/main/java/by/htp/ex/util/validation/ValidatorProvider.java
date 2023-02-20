package by.htp.ex.util.validation;

import by.htp.ex.util.validation.impl.NewsValidatorImpl;
import by.htp.ex.util.validation.impl.UserValidatorImpl;

public final class ValidatorProvider {

    private static final ValidatorProvider instance = new ValidatorProvider();

    private final IUserValidator userValidator = new UserValidatorImpl();
    private final INewsValidator newsValidator = new NewsValidatorImpl();
    private ValidatorProvider(){}

    public static ValidatorProvider getInstance() {
        return instance;
    }

    public IUserValidator getUserValidator() {
        return userValidator;
    }

    public INewsValidator getNewsValidator() {
        return newsValidator;
    }
}
