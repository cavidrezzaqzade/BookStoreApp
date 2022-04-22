package az.uni.bookappauth.exception.excModel;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomExceptionDateParse extends JsonProcessingException {
    public CustomExceptionDateParse(String msg, JsonLocation loc, Throwable rootCause) {
        super(msg, loc, rootCause);
    }

    public CustomExceptionDateParse(String msg) {
        super(msg);
    }

    public CustomExceptionDateParse(String msg, JsonLocation loc) {
        super(msg, loc);
    }

    public CustomExceptionDateParse(String msg, Throwable rootCause) {
        super(msg, rootCause);
    }

    public CustomExceptionDateParse(Throwable rootCause) {
        super(rootCause);
    }
}
