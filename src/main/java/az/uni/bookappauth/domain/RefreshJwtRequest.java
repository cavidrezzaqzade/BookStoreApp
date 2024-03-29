package az.uni.bookappauth.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RefreshJwtRequest {
    @NotEmpty
    public String refreshToken;
}
