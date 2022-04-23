package az.uni.bookappauth.mapper;

import az.uni.bookappauth.domain.app.AuthorDto;
import az.uni.bookappauth.domain.app.BookDto;
import az.uni.bookappauth.entity.AuthorEntity;
import az.uni.bookappauth.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "authors", target = "authors", qualifiedByName = "setAuthorToAuthorDto")
    List<BookDto> booksToBooksDto(List<BookEntity> entities);

    @Mapping(source = "authors", target = "authors", qualifiedByName = "setAuthorToAuthorDto")
    BookDto bookToBookDto(BookEntity entity);

    @Named("setAuthorToAuthorDto")
    static List<AuthorDto> setAuthorToAuthorDto(List<AuthorEntity> authors) {
        return authors.stream()
                .map(e -> AuthorDto.builder()
                .id(e.getId())
                .name(e.getName())
                .created(e.getCreated())
                .updated(e.getUpdated()).build())
                .collect(Collectors.toList());
    }

//    @Named("setAuthorToListLong")
//    static List<Long> setAuthorToListLong(List<AuthorEntity> authors) {
//        return authors.stream()
//                .map(AuthorEntity::getId)
//                .collect(Collectors.toList());
//    }
}
