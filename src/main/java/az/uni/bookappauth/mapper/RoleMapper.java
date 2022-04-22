package az.uni.bookappauth.mapper;

import az.uni.bookappauth.domain.RoleDto;
import az.uni.bookappauth.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto roleToRoleDto(RoleEntity entity);
    RoleEntity roleDtoToRole(RoleDto dto);
    List<RoleDto> rolesToRoleDtos(List<RoleEntity> roleEntities);
}
