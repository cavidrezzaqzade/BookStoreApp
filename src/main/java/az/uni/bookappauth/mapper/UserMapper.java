package az.uni.bookappauth.mapper;

import az.uni.bookappauth.domain.UserDto;
import az.uni.bookappauth.entity.RoleEntity;
import az.uni.bookappauth.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "roles", target = "roles", qualifiedByName = "setRoleToListLong")
    @Mapping(source = "name", target = "firstname")
    @Mapping(source = "surname", target = "lastname")
    List<UserDto> usersToUsersDto(List<UserEntity> userEntities);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "setRoleToListLong")
    @Mapping(source = "name", target = "firstname")
    @Mapping(source = "surname", target = "lastname")
    UserDto userToUserDto(UserEntity entity);

//    @Mapping(source = "roles", target = "roles", qualifiedByName = "setListLongToRole")
//    @Mapping(source = "firstname", target = "name")
//    @Mapping(source = "lastname", target = "surname")
//    UserEntity userDtoToUser(UserDto userDto);

    @Named("setRoleToListLong")
    static List<Long> setRoleToListLong(Set<RoleEntity> roles) {
        return roles.stream()
                .map(RoleEntity::getId)
                .collect(Collectors.toList());
    }

    @Named("setListLongToRole")
    static List<Long> setListLongToRole(Set<RoleEntity> roles) {
        return roles.stream()
                .map(RoleEntity::getId)
                .collect(Collectors.toList());
    }
}
