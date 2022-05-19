package az.uni.bookappauth.response;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseModelDTO<T> extends ResponseModel {
    private T data;
    private T errors;

    public ResponseModelDTO(String message, T data, T errors) {
        super(message);
        this.data = data;
        this.errors = errors;
    }

//    public ResponseModelDTO(String message) {
//        super(message);
//    }
//
//    public ResponseModelDTO(T data) {
//        this.data = data;
//    }
}
