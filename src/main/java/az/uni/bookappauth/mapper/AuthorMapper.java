package az.uni.bookappauth.mapper;

import az.uni.bookappauth.domain.app.AuthorDto;
import az.uni.bookappauth.entity.AuthorEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto authorToAuthorDto(AuthorEntity entity);
    AuthorEntity authorDtoToAuthor(AuthorDto dto);
    List<AuthorDto> authorsToAuthorDtos(List<AuthorEntity> authors);
    List<AuthorEntity> authorsDtoToAuthors(List<AuthorDto> authors);
}
