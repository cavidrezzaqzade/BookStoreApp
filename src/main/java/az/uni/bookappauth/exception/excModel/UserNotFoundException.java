package az.uni.bookappauth.exception.excModel;

public class UserNotFoundException extends GeneralException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}