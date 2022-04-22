package az.uni.bookappauth.exception.excModel;

public class GeneralJwtException extends RuntimeException{

    public GeneralJwtException() {
        super();
    }

    public GeneralJwtException(String message) {
        super(message);
    }

}
