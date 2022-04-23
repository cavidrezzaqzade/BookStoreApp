package az.uni.bookappauth.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SearchCriteria {
    private final String key;
    private final String operation;
    private final Object value;
}
