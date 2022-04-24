package az.uni.bookappauth.domain.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "book name can not be empty")
    private String bookName;

    @Min(value = 1,message = "pageCount must be greater than 0")
    private Integer pageCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long publisherId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime created;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updated;

//    @NotNull(message = "authors can not be null")
//    @NotEmpty(message = "authors can not be null")
//    private List<@NotNull(message = "authore id can not be null") @Min(value = 1, message = "authore id can not be 0 or negative") Long> authors;

    @NotNull(message = "authors can not be null")
    @NotEmpty(message = "authors can not be null")
    private List<@Valid @NotNull AuthorDto> authors;
}
