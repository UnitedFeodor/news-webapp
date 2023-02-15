package by.htp.ex.util.validation;

import java.util.List;

public interface IValidator {

    List<String> getErrors();
    boolean isValid();

}
