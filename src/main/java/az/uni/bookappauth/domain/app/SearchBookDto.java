package az.uni.bookappauth.domain.app;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchBookDto {

    private String bookName;
    private Integer pageCount;

}
