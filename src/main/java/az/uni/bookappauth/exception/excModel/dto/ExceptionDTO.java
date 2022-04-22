package az.uni.bookappauth.exception.excModel.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {

    private Integer code;

    private String message;

    @Override
    public String toString() {
        return "ExceptionDTO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}