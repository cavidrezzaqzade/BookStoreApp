package az.uni.bookappauth.service.app;

import az.uni.bookappauth.domain.RoleDto;
import az.uni.bookappauth.domain.app.AuthorDto;
import az.uni.bookappauth.entity.AuthorEntity;
import az.uni.bookappauth.entity.RoleEntity;
import az.uni.bookappauth.mapper.AuthorMapper;
import az.uni.bookappauth.mapper.RoleMapper;
import az.uni.bookappauth.repository.AuthorRepository;
import az.uni.bookappauth.repository.RoleRepository;
import az.uni.bookappauth.response.MessageResponse;
import az.uni.bookappauth.response.Reason;
import az.uni.bookappauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public ResponseEntity<?> addAuthor(AuthorDto authorDto){

        log.info("AuthorService/addNewAuthor method started");
        Map<String, String> map = new HashMap<>();

        if(authorRepository.existsByNameIgnoreCase(authorDto.getName()))
            map.put("authorName", "already exists");
        if(!map.isEmpty()) {
            log.error("AuthorService/addAuthor method with rolename already exists -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
            return MessageResponse.response(Reason.ALREADY_EXIST.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        AuthorEntity author = new AuthorEntity();
        author.setName(authorDto.getName());
        authorRepository.save(author);

        AuthorDto dto = authorMapper.authorToAuthorDto(author);
        log.info("AuthorService/addAuthor method ended -> status:" + HttpStatus.OK);
        return MessageResponse.response(Reason.SUCCESS_ADD.getValue(), dto, null, HttpStatus.OK);
    }

    public ResponseEntity<?> getAuthors(){
        log.info("AuthorService/getauthors method started");
        List<AuthorEntity> users = authorRepository.findAll();
        List<AuthorDto> roleDtos = authorMapper.authorsToAuthorDtos(users);
        log.info("AuthorService/getauthors method ended -> status:" + HttpStatus.OK);
        return MessageResponse.response(Reason.SUCCESS_GET.getValue(), roleDtos, null, HttpStatus.OK);
    }

//    public ResponseEntity<?> deleteRole(Long roleId){
//        log.info("RoleService/deleteRole method started");
//        Map<String, String> map = new HashMap<>();
//
//        Optional<RoleEntity> roleEntity = roleRepository.findById(roleId);
//        boolean checkForeignKeyViolation = roleRepository.checkForeignKeyExists(roleId);
//        if(roleEntity.isEmpty())
//            map.put("roleId", "role doesn't exist");
//        if(!map.isEmpty()){
//            log.error("RoleService/deleteRole method ended with roleId doesn't exists -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
//            return MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//
//        if(checkForeignKeyViolation)
//            map.put("roleId", "foreign key constraint viaolation");
//        if(!map.isEmpty()){
//            log.error("RoleService/deleteRole method ended with roleId fk violation -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
//            return MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//
//        roleRepository.delete(roleEntity.get());
//        RoleDto dto = roleMapper.roleToRoleDto(roleEntity.get());
//        log.info("RoleService/deleteRole method ended -> status:" + HttpStatus.OK);
//        return MessageResponse.response(Reason.SUCCESS_DELETE.getValue(), dto, null, HttpStatus.OK);
//    }
//
//    public ResponseEntity<?> updateRole(RoleDto role, Long roleId){
//        log.info("RoleService/updateRole method started");
//        Map<String, String> map = new HashMap<>();
//        Optional<RoleEntity> roleEntity = roleRepository.findById(roleId);
//
//        if(roleEntity.isEmpty())
//            map.put("roleId", "role doesn't exist");
//        if(!map.isEmpty()) {
//            log.error("RoleService/updateRole method ended with roleId doesn't exists -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
//            return MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//        Optional<RoleEntity> roleByRoleName = roleRepository.findByRoleNameIgnoreCase(role.getRoleName());
//        if(roleByRoleName.isPresent())
//            if(!Objects.equals(roleByRoleName.get().getId(), roleId) && roleByRoleName.get().getRoleName().equalsIgnoreCase(role.getRoleName()))
//                map.put("roleName", "roleName already exists");
//        if(!map.isEmpty()) {
//            log.error("RoleService/updateRole method ended with rolename already exists -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
//            return MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//        roleEntity.get().setRoleName(role.getRoleName());
//        roleRepository.save(roleEntity.get());
//        RoleDto dto = roleMapper.roleToRoleDto(roleEntity.get());
//        log.info("RoleService/updateRole method ended -> status:" + HttpStatus.OK);
//        return MessageResponse.response(Reason.SUCCESS_UPDATE.getValue(), dto, null, HttpStatus.OK);
//    }
}
