package az.uni.bookappauth.exception.excModel.dto;

public enum DefaultException {

    USER_NOT_FOUND("User not founded"),
    NAME_ALREADY_EXIST("This name already exist.");

    private final String value;

    DefaultException(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}