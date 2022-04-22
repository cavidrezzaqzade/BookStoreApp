package az.uni.bookappauth.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public class MessageResponse {
    public static ResponseEntity<?> response(String message, Object data, Object error, HttpStatus status) {
        return new ResponseEntity(new ResponseModelDTO(message, data, error), status);
    }
    public static ResponseEntity<Object> validationResponse(String message, Object data, Object error, HttpStatus status) {
        return ResponseEntity.ok().body(new ResponseModelDTO(message, data, error));
    }


    public static ResponseEntity<?> successGetResponse(Object data, Object error) {
        return ResponseEntity.ok().body(new ResponseModelDTO(Reason.SUCCESS_GET.getValue(), data, error));
    }

    public static ResponseEntity<?> successUpdateResponse(String message, Object data, Object error) {
        return ResponseEntity.ok().body(new ResponseModelDTO(message, data, error));
    }

    public static ResponseEntity<?> successDelete() {
        return ResponseEntity.ok().body(new ResponseModel(Reason.SUCCESS_DELETE.getValue()));
    }

    //****************************************************************************************

    public static ResponseEntity<?> failureNotFoundResponse() {
        return new ResponseEntity(new ResponseModel(Reason.NOT_FOUND.getValue()),HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> failureDeleteConstraintViolationResponse(String field) {
        ResponseErrorMapModel responseErrorListModel =new ResponseErrorMapModel();
        responseErrorListModel.getErrors().put(field,Reason.CONSTRAINT_VIOLATED.getValue());
        return new ResponseEntity(responseErrorListModel,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ResponseEntity<?> errorResponse(Map<String, String> errors) {
        ResponseErrorMapModel responseErrorListModel =new ResponseErrorMapModel();
        responseErrorListModel.setErrors(errors);
        return new ResponseEntity(responseErrorListModel,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ResponseEntity<?> errorResponse(ResponseErrorMapModel responseErrorListModel) {
        return new ResponseEntity(responseErrorListModel,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ResponseEntity<?> errorUnknownResponse() {
        return new ResponseEntity(new ResponseModel(Reason.UNKNOW.getValue()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}